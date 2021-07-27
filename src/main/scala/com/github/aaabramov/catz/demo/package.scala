package com.github.aaabramov.catz

import io.circe.generic.extras.Configuration

package object demo {

  private[demo] implicit val configuration: Configuration = Configuration.default

}
