package com.fuyun.game.cf.utils

import java.awt.{Point, Rectangle}

/**
  * Created by fuyun on 2017/1/19.
  */
object ImproveAwt {

  implicit class RichRectangle(rect: Rectangle) extends Iterable[Point] {
    def xRange: Range = rect.x to (rect.x + rect.width)

    def yRange: Range = rect.y to (rect.y + rect.height)

    def foreachPoint(func: (Int, Int) => Unit): Unit = {
      for {
        x <- xRange
        y <- yRange
      } {
        func(x, y)
      }
    }

    class PointIter extends Iterator[Point] {
      var cursor = 0
      val maxPlace = rect.width * rect.height

      override def hasNext: Boolean = {
        cursor < maxPlace
      }

      override def next(): Point = {
        val p = new Point(rect.x + cursor % rect.width, rect.y + cursor / rect.width)
        cursor += 1
        p
      }
    }

    override def iterator: Iterator[Point] = {
      new PointIter
    }
  }

}
object Test extends App {
  import ImproveAwt._
  new Rectangle(0, 0, 2, 2).foreach(println)
}
