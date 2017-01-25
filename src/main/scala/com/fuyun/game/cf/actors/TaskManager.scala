package com.fuyun.game.cf.actors

import java.awt.Rectangle

import akka.actor.SupervisorStrategy.Stop
import akka.actor.{Actor, Props}
import com.fuyun.game.cf.actors.TaskManager.Start

/**
  * Created by fuyun on 2017/1/18.
  */
class TaskManager extends Actor {
  val imageDispatcher = context.actorOf(Props[ImageDispatcher], "imageDispatcher")
  val screenCapture = context.actorOf(Props(classOf[ScreenCapture], imageDispatcher), "screenCapture")
  val controller = context.actorOf(Props(new Controller(imageDispatcher)), "controller")
  val weaponMonitor = context.actorOf(Props(new WeaponMonitor(imageDispatcher, controller)), "weaponMonitor")

  override def receive: Receive = {
    case Start =>
      screenCapture ! ScreenCapture.SetRectangle(new Rectangle(0, 0, 1024, 768))
      screenCapture ! ScreenCapture.StartCapture
    case Stop =>
      screenCapture ! ScreenCapture.StopCapture
  }

  @scala.throws[Exception](classOf[Exception])
  override def postStop(): Unit = {
    screenCapture ! ScreenCapture.StopCapture
  }
}

object TaskManager {

  object Start

}
