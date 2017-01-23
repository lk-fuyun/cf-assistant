package com.fuyun.game.cf.actors

import java.awt.Robot

import akka.actor.Actor
import com.fuyun.game.cf.actors.KMController.{MouseMoveTo, MouseWheel}
import com.fuyun.game.common.KMLLib

/**
  * Created by fuyun on 2017/1/18.
  * 键盘鼠标控制器
  */
class KMController extends Actor {

  @scala.throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    KMLLib.INSTANCE.OpenDevice()
  }

  override def receive: Receive = {
    case MouseMoveTo(x, y) =>
      KMLLib.INSTANCE.SimulationMove(x, y)
    case MouseWheel(count: Int) =>
      KMLLib.INSTANCE.MouseWheel(count)
  }

  @scala.throws[Exception](classOf[Exception])
  override def postStop(): Unit = {
    KMLLib.INSTANCE.CloseDevice()
  }
}

object KMController {
  object Shoot
  object FinFire
  case class MouseMoveTo(x: Int, y: Int)

  case class PressKey(key: Char)

  case class MouseWheel(count: Int)

}
