package com.cogpp.datetime.generator

import java.time.{Duration, Instant, LocalDateTime, OffsetDateTime}

import com.cogpp.datetime.generator.ArbitraryTime._
import org.scalacheck.Prop._
import org.scalacheck.Properties


object DateTimeShrinkProperties extends Properties("DateTime") {

  property("terminates (this test never terminates on failure!)") = forAll { (dateTime: OffsetDateTime) =>
    ArbitraryTimeShrink.timeStream(dateTime).filter(_ => false)
    true
  }

  property("gets closer to local epoch") = forAll { (dateTime: OffsetDateTime) =>
    val stream = ArbitraryTimeShrink.timeStream(dateTime)
    val adurations=stream
      .toList
      .map({ datetime => Duration.between(datetime,ArbitraryTimeShrink.LOCAL_EPOCH) })
      .sliding(2)
      .filter(dur => dur.size == 2)
      .toList

    println(adurations.toList)
    println(adurations.find(durations => durations(0).compareTo(durations(1)) >= 0))
    println(adurations.find(durations => durations(0).compareTo(durations(1)) <= 0))
    adurations.forall({ durations => durations(0).compareTo(durations(1)) >= 0}) || adurations.forall({ durations => durations(0).compareTo(durations(1)) <= 0})
  }

}
