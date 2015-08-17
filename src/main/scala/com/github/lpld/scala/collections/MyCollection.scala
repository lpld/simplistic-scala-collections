package com.github.lpld.scala.collections

/**
 * @author leopold
 * @since 16/08/15
 */
trait MyCollection[+ELEM] extends MyCollectionLike[ELEM, MyCollection[ELEM]] {
  def size: Int
}

object MyCollection {
  implicit def canBuildFrom[E]: MyCanBuildFrom[MyCollection[_], E, MyCollection[E]]
  = new MyCollectionCanBuildFrom
}

trait MyCollectionLike[+ELEM, +REPR] {
  def iterator: MyIterator[ELEM]

  protected[this] def newBuilder: MyCollectionBuilder[ELEM, REPR]

  def foreach[X](f: ELEM => X): Unit = {
    val it = iterator
    while (it.hasNext) f(it.next)
  }

  def filter(f: ELEM => Boolean): REPR = {
    val builder = newBuilder
    foreach(e => if (f(e)) builder.add(e))
    builder.build
  }

  def map[NEW_ELEM, NEW_COL](f: ELEM => NEW_ELEM)(implicit cbf: MyCanBuildFrom[REPR, NEW_ELEM, NEW_COL]): NEW_COL = {
    val builder: MyCollectionBuilder[NEW_ELEM, NEW_COL] = cbf.builder
    foreach(e => builder.add(f(e)))
    builder.build
  }
}

trait MyIterator[+ELEM] {
  def hasNext: Boolean
  def next: ELEM
}

trait MyCollectionBuilder[-ELEM, +REPR] {
  def add(elem: ELEM): Unit
  def build: REPR
}

trait MyCanBuildFrom[-FROM, -ELEM, +TO] {
  def builder: MyCollectionBuilder[ELEM, TO]
}

class MyCollectionCanBuildFrom[ELEM] extends MyCanBuildFrom[MyCollection[_], ELEM, MyCollection[ELEM]] {

  // default - array
  override def builder: MyCollectionBuilder[ELEM, MyCollection[ELEM]] = new MyArrayBuilder
}
