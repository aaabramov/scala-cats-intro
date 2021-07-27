package com.github.aaabramov.catz.demo

import cats.Show

object P3_Show extends App {

  import cats.syntax.all._

  val alex = Person("Alexander", 20)

  //  implicit val personShow: Show[Person] = new Show[Person] {
  //    override def show(person: Person): String = person.toString
  //  }

  implicit val personShow: Show[Person] = Show.fromToString

  val maskedPersonShow: Show[Person] = person =>
    person
      .copy(
        name = s"${person.name.head}${"*" * (person.name.length - 1)}" // Joe => J**
      )
      .toString

  // 1: Using custom helper <- not recommended
  def show[T](t: T)(implicit S: Show[T]): String =
    S.show(t)

  // 2: Using cats-provided helper (AKA cats syntax)
  alex.show

  // Running
  println(show(alex))
  println(alex.show)
  println(show(alex)(maskedPersonShow))

}
