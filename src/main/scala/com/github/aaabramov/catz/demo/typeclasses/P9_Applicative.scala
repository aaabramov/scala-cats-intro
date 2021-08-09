package com.github.aaabramov.catz.demo.typeclasses

import cats.Applicative

import scala.concurrent.{ExecutionContext, Future}


object P9_Applicative extends App {

  // Applicative extends Functor
  // - def pure[A](a: A): F[A]
  // - def map[A, B](fa: F[A])(f: A => B): F[B] // (from Functor)

  implicit val optApplicative: Applicative[Option] = new Applicative[Option] {

    override def pure[A](x: A): Option[A] = Option(x)

    override def map[A, B](fa: Option[A])(f: A => B): Option[B] =
      fa.map(f)

    // ignore this for now
    override def ap[A, B](ff: Option[A => B])(fa: Option[A]): Option[B] = ???
  }

  implicit val listApplicative: Applicative[List] = new Applicative[List] {

    override def pure[A](x: A): List[A] = List(x)

    override def map[A, B](fa: List[A])(f: A => B): List[B] =
      fa.map(f)

    // ignore this for now
    override def ap[A, B](ff: List[A => B])(fa: List[A]): List[B] = ???
  }

  implicit def futureApplicative(implicit ec: ExecutionContext): Applicative[Future] =
    new Applicative[Future] {

      override def pure[A](x: A): Future[A] = Future.successful(x)

      override def map[A, B](fa: Future[A])(f: A => B): Future[B] =
        fa.map(f)

      // ignore this for now
      override def ap[A, B](ff: Future[A => B])(fa: Future[A]): Future[B] = ???
    }

}
