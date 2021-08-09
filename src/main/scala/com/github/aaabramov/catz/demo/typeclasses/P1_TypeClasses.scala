package com.github.aaabramov.catz.demo.typeclasses

import com.github.aaabramov.catz.demo.Person


object P1_TypeClasses extends App {

  // And they have scaladoc (:
  trait ToString[T] {
    def render(a: T): String
  }

  // type ToString = T => String
  // def show[T](e: T)(implicit TS: ToString)
  // def show[T](e: T)(implicit TS: T => String)

  def show[T](e: T)(implicit TS: ToString[T]): Unit =
    println(TS.render(e))

  implicit object MyImpl extends ToString[Person] {
    override def render(p: Person): String = s"The person name ${p.name} is ${p.age} year(s) old"
  } // "same as" `val myImpl: ToString[Person] = ???`

  // Simple to string implementation
  val basic: ToString[Person] = _.toString

  // Masking the name implementation
  val masking: ToString[Person] = person =>
    person
      .copy(
        name = s"${person.name.head}${"*" * (person.name.length - 1)}" // Joe => J**
      )
      .toString

  val alex = Person("Alexander", 20)

  show(alex)
  show(alex)(masking)

  basic.render(alex)

}
