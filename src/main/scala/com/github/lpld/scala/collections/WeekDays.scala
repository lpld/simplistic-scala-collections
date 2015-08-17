package com.github.lpld.scala.collections

/**
 * @author leopold
 * @since 17/08/15
 */
object WeekDays {
  def apply(days: Int*): WeekDays = {
    val wd = new WeekDays
    days.foreach(d => {
      if (d < 1 || d > 7) {
        throw new Error
      }
      wd.add(d)
    })

    wd
  }

  def isWeekend(day: Int) = day == 6 || day == 7
  def isWeekday(day: Int) = !isWeekend(day)

  implicit def canBuildFrom: MyCanBuildFrom[WeekDays, Int, WeekDays] = new WeekDaysCanBuildFrom
}

class WeekDays extends MyArray[Int] with MyCollectionLike[Int, WeekDays] {

  override def newBuilder = new WeekDaysBuilder

  def validate(): Unit = {}

}

class WeekDaysBuilder extends MyArrayBuilder[Int] with MyCollectionBuilder[Int, WeekDays] {

  override def build: WeekDays = array
  override val array: WeekDays = new WeekDays
}

class WeekDaysCanBuildFrom extends MyCanBuildFrom[WeekDays, Int, WeekDays] {
  override def builder = new WeekDaysBuilder
}