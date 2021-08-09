package com.github.aaabramov.catz.demo.typeclasses

import cats.kernel.Monoid
import cats.syntax.semigroup._
import com.github.aaabramov.catz.demo.Price


object P6_Monoid extends App {

  // Monoid extends Semigroup
  // - def empty
  // - def combine(x, y) // (from Semigroup)

  implicit val priceMonoid: Monoid[Price] = new Monoid[Price] {
    override def empty: Price = Price(0)

    override def combine(x: Price, y: Price): Price =
      Price(x.amount + y.amount)
  }

  def sum[T](list: Seq[T])(implicit M: Monoid[T]): T =
    list.foldLeft(M.empty) { case (coll, curr) =>
      coll |+| curr
    }

  // [0, 1, 2, 3]
  // (0)(A, A => A)
  // 0 => [0, 0] => 0
  // 0 => [0, 1] => 1
  // 1 => [1, 2] => 1

  val ints: Seq[Int] = Seq(
    10,
    20,
    50,
    300
  )

  val prices: Seq[Price] = Seq(
    Price(10),
    Price(20),
    Price(50),
    Price(300)
  )

  println(sum(ints))
  println(sum(prices))

}
