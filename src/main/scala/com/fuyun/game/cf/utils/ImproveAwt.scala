package com.fuyun.game.cf.utils

import java.awt.Rectangle

/**
  * Created by fuyun on 2017/1/19.
  */
object ImproveAwt {

  implicit class RichRectangle(rect: Rectangle) {
    def xRange: Range = rect.x to (rect.x + rect.width)

    def yRange: Range = rect.y to (rect.y + rect.height)
  }

}
