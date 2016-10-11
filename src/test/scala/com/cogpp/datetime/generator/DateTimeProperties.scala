package com.cogpp.datetime.generator

import java.time._

import com.cogpp.datetime.generator.ArbitaryTime._
import org.scalacheck.Prop.forAll
import org.scalacheck.Properties




object DateTimeProperties extends Properties("DateTime") {

  /*
  There are many properties here which seem to do nothing.
  What they check is that, an appropriate implict Arbitary[T]
  is in scope, and that it can generate many values without
  producing an exception.
   */

  property("localDateTime") = forAll { (dt: LocalDateTime) =>
    true
  }

  property("month") = forAll { (m: Month) =>
    true
  }

  property("localDate") = forAll { (d: LocalDate) =>
    true
  }

  property("localDuration") = forAll { (d: Duration) =>
    true
  }

  property("localPeriod") = forAll { (d: Period) =>
    true
  }

  property("Instant") = forAll { (i:Instant) =>
    true
  }

  property("localTime") = forAll { (i:LocalTime) =>
    true
  }

  property("zoneId") = forAll { (zid:ZoneId) =>
    true
  }

  property("zoneOffset") = forAll { (z:ZoneOffset) =>
    true
  }
}
