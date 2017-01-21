package com.fuyun.game.cf.actors

import akka.actor.{Actor, ActorRef}
import akka.actor.Actor.Receive

/**
  * Created by fuyun on 2017/1/18.
  */
class WeaponMonitor(imageDispatcher: ActorRef) extends Actor {

  @scala.throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    imageDispatcher ! ImageDispatcher.Subscribe
  }

  override def receive: Receive = {
    case ImageDispatcher.OneCapture(image) =>

  }
}
object WeaponMonitor {
  object Subscribe
  case class WeaponChange()
}
