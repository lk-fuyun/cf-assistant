package com.fuyun.game.cf.actors.weapons

import java.awt.image.BufferedImage

import akka.actor.{Actor, Cancellable, Props}
import com.fuyun.game.cf.actors.{ImageDispatcher, KMController}

import scala.concurrent.duration._
import scala.language.postfixOps

/**
  * Created by fuyun on 2017/1/24.
  */
class Rifle extends Actor {
  val kMController = context.actorOf(Props[KMController], "kmController")
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
        context.parent ! KMController.StopTouchFire
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
        context.parent ! KMController.StartTouchFire
      }
  }

  @scala.throws[Exception](classOf[Exception])
  override def postStop(): Unit = {
    timerController.cancel()
  }
}
