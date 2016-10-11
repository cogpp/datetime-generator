package com.cogpp.datetime.generator

import java.time
import java.time._

import org.scalacheck.{Arbitrary, Gen}

import scala.collection.JavaConverters._



/**
  * A collection of implicat vals you can use to property test with java.time
  */
object ArbitaryTime {

  lazy val uniformMonth:Gen[Month]  = Gen.oneOf(Month.values())

  lazy val uniformPeriod:Gen[Period] = Gen.choose(Int.MinValue,Int.MaxValue) map { d => Period.ofDays(d) }

  lazy val uniformDuration:Gen[Duration] = Gen.choose(Long.MinValue,Long.MaxValue) map { n => Duration.ofNanos(n) }

  lazy val uniformLocalDateTime:Gen[LocalDateTime] =  uniformDuration map {n:Duration => LocalDateTime.of(1970,Month.JANUARY,1,0,0,0,0).plus(n)}

  lazy val uniformLocalDate:Gen[LocalDate] = uniformPeriod map {d => LocalDate.of(1970,Month.JANUARY,1).plus(d) }

  lazy val uniformLocalTime:Gen[LocalTime] = Gen.choose( 0, 24 * 60 * 60 * 1000000000 - 1) map {n => LocalTime.ofNanoOfDay(n)}

  lazy val uniformInstant:Gen[Instant] = for {
    epochSecond <- Gen.choose(Long.MinValue,Long.MaxValue)
    nanoAdjustment <- Gen.choose(Long.MinValue,Long.MaxValue)
  } yield Instant.ofEpochSecond(epochSecond,nanoAdjustment)

  lazy val uniformMonthDay:Gen[MonthDay] = for {
    month <- uniformMonth
    leapYear <- Gen.oneOf(true, false)
    dayOfMonth <- Gen.choose(month.minLength(),month.maxLength())
  } yield MonthDay.of(month,dayOfMonth)

  lazy val uniformZoneId:Gen[ZoneId] = Gen.oneOf(ZoneId.getAvailableZoneIds.asScala.toSeq) map { zid => ZoneId.of(zid)}

  lazy val uniformZoneOffset:Gen[ZoneOffset] = for {
    hours <- Gen.choose(-18,18)
    //The mins and secs must have the same sign as hours and not go over 18 hours.
    maxTicks = 59 * (if (hours >=0) 1 else -1) * (if (math.abs(hours) >= 18) 0 else 1)
    minutes <- Gen.choose(0,maxTicks)
    seconds <- Gen.choose(0,maxTicks)
  } yield ZoneOffset.ofHoursMinutesSeconds(hours,minutes,seconds)

  lazy val uniformOffsetDateTime:Gen[OffsetDateTime] = for {
    localDateTime <- uniformLocalDateTime
    zoneOffset <- uniformZoneOffset
  } yield OffsetDateTime.of(localDateTime,zoneOffset)

  lazy val uniformOffsetTime:Gen[OffsetTime] = for {
    localTime <- uniformLocalTime
    zoneOffset <- uniformZoneOffset
  } yield OffsetTime.of(localTime,zoneOffset)

  implicit lazy val arbitraryMonth:Arbitrary[Month] = Arbitrary(uniformMonth)

  implicit lazy val arbitraryDuration:Arbitrary[Duration] = Arbitrary(Gen.frequency(
    (1,Duration.ZERO),
    (1,uniformDuration)
  ))

  implicit lazy val arbitraryPeriod:Arbitrary[Period] = Arbitrary(Gen.frequency(
    (1,Period.ZERO),
    (1,uniformPeriod)
  ))

  implicit lazy val arbitaryLocalDateTime:Arbitrary[LocalDateTime] = Arbitrary(Gen.frequency(
      (1,LocalDateTime.MIN),
      (1,LocalDateTime.MAX),
      (1,uniformLocalDateTime)))

  implicit lazy val arbitraryLocalDate:Arbitrary[LocalDate] = Arbitrary(Gen.frequency(
    (1,LocalDate.MIN),
    (1,LocalDate.MAX),
    (1,uniformLocalDate)
  ))

  implicit lazy val artbitaryInstatnt:Arbitrary[Instant] = Arbitrary(Gen.frequency(
    (1,Instant.EPOCH),
    (1,Instant.MAX),
    (1,Instant.MIN)
  ))

  implicit lazy val arbitaryTime:Arbitrary[LocalTime] = Arbitrary(Gen.frequency(
    (1,LocalTime.MAX),
    (1,LocalTime.MIN),
    (1,LocalTime.MIDNIGHT),
    (1,LocalTime.NOON),
    (1,uniformLocalTime)
  ))

  implicit lazy val arbitaryMonthDay:Arbitrary[MonthDay] = Arbitrary(uniformMonthDay)

  implicit lazy val arbitraryZoneId:Arbitrary[ZoneId] = Arbitrary(uniformZoneId)

  implicit lazy val arbitraryZoneOffset:Arbitrary[ZoneOffset] = Arbitrary(Gen.frequency(
    (1,ZoneOffset.MAX),
    (1,ZoneOffset.MIN),
    (1,ZoneOffset.UTC),
    (1,uniformZoneOffset)
  ))

  implicit lazy val arbitaryOffsetDateTime:Arbitrary[OffsetDateTime] = Arbitrary(Gen.frequency(
    (1,OffsetDateTime.MAX),
    (1,OffsetDateTime.MIN),
    (1,uniformOffsetDateTime)
  ))

  implicit lazy val arbitraryOffsetTime:Arbitrary[OffsetTime] = Arbitrary(Gen.frequency(
    (1,OffsetTime.MIN),
    (1,OffsetTime.MAX),
    (1,uniformOffsetTime)
  ))
}
