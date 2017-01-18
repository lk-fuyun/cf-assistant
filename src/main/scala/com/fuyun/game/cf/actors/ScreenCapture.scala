package com.fuyun.game.cf.actors

import java.awt.{Rectangle, Robot}

import akka.actor.{Actor, ActorRef}

/**
  * Created by fuyun on 2017/1/18.
  */
class ScreenCapture(imageDispatcher: ActorRef) extends Actor {

  import ScreenCapture._

  private var rect = new Rectangle()
  private val robot = new Robot()
  private val capturing: Receive = {
    case CaptureOnce =>
      val image = robot.createScreenCapture(rect)
      imageDispatcher ! ImageDispatcher.OneCapture(image)
      self ! CaptureOnce
    case StopCapture =>
      context.unbecome()
  }

  override def receive: Receive = {
    case SetRectangle(r) =>
      rect = new Rectangle(r)
    case StartCapture =>
      context.become(capturing, discardOld = false)
      self ! CaptureOnce
  }
}

object ScreenCapture {

  case class SetRectangle(rect: Rectangle)

  object StartCapture

  object StopCapture

  private object CaptureOnce

}
