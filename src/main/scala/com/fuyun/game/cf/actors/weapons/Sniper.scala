package com.fuyun.game.cf.actors.weapons

import java.awt.Rectangle
import java.awt.image.BufferedImage

import akka.actor.{Actor, Props}
import com.fuyun.game.cf.actors.{ImageDispatcher, KMController}
import com.fuyun.game.cf.utils.ImproveAwt._

import scala.concurrent.duration._
import scala.language.postfixOps

/**
  * Created by fuyun on 2017/1/24.
  */
class Sniper extends Actor {
  val moveDetectRange = new Rectangle(507, 379, 11, 11)
  val roomDetectRange = new Rectangle(470, 385, 3, 1)
  val kMController = context.actorOf(Props[KMController], "kmController")
  var preImage: BufferedImage = _

  def isRoom(implicit image: BufferedImage): Boolean = {
    roomDetectRange.forall(p => image.getRGB(p.x, p.y) == 0xff000000)
  }
  def isMoving(implicit image: BufferedImage): Boolean = {
    val res = preImage == null || moveDetectRange.exists(p => preImage.getRGB(p.x, p.y) != image.getRGB(p.x, p.y))
    preImage = image
    res
  }
  object Return
  val sleeping: Receive = {
    case Return =>
      context.unbecome()
    case _ =>
  }

  override def receive: Receive = {
    case ImageDispatcher.OneCapture(image) =>
      implicit val i = image
      if (isInRange && isRoom && isMoving) {
        kMController ! KMController.Shoot
        kMController ! KMController.TakeBackSniper
        context.become(sleeping, discardOld = false)
        context.system.scheduler.scheduleOnce(500 millis) {
          self ! Return
        }
      }
  }
}
