package com.github.lpld.scala.collections

/**
 * @author leopold
 * @since 16/08/15
 */
object MyArray {
  def apply[ELEM](elems: ELEM*): MyArray[ELEM] = {
    val array = new MyArray[ELEM]
    elems.foreach(array.add)
    array
  }

  implicit def canBuildFrom[ELEM]: MyCanBuildFrom[MyArray[_], ELEM, MyArray[ELEM]] = new MyArrayCanBuildFrom
}

class MyArrayCanBuildFrom[ELEM] extends MyCanBuildFrom[MyArray[_], ELEM, MyArray[ELEM]] {
  override def builder = new MyArrayBuilder
}

class MyArray[ELEM] extends MyCollection[ELEM]
                            with MyCollectionLike[ELEM, MyArray[ELEM]] {

  private val elements = new Array[Any](100)
  var size = 0

  override def newBuilder = new MyArrayBuilder
  override def iterator: MyIterator[ELEM] = new MyArrayIterator

  def add(elem: ELEM) = {
    elements(size) = elem
    size += 1
  }

  private class MyArrayIterator extends MyIterator[ELEM] {
    var idx = 0

    override def hasNext: Boolean = idx < size

    override def next: ELEM = {
      val elem = elements(idx)
      idx += 1
      elem.asInstanceOf[ELEM]
    }
  }
}

class MyArrayBuilder[ELEM] extends MyCollectionBuilder[ELEM, MyArray[ELEM]] {
//  def newArray: MyArray[ELEM] = new MyArray[ELEM]
  val array = new MyArray[ELEM]

  override def add(elem: ELEM): Unit = array.add(elem)
  override def build: MyArray[ELEM] = array
}

