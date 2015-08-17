package com.github.lpld.scala.collections

/**
 * @author leopold
 * @since 16/08/15
 */

trait MyList[+ELEM] extends MyCollection[ELEM] with MyCollectionLike[ELEM, MyList[ELEM]] {

  def isEmpty: Boolean
  def head: ELEM
  def tail: MyList[ELEM]
  def apply(idx: Int): ELEM

  override def size = {
    val it = iterator
    var size = 0
    while (it.hasNext) {
      it.next
      size += 1
    }
    size
  }

  protected[this] def newBuilder = new MyListBuilder
  override def iterator: MyIterator[ELEM] = new MyListIterator(this)
}

class MyListBuilder[ELEM] extends MyCollectionBuilder[ELEM, MyList[ELEM]] {

  private var start: MyList[ELEM] = Nil
  private var prev: ListItem[ELEM] = null

  override def add(elem: ELEM): Unit = {
    val item = new ListItem(elem, Nil)

    if (start.isEmpty) {
      start = item
    } else {
      prev.tail = item
    }
    prev = item
  }
  override def build: MyList[ELEM] = start
}

class MyListIterator[ELEM](private var list: MyList[ELEM]) extends MyIterator[ELEM] {

  override def hasNext: Boolean = !list.isEmpty

  override def next: ELEM = {
    val elem = list.head
    list = list.tail
    elem
  }
}


object MyList {

  def apply[ELEM](elems: ELEM*): MyList[ELEM] = {
    var item: MyList[ELEM] = Nil
    elems.reverse.foreach(elem => item = new ListItem(elem, item))
    item
  }

  implicit def canBuildFrom[ELEM]: MyCanBuildFrom[MyList[_], ELEM, MyList[ELEM]] = new MyListCanBuildFrom
}

class MyListCanBuildFrom[ELEM] extends MyCanBuildFrom[MyList[_], ELEM, MyList[ELEM]] {
  override def builder = new MyListBuilder
}

object Nil extends MyList[Nothing] {

  override def isEmpty: Boolean = true
  override def head: Nothing = throw new NoSuchElementException
  override def tail: MyList[Nothing] = throw new NoSuchElementException
  override def apply(idx: Int): Nothing = throw new NoSuchElementException
}

private case class ListItem[ELEM](override val head: ELEM, var tail: MyList[ELEM]) extends MyList[ELEM] {

  override def isEmpty: Boolean = false
  override def apply(idx: Int): ELEM = if (idx == 0) head else tail(idx - 1)
}
