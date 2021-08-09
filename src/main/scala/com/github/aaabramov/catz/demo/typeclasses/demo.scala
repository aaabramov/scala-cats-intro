package com.github.aaabramov.catz.demo

import io.circe.generic.extras.Configuration

package object typeclasses {

  private[demo] implicit val configuration: Configuration = Configuration.default

}
