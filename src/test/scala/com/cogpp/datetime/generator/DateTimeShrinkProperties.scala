package com.cogpp.datetime.generator

import java.time._

import com.cogpp.datetime.generator.ArbitraryTime._
import org.scalacheck.Prop._
import org.scalacheck.Properties


object DateTimeShrinkProperties extends Properties("DateTime") {

  property("terminates (this test never terminates on failure!)") = forAll { (dateTime: OffsetDateTime) =>
    ArbitraryTimeShrink.timeStream(dateTime).filter(_ => false)
    true
  }


}
