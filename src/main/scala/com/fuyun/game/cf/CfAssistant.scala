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

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("test-system")
    val taskManager = system.actorOf(Props[TaskManager], "taskManager")
    taskManager ! TaskManager.Start
  }
}
