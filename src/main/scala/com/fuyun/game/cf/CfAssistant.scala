package com.fuyun.game.cf

import java.awt.Rectangle

import akka.actor.{ActorSystem, Props}
import com.fuyun.game.cf.actors._
import com.fuyun.game.common.KMLLib

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
    mouseTest()
  }

  def test(): Unit = {
    val p = system.actorOf(Props(new PersonController(moveDetector, kMController)), "personController")
    p ! PersonController.Test
    screenCapture ! ScreenCapture.SetRectangle(new Rectangle(0, 0, 1024, 768))
    screenCapture ! ScreenCapture.StartCapture
  }
  def mouseTest(): Unit = {
    KMLLib.INSTANCE.OpenDevice()
    KMLLib.INSTANCE.SimulationMove(100, 100)
    Thread.sleep(500)
    for (elem <- 0 to 100) {
      KMLLib.INSTANCE.MoveToR(2, 0)
    }
    Thread.sleep(1000)
    KMLLib.INSTANCE.MoveToR(0, 100)
  }
}
