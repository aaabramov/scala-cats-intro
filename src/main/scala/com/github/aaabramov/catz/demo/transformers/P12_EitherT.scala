package com.github.aaabramov.catz.demo.transformers

import cats.data.EitherT
import com.github.aaabramov.catz.demo.Person

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

//@formatter:off
sealed trait AppError {
  def code: Int
  def message: String
}

object AppError {
  case class PersonNotFound(id: Long) extends AppError {
    override def code: Int = 404
    override def message: String = s"Person with id=$id does not exist"
  }

  case class InvalidPersonId(invalidId: Long) extends AppError {
    override def code: Int = 400
    override def message: String = s"Invalid personId provided: $invalidId"
  }

  case object EmptyPersonName extends AppError {
    override def code: Int = 400
    override def message: String = "Name could not be empty"
  }

  case class TooYoungError(youngPerson: Person) extends AppError {
    override def code: Int = 400
    override def message: String = s"${youngPerson.name}(${youngPerson.age} y.o.) is too young to use this service."
  }
}
//@formatter:on

object P11_EitherT extends App {

  /** Problem statement */

  // If we can't find a person should we really complicate the whole monad pipeline? NO!
  def fetchPerson(id: Long): Future[Option[Person]] =
    if (id % 2 == 0) {
      Future(Some(Person("Alex", 20)))
    } else if (id < 0) {
      Future.failed(new RuntimeException("ID < 0"))
    } else {
      Future(None)
    }

  // How many time you've written code like this?
  fetchPerson(10).map(_.map(_.name)): Future[Option[String]]
  fetchPerson(10).map(_.filter(_.age > 18)): Future[Option[Person]]
  fetchPerson(10).map(_.exists(_.name.nonEmpty)): Future[Boolean]

  /** Solution */

  // Welcome EitherT!
  // case class EitherT[F[_], A, B](value: F[Either[A, B]])

  def fetchPersonNew(id: Long): EitherT[Future, AppError, Person] =
    if (id % 2 == 0) {
      EitherT.pure(Person("Alex", 20))
    } else if (id < 0) {
      EitherT.leftT(AppError.InvalidPersonId(id))
    } else {
      EitherT.leftT(AppError.PersonNotFound(id))
    }

  /** Methods */

  // def map[D](f: B => D): EitherT[F, A, D]
  val name = fetchPersonNew(10).map(_.name)

  def validate(person: Person): Either[AppError, Person] =
    if (person.name.nonEmpty) Right(person)
    else Left(AppError.EmptyPersonName)

  // def subflatMap[AA >: A, D](f: B => Either[AA, D]): EitherT[F, AA, D]
  val maybeHobby = fetchPersonNew(10).subflatMap(validate)

  // def fold[C](fa: A => C, fb: B => C): F[C]
  val hobby1 =
    fetchPersonNew(10)
      .map(_.name.toUpperCase)
      .fold(
        error => s"Error occurred: $error", // handle errors
        name => s"Person found: $name" // success case
      )

  // unwrap the EitherT, e.g. in the endpoints code
  val outcome: Future[Either[AppError, String]] = name.value


  //@formatter:off
  /** Factory methods */

  // B => EitherT[F, A, B] => EitherT( F( Right(B) ) )
  EitherT.pure == EitherT.rightT

  // A => EitherT[F, A, B] => EitherT( F( Left(A) ) )
  EitherT.leftT[Future, String](AppError.EmptyPersonName)

  // Either[A, B] => EitherT[F, A, B]
  EitherT.fromEither[Future](Left("error"))
  EitherT.fromEither[Future](Right(10))

  // Option[B] => EitherT[F, A, B]
  EitherT.fromOption[Future](
    Some("Joe"),
    ifNone = AppError.PersonNotFound(0L)
  ) // EitherT( F( Right("Joe") ) )

  EitherT.fromOption[Future](
    None,
    ifNone = AppError.PersonNotFound(0L)
  ) // EitherT( F( Left(AppError.PersonNotFound(0L)) ) )

  // F[Either[A, B]] => EitherT[F, A, B]
  EitherT[Future, String, Int](Future.successful(Left("error"))) // EitherT( F( Left("error") ) )
  EitherT[Future, String, Int](Future.successful(Right(10)))     // EitherT( F( Right(10) ) )

  // if (cond) EitherT.rightT(B) else EitherT.leftT(A)
  {
    val name1 = "Joe"
    EitherT.cond[Future](
      test  = name1.nonEmpty, // true
      right = name1,
      left  = AppError.EmptyPersonName
    ) // EitherT( F( Right("Joe") ) )

    val name2 = ""
    EitherT.cond[Future](
      test  = name2.nonEmpty, // false
      right = name2,
      left  = AppError.EmptyPersonName
    ) // EitherT( F( Left(AppError.EmptyPersonName) ) )
  }


}
