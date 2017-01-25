package com.fuyun.game.cf.actors

import akka.actor.{Actor, Cancellable}
import com.fuyun.game.common.KMLLib

import scala.concurrent.duration._
import scala.language.postfixOps
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by fuyun on 2017/1/18.
  * 键盘鼠标控制器
  */
class KMController extends Actor {

  import KMController._

  val kml = KMLLib.INSTANCE

  @scala.throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    kml.OpenDevice()
  }

  @scala.throws[Exception](classOf[Exception])
  override def postStop(): Unit = {
    kml.CloseDevice()
  }

  private object Tick

  var timeController: Cancellable = _

  var touchTimes = 0
  val touchFiring: Receive = {
    case Tick =>
      touchTimes += 1
      if (touchTimes >= 5) {
        touchTimes = -3
      }
      if (touchTimes >= 0) {
        kml.LeftClick(1)
      }
    case StopTouchFire =>
      context.unbecome()
      timeController.cancel()
  }

  val firing: Receive = {
    case StopFire =>
      kml.LeftUp()
      context.unbecome()
  }
  object CB
  override def receive: Receive = {
    case Shoot =>
      kml.LeftClick(1)
    case StartTouchFire =>
      context.become(touchFiring, discardOld = false)
      timeController = context.system.scheduler.schedule(0 milli, 100 milli)(self ! Tick)
    case TakeBackSniper =>
      kml.KeyPress("3", 1)
      context.system.scheduler.scheduleOnce(300 millis)(self ! CB)
    case CB =>
      kml.KeyPress("q", 1)
    case StartFire =>
      kml.LeftDown()
      context.become(firing, discardOld = false)
  }
}

object KMController {

  object Shoot

  object StartTouchFire

  object StopTouchFire

  object StartFire

  object StopFire

  object TakeBackSniper

}
