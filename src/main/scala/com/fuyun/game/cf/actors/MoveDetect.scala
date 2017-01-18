package com.fuyun.game.cf.actors

import java.awt.Rectangle
import java.awt.image.Raster

import akka.actor.{Actor, ActorRef}

/**
  * Created by fuyun on 2017/1/18.
  */
class MoveDetect(imageDispatcher: ActorRef) extends Actor {

  @scala.throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    imageDispatcher ! ImageDispatcher.SubscribeRect(new Rectangle(10, 10, 20, 20), self)
  }
  var preRaster: Raster = _
  override def receive: Receive = {
    case ImageDispatcher.RectData(raster) =>
  }
}
