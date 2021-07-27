package com.github.aaabramov.catz.demo

import cats.kernel.Monoid

object P7_HigherKindedTypes extends App {

  val opt: Option[Int] = Option(10)
  val seq: Seq[Int] = Seq(10, 20, 30)
  val set: Set[Int] = Set(100, 200, 300)

  trait Sum[A, G[_]] {
    def sum(container: G[A]): A
  }

  object Sum {
    // Helper to invoke instead of implicitly[Sum[A, G]]
    def apply[A, G[_]](implicit S: Sum[A, G]): Sum[A, G] = S
  }

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

  // TODO: app ops?

}
