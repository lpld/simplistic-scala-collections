package com.github.lpld.scala.collections

/**
 * @author leopold
 * @since 16/08/15
 */
object SimpleCollections {

  def main(args: Array[String]) {

    // array of strings
    val ar = MyArray("1", "2", "3", "4", "5", "6")

    // MyArray is transformed to MyArray
    val evens = ar.map(_.toInt)
      .filter(_ % 2 == 0)

    evens.add(8)

    println("Even numbers filtered:")
    evens.foreach(println)

    // list of ints
    val ar2 = MyList(1, 2, 3, 4, 5, 6)

    // MyList transformed to MyList
    val num = ar2.map(_ * 2).filter(_ % 3 == 0).head

    println("first number divided by 3: " + num)

    val wd = WeekDays(1, 3, 5, 7)

    // WeekDays is transformed to WeekDays
    val wd1 = wd.map(_ * 2)
    wd1.validate()

    // WeekDays is transformed to MyArray
    val stringWd = wd.map(_.toString)
    stringWd.foreach(println)


    val wd2: MyCollection[Int] = wd1

    // this still doesn't work right: this should produce WeekDays but it produced MyArray
    // because the compile-time type of the collection is MyCollection
    val res = wd2.map(_ * 3)

    println(res)
  }
}

class A
class B extends A













