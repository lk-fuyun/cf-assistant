package com.fuyun.game.cf.actors

import java.awt.image.BufferedImage

/**
  * Created by fuyun on 2017/1/24.
  */
package object weapons {

  def isInRange(implicit image: BufferedImage): Boolean = {
    ???
  }

  object Action {

    // 只开一发，适用于狙击枪,手枪
    object Shoot

    object TakeBackSniper

    object StartFire

    object StopFire

  }

}
