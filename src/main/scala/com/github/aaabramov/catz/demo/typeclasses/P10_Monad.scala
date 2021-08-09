package com.github.aaabramov.catz.demo.typeclasses

import cats.Monad

import scala.concurrent.{ExecutionContext, Future}


//@formatter:off
object P10_Monad extends App {

  // Monad
  // - def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

  implicit val optApplicative: Monad[Option] =
    new Monad[Option] {

    override def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] =
      fa.flatMap(f)

    override def pure[A](x: A): Option[A] =
      Option(x)

    override def map[A, B](fa: Option[A])(f: A => B): Option[B] =
      fa.map(f)

    // ignore this for now
    override def ap[A, B](ff: Option[A => B])(fa: Option[A]): Option[B] = ???
    override def tailRecM[A, B](a: A)(f: A => Option[Either[A, B]]): Option[B] = ???
  }

  implicit val listApplicative: Monad[List] =
    new Monad[List] {
    override def flatMap[A, B](fa: List[A])(f: A => List[B]): List[B] =
      fa.flatMap(f)

    override def pure[A](x: A): List[A] =
      List(x)

    override def map[A, B](fa: List[A])(f: A => B): List[B] =
      fa.map(f)

    // ignore this for now
    override def ap[A, B](ff: List[A => B])(fa: List[A]): List[B] = ???
    override def tailRecM[A, B](a: A)(f: A => List[Either[A, B]]): List[B] = ???
  }

  implicit def futureApplicative(implicit ec: ExecutionContext): Monad[Future] =
    new Monad[Future] {

      override def flatMap[A, B](fa: Future[A])(f: A => Future[B]): Future[B] =
        fa.flatMap(f)

      override def pure[A](x: A): Future[A] =
        Future.successful(x)

      override def map[A, B](fa: Future[A])(f: A => B): Future[B] =
        fa.map(f)

      // ignore this for now
      override def ap[A, B](ff: Future[A => B])(fa: Future[A]): Future[B] = ???
      override def tailRecM[A, B](a: A)(f: A => Future[Either[A, B]]): Future[B] = ???
    }

}
