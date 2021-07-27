package com.github.aaabramov.catz.demo

import cats.{Eq, Show}

//noinspection ComparingUnrelatedTypes
object P4_Eq extends App {

  import cats._

  val alex = Person("Alexander", 20)
  val joe = Person("Joe", 30)


  println("alex == joe: " + (alex == joe))
  // This compiles successfully and ignores the type difference
  println("alex != 2: " + (alex != 2))
  println("alex == 2: " + (alex == 2))
  println("alex.name == 14: " + (alex.name == 14))

  // Introducing cats.Eq

//  implicit val personEq: Eq[Person] = new Eq[Person] {
//    override def eqv(x: Person, y: Person): Boolean =
//      x == y
//  }

  implicit val personEq: Eq[Person] = Eq.fromUniversalEquals

  println(personEq.eqv(alex, joe))

  import cats.syntax.all._

  // Better syntax
  println("alex === joe: " + (alex === joe))
  println("alex === joe: " + (alex.eqv(joe)))
  println("alex === joe: " + (alex.neqv(joe)))
//  println("alex === 2: " + (alex === 2)) // Compilation error
  println("alex =!= joe: " + (alex =!= joe))
//  println("alex =!= 2: " + (alex =!= 2)) // Compilation error

}
