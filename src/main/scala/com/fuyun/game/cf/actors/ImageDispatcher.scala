package com.fuyun.game.cf.actors

import java.awt.Rectangle
import java.awt.image.{BufferedImage, Raster}

import akka.actor.{Actor, ActorRef}
import com.fuyun.game.cf.actors.ImageDispatcher.{OneCapture, RectData, SubscribeRect}

import scala.collection.mutable

/**
  * Created by fuyun on 2017/1/18.
  */
class ImageDispatcher extends Actor {
  val subscribers = mutable.MutableList[(Rectangle, ActorRef)]()

  override def receive: Receive = {
    case SubscribeRect(rect, subscriber) =>
      subscribers += ((rect, subscriber))
    case OneCapture(image: BufferedImage) =>
      subscribers.foreach { case (rect, subscriber) =>
        subscriber ! RectData(image.getData(rect))
      }
  }
}

object ImageDispatcher {

  case class OneCapture(image: BufferedImage)

  case class SubscribeRect(rect: Rectangle, subscriber: ActorRef)

  case class RectData(raster: Raster)

}
