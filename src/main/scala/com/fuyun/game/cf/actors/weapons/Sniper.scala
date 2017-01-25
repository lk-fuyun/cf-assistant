package com.fuyun.game.cf.actors.weapons

import java.awt.Rectangle
import java.awt.image.BufferedImage

import akka.actor.{Actor, Props}
import com.fuyun.game.cf.actors.{ImageDispatcher, KMController}
import com.fuyun.game.cf.utils.ImproveAwt._

/**
  * Created by fuyun on 2017/1/24.
  */
class Sniper extends Actor {
  val moveDetectRange = new Rectangle(???, ???, ???, ???)
  val kMController = context.actorOf(Props[KMController], "kmController")
  var preImage: BufferedImage = _

  def isRoom(implicit image: BufferedImage): Boolean = {
    ???
  }
  def isMoving(implicit image: BufferedImage): Boolean = {
    val res = preImage == null || moveDetectRange.exists(p => preImage.getRGB(p.x, p.y) != image.getRGB(p.x, p.y))
    preImage = image
    res
  }

  override def receive: Receive = {
    case ImageDispatcher.OneCapture(image) =>
      implicit val i = image
      if (isInRange && isRoom && isMoving) {
        kMController ! KMController.Shoot
        kMController ! KMController.TakeBackSniper
      }
  }
}
