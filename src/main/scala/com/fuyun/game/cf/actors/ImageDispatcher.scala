package com.fuyun.game.cf.actors

import java.awt.image.BufferedImage

import akka.actor.{Actor, ActorRef}
import com.fuyun.game.cf.actors.ImageDispatcher._

import scala.collection.mutable

/**
  * Created by fuyun on 2017/1/18.
  */
class ImageDispatcher extends Actor {
  val subscribers = mutable.Set[ActorRef]()

  override def receive: Receive = {
    case Subscribe =>
      subscribers += sender()
    case UnSubscribe =>
      subscribers -= sender()
    case oneCapture: OneCapture =>
      subscribers.foreach { subscriber =>
        subscriber ! oneCapture
      }
  }
}

object ImageDispatcher {

  case class OneCapture(image: BufferedImage)

  object Subscribe

  case class UnSubscribe(implicit actorRef: ActorRef)

}
