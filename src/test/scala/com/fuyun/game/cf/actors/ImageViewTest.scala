package com.fuyun.game.cf.actors

import java.awt.event.{WindowAdapter, WindowEvent}
import java.awt.{Label, Rectangle}
import javax.swing.JFrame

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.fuyun.game.cf.actors.ImageViewTest.Count
import com.fuyun.game.common.KMLLib

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps

/**
  * Created by fuyun on 2017/1/18.
  */
class ImageViewTest(imageDispatcher: ActorRef) extends Actor {
  val frame = new JFrame()
  val label = new Label()
  frame.add(label)
  frame.setVisible(true)
  frame.setAlwaysOnTop(true)
  frame.setBounds(50, 50, 200, 100)
  frame.addWindowListener(new WindowAdapter {

    override def windowClosing(e: WindowEvent): Unit = {
      context.system.shutdown()
    }
  })
  @scala.throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    imageDispatcher ! ImageDispatcher.Subscribe
    context.system.scheduler.schedule(1 second, 1 second) {
      self ! Count
    }
  }
  var frameCount = 0
  override def receive: Receive = {
    case ImageDispatcher.OneCapture(image) =>
      frameCount += 1
    case Count =>
      label.setText(f"$frameCount fps")
      frameCount = 0
  }
}
object ImageViewTest {
  private object Count

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("test-system")
    val imageDispatcher = system.actorOf(Props[ImageDispatcher], "imageDispatcher")
    val screenCapture = system.actorOf(Props(classOf[ScreenCapture], imageDispatcher), "screenCapture")
    system.actorOf(Props(classOf[ImageViewTest], imageDispatcher), "testActor")
    screenCapture ! ScreenCapture.SetRectangle(new Rectangle(0, 0, 800, 600))
    screenCapture ! ScreenCapture.StartCapture
  }
}
object KMCTest extends App {
  KMLLib.INSTANCE.OpenDevice()
  KMLLib.INSTANCE.SimulationMove(0, 500)
  val times = 50
  val startTime = System.currentTimeMillis()
  for (elem <- 1 to times) {
    val p = 50 * (elem % 2)
    KMLLib.INSTANCE.SimulationMove(p, 500)
  }
  val stop = System.currentTimeMillis()
  println(s"${(stop - startTime) / times} ms/require")
  KMLLib.INSTANCE.CloseDevice()
}
object MTest extends App {
  KMLLib.INSTANCE.OpenDevice()
  KMLLib.INSTANCE.KeyPress("q", 2)
  KMLLib.INSTANCE.CloseDevice()
}
