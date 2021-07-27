package com.github.aaabramov.catz.demo

case class Price(amount: Int) extends AnyVal {
  override def toString: String = "$" + amount
}
