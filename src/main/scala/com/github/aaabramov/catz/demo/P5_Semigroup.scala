package com.github.aaabramov.catz.demo

import cats.Semigroup

object P5_Semigroup extends App {

  // Semigroup
  // - def combine(x, y)

  implicit val intSemigroup: Semigroup[Int] = new Semigroup[Int] {
    override def combine(x: Int, y: Int): Int =
      x + y
  }

  implicit val priceSemigroup: Semigroup[Price] = (x, y) => Price(x.amount + y.amount)

  import cats.syntax.semigroup._
  println(
    Price(100) |+| Price(200)
  )

}
