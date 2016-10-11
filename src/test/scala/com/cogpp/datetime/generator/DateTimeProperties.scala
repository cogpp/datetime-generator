package com.cogpp.datetime.generator

import java.time._

import com.cogpp.datetime.generator.ArbitraryTime._
import org.scalacheck.Prop._
import org.scalacheck.Properties




object DateTimeProperties extends Properties("DateTime") {

  /*
  There are many properties here which seem to do nothing.
  What they check is that, an appropriate implict Arbitary[T]
  is in scope, and that it can generate many values without
  producing an exception.
   */

  property("localDateTime") = forAll { (dt: LocalDateTime) =>
    dt == dt
  }

  property("month") = forAll { (m: Month) =>
    m == m
  }

  property("localDate") = forAll { (d: LocalDate) =>
    d == d
  }

  property("localDuration") = forAll { (d: Duration) =>
    d == d
  }

  property("localPeriod") = forAll { (p: Period) =>
    p == p
  }

  property("Instant") = forAll { (i:Instant) =>
    i == i
  }

  property("localTime") = forAll { (i:LocalTime) =>
    i == i
  }

  property("zoneId") = forAll { (zid:ZoneId) =>
    zid == zid
  }

  property("zoneOffset") = forAll { (z:ZoneOffset) =>
    z == z
  }

  property("offsetDateTime") = forAll { (odt:OffsetDateTime) =>
    odt == odt
  }

  property("offsetTime") = forAll { (ot:OffsetTime) =>
    ot == ot
  }

  property("year") = forAll { (y:Year) =>
    y == y
  }

  property("yearMonth") = forAll { (ym:YearMonth) =>
    ym == ym
  }

  property("zonedDateTime") = forAll { (zdt:ZonedDateTime) =>
    zdt == zdt
  }

  property("dayOfWeek") = forAll { (dow:DayOfWeek) =>
    dow == dow
  }
}
