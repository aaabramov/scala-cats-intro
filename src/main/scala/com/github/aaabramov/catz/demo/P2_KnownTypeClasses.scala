package com.github.aaabramov.catz.demo

import io.circe.Codec
import io.circe.generic.extras.semiauto.deriveConfiguredCodec

object P2_KnownTypeClasses extends App {

  trait ToString[T] {
    def render(a: T): String
  }

  object Person {
    implicit val codec: Codec.AsObject[Person] = deriveConfiguredCodec
  }

  // scala.math.Ordered
  // scala.math.Ordering
  // scala.math.Integral

  // scala.collection.generic.CanBuildFrom

  // play.api.libs.json.Format

  // spray.json.JsonFormat

  type Json = String

  trait JsonFormat[T] {
    def toJson(e: T): Json
    def fromJson(json: Json): T
  }


}
