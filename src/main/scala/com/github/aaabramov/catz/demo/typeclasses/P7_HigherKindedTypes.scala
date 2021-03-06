package com.github.aaabramov.catz.demo.typeclasses

import cats.kernel.Monoid

// F[_] ~ Future[_] ~ Option[_]

trait Sum[A, G[_]] {
  def sum(container: G[A]): A
}

object Sum {
  // Helper to invoke instead of implicitly[Sum[A, G]]
  def apply[A, G[_]](implicit S: Sum[A, G]): Sum[A, G] = S
}

trait Example[F[_]]

object P7_HigherKindedTypes extends App {

  // Either[A, B]
  type ErrorOr[A] = Either[String, A] // ~ F[_]

  // Type[Type]
  // Type[Type1, Type2]

  val opt: Option[Int] = Option(10)
  val seq: Seq[Int] = Seq(10, 20, 30)
  val set: Set[Int] = Set(100, 200, 300)

  implicit def optSum[A](implicit M: Monoid[A]): Sum[A, Option] =
    (o: Option[A]) => o.getOrElse(M.empty)

  implicit def seqSum[A](implicit M: Monoid[A]): Sum[A, Seq] =
    (s: Seq[A]) => s.foldLeft(M.empty) { case (coll, curr) =>
      M.combine(coll, curr)
    }

  implicit def setSum[A](implicit M: Monoid[A]): Sum[A, Set] =
    (s: Set[A]) => s.foldLeft(M.empty) { case (coll, curr) =>
      M.combine(coll, curr)
    }

  println(Sum[Int, Option].sum(opt))

  println(Sum[Int, Seq].sum(seq))

  println(Sum[Int, Set].sum(set))

}
