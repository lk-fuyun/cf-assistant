package com.fuyun.game.cf

import java.awt.{Button, Graphics, Rectangle}
import java.awt.event.{ActionEvent, ActionListener, MouseEvent, MouseMotionAdapter}
import javax.swing.JFrame

import akka.actor.{ActorSystem, Props}
import com.fuyun.game.cf.actors.{Controller, _}
import com.fuyun.game.common.KMLLib

import scala.collection.mutable

/**
  * Created by fuyun on 2017/1/18.
  */
object CfAssistant {
  val system = ActorSystem("test-system")
  val imageDispatcher = system.actorOf(Props[ImageDispatcher], "imageDispatcher")
  val screenCapture = system.actorOf(Props(classOf[ScreenCapture], imageDispatcher), "screenCapture")

  def main(args: Array[String]): Unit = {
    testMouse()
  }
  def testMouse(): Unit = {
    val eventList = new mutable.MutableList[MouseEvent]()
    val frame = new JFrame() {
      override def paint(g: Graphics): Unit = {
        eventList.foreach(e => g.drawOval(e.getX, e.getY, 3, 3))
      }
    }
//    val b = new Button("qc")
//    b.addActionListener(new ActionListener {
//      override def actionPerformed(e: ActionEvent): Unit =
//    })
    frame.setBounds(0, 0, 1024, 768)
    frame.addMouseMotionListener(new MouseMotionAdapter {
      override def mouseDragged(e: MouseEvent): Unit = {
        eventList += e
        frame.repaint()
      }
    })
    frame.setVisible(true)
  }

  def test(): Unit = {
    val p = system.actorOf(Props(new Controller(imageDispatcher)), "personController")
    p ! Controller.Test
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
