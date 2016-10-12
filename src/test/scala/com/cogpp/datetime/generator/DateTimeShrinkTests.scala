package com.cogpp.datetime.generator

import java.time.{Instant, OffsetDateTime}

import org.scalatest.FlatSpec


class DateTimeShrinkTests extends FlatSpec {
  "The time shrink stream" should "terminate on reaching the epoch" in {
    ArbitraryTimeShrink.timeStream(ArbitraryTimeShrink.LOCAL_EPOCH) == Stream.empty
  }
}