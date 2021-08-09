package com.github.aaabramov.catz.demo.typeclasses

import cats.Functor

import scala.concurrent.{ExecutionContext, Future}


object P8_Functor extends App {

  // Functor
  // - def map[A, B](fa: F[A])(f: A => B): F[B]

  implicit val optFunctor: Functor[Option] = new Functor[Option] {
    override def map[A, B](fa: Option[A])(f: A => B): Option[B] =
      fa.map(f)
  }

  implicit val listFunctor: Functor[List] = new Functor[List] {
    override def map[A, B](fa: List[A])(f: A => B): List[B] =
      fa.map(f)
  }

  implicit def futureFunctor(implicit ec: ExecutionContext): Functor[Future] =
    new Functor[Future] {
      override def map[A, B](fa: Future[A])(f: A => B): Future[B] =
        fa.map(f)
    }

  def toUppercase[F[String]](f: F[String])
                            (implicit F: Functor[F]): F[String] =
    F.map(f)(_.toUpperCase)

  println {
    toUppercase(Option("Joe"))
  }
  println {
    toUppercase(Option.empty[String])
  }
  println {
    toUppercase(List("joe", "sam", "chloe"))
  }
  println {
    toUppercase(List.empty[String])
  }

}
