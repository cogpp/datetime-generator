package com.cogpp.datetime.generator

import java.time.temporal.{ChronoField, Temporal, TemporalField}
import java.time.{OffsetDateTime, ZoneOffset}

import org.scalacheck.Shrink



/**
  * Created by phillg07 on 12/10/2016.
  */
object ArbitraryTimeShrink {

  /**
    * The machine epoch cannot easily be converted to a local epoch. see: http://bit.ly/2dRbWBZ
    */
  val LOCAL_EPOCH = OffsetDateTime.of(1970,1,1,0,0,0,0,ZoneOffset.UTC)

  def fieldsToChange:List[TemporalField] = List(
    ChronoField.YEAR,
    ChronoField.MONTH_OF_YEAR,
    ChronoField.DAY_OF_MONTH,
    ChronoField.HOUR_OF_DAY,
    ChronoField.MINUTE_OF_HOUR,
    ChronoField.SECOND_OF_MINUTE,
    ChronoField.MILLI_OF_SECOND)

  def timeStream(time:OffsetDateTime):Stream[OffsetDateTime] = {
    time match {
      case LOCAL_EPOCH => Stream.empty
      case t if t.getOffset != ZoneOffset.UTC &&
                t.getYear < OffsetDateTime.MAX.getYear &&
                t.getYear > OffsetDateTime.MIN.getYear =>  nextStreamItem(t, _.withOffsetSameInstant(ZoneOffset.UTC), timeStream)
      case t => nextStreamItem(t, stepCloser, timeStream)

    }
  }

  def nextStreamItem(time: OffsetDateTime,curStep:OffsetDateTime=>OffsetDateTime, nextStep:OffsetDateTime=>Stream[OffsetDateTime]):Stream[OffsetDateTime] = {
    val curTime=curStep(time)
    Stream.cons(curTime, nextStep(curTime))
  }

  def stepCloser(time:OffsetDateTime):OffsetDateTime = {
    firstDifferentField(time).map(f => halveField(time,f)).getOrElse(LOCAL_EPOCH)
  }

  def firstDifferentField(time:OffsetDateTime):Option[TemporalField] = {
    val fields:List[(Int, Int, TemporalField)] = (fieldsToChange map { f=> time.get(f)} , fieldsToChange map {f => LOCAL_EPOCH.get(f)} , fieldsToChange).zipped.toList
    fields.find( f => f._1 != f._2 ).map(_._3)
  }

  def halveField(time:OffsetDateTime,field:TemporalField): OffsetDateTime = {
    time.`with`(field,((time.get(field) - LOCAL_EPOCH.get(field)) / 2) + LOCAL_EPOCH.get(field))
  }


  implicit def shrinkOffsetDateTime:Shrink[OffsetDateTime] = Shrink { (time: OffsetDateTime) => timeStream(time) }
}
