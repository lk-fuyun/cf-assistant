package com.fuyun.game.cf

import java.awt.Rectangle

import akka.actor.{ActorSystem, Props}
import com.fuyun.game.cf.actors._

/**
  * Created by fuyun on 2017/1/18.
  */
object CfAssistant {
  val system = ActorSystem("test-system")
  val imageDispatcher = system.actorOf(Props[ImageDispatcher], "imageDispatcher")
  val screenCapture = system.actorOf(Props(classOf[ScreenCapture], imageDispatcher), "screenCapture")
  val kMController = system.actorOf(Props[KMController], "KMController")
  val moveDetector = system.actorOf(Props(new MoveDetector(imageDispatcher)), "moveDetector")

  def main(args: Array[String]): Unit = {
    test()
  }

  def test(): Unit = {
    val p = system.actorOf(Props(new PersonController(moveDetector, kMController)), "personController")
    p ! PersonController.Test
    screenCapture ! ScreenCapture.SetRectangle(new Rectangle(0, 0, 1024, 768))
    screenCapture ! ScreenCapture.StartCapture
  }
}
