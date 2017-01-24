package com.fuyun.game.cf.actors.weapons

import java.awt.image.BufferedImage

import akka.actor.{Actor, Cancellable}
import com.fuyun.game.cf.actors.ImageDispatcher

import scala.concurrent.duration._
import scala.language.postfixOps

/**
  * Created by fuyun on 2017/1/24.
  */
class Rifle extends Actor {
  var curImage: BufferedImage = _

  object Tick
  var timerController: Cancellable = _
  var inRangeCount = 0
  val fighting: Receive = {
    case ImageDispatcher.OneCapture(image) =>
      if (isInRange(image)) {
        inRangeCount += 1
      }
    case Tick =>
      if (inRangeCount == 0) {
        context.unbecome()
        timerController.cancel()
        context.parent ! Action.StopFire
      }
      inRangeCount = 0
  }

  override def receive: Receive = {
    case ImageDispatcher.OneCapture(image) =>
      if (isInRange(image)) {
        context.become(fighting, discardOld = false)
        timerController = context.system.scheduler.schedule(400 milli, 400 milli) {
          self ! Tick
        }
        context.parent ! Action.StartFire
      }
  }

  @scala.throws[Exception](classOf[Exception])
  override def postStop(): Unit = {
    timerController.cancel()
  }
}
