package com.fuyun.game.cf.actors

import java.awt.Rectangle
import java.awt.image.BufferedImage
import com.fuyun.game.cf.utils.ImproveAwt._

/**
  * Created by fuyun on 2017/1/24.
  */
package object weapons {
  val peopleNameRect = new Rectangle(479, 425, 68, 16)
  val mask = 0xff

  implicit val executionContext = scala.concurrent.ExecutionContext.Implicits.global

  def isInRange(implicit image: BufferedImage): Boolean = {
    val bCount = peopleNameRect.count(p => image.getRGB(p.x, p.y) == 0xff000000)
    val rCount = peopleNameRect.count { p =>
      val rgb = image.getRGB(p.x, p.y)
      (rgb & mask) < 60 &&
        ((rgb >> 8) & mask) < 80 &&
        ((rgb >> 16) & mask) > 150
    }
    bCount > 15 && rCount > 20
  }
}
