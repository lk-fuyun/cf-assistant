package com.fuyun.game.cf.actors

import java.awt.Robot

import akka.actor.Actor
import com.fuyun.game.cf.actors.KMController.MouseMoveTo
import com.fuyun.game.common.KMLLib

/**
  * Created by fuyun on 2017/1/18.
  * 键盘鼠标控制器
  */
class KMController extends Actor {
  val robot = new Robot()
  @scala.throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
//    KMLLib.INSTANCE.OpenDevice()
  }

  override def receive: Receive = {
    case MouseMoveTo(x, y) =>
//      KMLLib.INSTANCE.SimulationMove(x, y)
    robot.mouseMove(x, y)
  }
}
object KMController {
  case class MouseMoveTo(x: Int, y: Int)
}
