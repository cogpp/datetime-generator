package com.cogpp.datetime.generator

import java.time._
import java.time.temporal.TemporalAmount

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
    val durations=stream
      .toList
      .map({ datetime => Duration.between(datetime,ArbitraryTimeShrink.LOCAL_EPOCH) })
      .map({ d:Duration => if (d.isNegative) d.negated() else d})
    val periods=stream
      .toList
      .map({ datetime => Period.between(datetime.toLocalDate,ArbitraryTimeShrink.LOCAL_EPOCH.toLocalDate) })
      .map({p:Period => if(p.isNegative) p.negated() else p})
    val temporalAmounts:List[Either[Period,Duration]] = durations zip periods map {case (d:Duration,p:Period) => if (p.isZero) Right(d) else Left(p)}
    println(temporalAmounts)
    val comparisons=temporalAmounts
      .sliding(2)
      .filter(dur => dur.size == 2)
      .collect {
        case Left(p1) :: Left(p2) :: _ => periodIntProjection(p1) >= periodIntProjection(p2)
        case Right(d1) :: Right(d2) :: _ => d1.compareTo(d2) >= 0
      }
      .toList
    temporalAmounts
      .sliding(2)
      .filter(dur => dur.size == 2)
      .foreach(println)
    println(comparisons.toList)
    comparisons.forall(identity) || comparisons.forall( x => !x)
  }


  /**
    * This gives us an integer value which represents a period. It only promises that the ordering is preserved,
    * not that the number reflects the number of days. This is because we cannot actually know how many days in a month.
    * So we multiply by a number large enough to ensure the ordering is correct.
    * @param p
    * @return
    */
  def periodIntProjection(p:Period) : Long = {
    val pnorm = if(p.isNegative) p.negated() else p
    pnorm.getYears *10000 + pnorm.getMonths * 100 + pnorm.getDays
  }

}
