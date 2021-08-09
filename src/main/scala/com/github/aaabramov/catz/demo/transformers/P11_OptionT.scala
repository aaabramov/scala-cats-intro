package com.github.aaabramov.catz.demo.transformers

import cats.data.OptionT
import com.github.aaabramov.catz.demo.Person

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object P11_OptionT extends App {

  /** Problem statement */

  {
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
  }

  /** Solution */

  // Welcome OptionT!
  // case class OptionT[F[_], A](value: F[Option[A]])

  def fetchPerson(id: Long): OptionT[Future, Person] =
    if (id % 2 == 0) {
      OptionT.pure(Person("Alex", 20))
    } else if (id < 0) {
      OptionT.liftF(Future.failed(new RuntimeException("ID < 0")))
    } else {
      OptionT.none[Future, Person]
      // OptionT.none -- scala is powerful enough
    }

  /** Methods */

  // def map[B](f: A => B): OptionT[F, B]
  val maybeName = fetchPerson(10).map(_.name)

  // def subflatMap[B](f: A => Option[B]): OptionT[F, B]
  val maybeHobby = fetchPerson(10).subflatMap(_.hobby)

  // def filter(p: A => Boolean): OptionT[F, A]
  val isAdult = fetchPerson(10).filter(_.age > 18)

  // def fold[B](default: => B)(f: A => B): F[B]
  val hobby1 =
    fetchPerson(10)
      .subflatMap(_.hobby)
      .fold("no hobby")(hobby => s"hobby is $hobby")

  // def cata[B](default: => B, f: A => B): F[B] // same as fold but with one parameter list
  val hobby2 =
    fetchPerson(10)
      .subflatMap(_.hobby)
      .cata("no hobby", hobby => s"hobby is $hobby")

  // unwrap the OptionT, e.g. in the endpoints code
  val future: Future[Option[String]] = maybeName.value

  /** Factory methods */

  // A => OptionT[A]
  OptionT.some == OptionT.pure

  // Option[A] => OptionT[F, A]
  OptionT.fromOption[Future](Some(10)): OptionT[Future, Int]

  // F[Option[A]] => OptionT[F, A]
  OptionT.apply[Future, Int](Future(Some(10))): OptionT[Future, Int]

  // if (cond) OptionT.some(A) else OptionT.none[A]
  OptionT.when[Future, String](1 % 2 == 0)("Joe")

  // if (cond) OptionT.lift(F[A]) else OptionT.none[F, A]
  OptionT.whenF(1 % 2 == 0)(Future("Joe"))


}
