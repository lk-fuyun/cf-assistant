package com.fuyun.game.cf.actors

import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

import akka.actor.{Actor, ActorRef}
import com.fuyun.game.cf.actors.WeaponMonitor.WeaponClassChange
import com.fuyun.game.cf.base.WeaponClass
import com.fuyun.game.cf.utils.WeaponUtil
import com.fuyun.game.cf.utils.ImproveAwt._

import scala.collection.mutable

/**
  * Created by fuyun on 2017/1/18.
  */
class WeaponMonitor(imageDispatcher: ActorRef, controller: ActorRef) extends Actor {
  val WeaponPath = new File("UnknownGuns/")
  WeaponPath.mkdirs()
  @scala.throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    imageDispatcher ! ImageDispatcher.Subscribe
  }
  var preWeaponClass: WeaponClass = WeaponClass.UNKNOWN
  val cursorRect = new Rectangle(380, 384, 131, 1)
  def hasCursor(image: BufferedImage): Boolean = {
    var count = 0
    cursorRect.sliding(2).foreach{ case Seq(h, l) =>
        if (image.getRGB(h.x, h.y) == image.getRGB(l.x, l.y)) {
          count += 1
        } else {
          count = 0
        }
        if (count >= 4) {
          return true
        }
    }
    false
  }
  val unknownsWeapon = mutable.Set[Int]()
  override def receive: Receive = {
    case ImageDispatcher.OneCapture(image) =>
      val hash = WeaponUtil.weaponHash(image)
      val weaponClass = WeaponUtil.getWeapon(hash).map(_.weaponClass).orElse {
        if (!hasCursor(image)) Some(WeaponClass.SNIPE) else None
      }.getOrElse(WeaponClass.UNKNOWN)
      if (weaponClass == WeaponClass.UNKNOWN && !unknownsWeapon(hash)) {
        unknownsWeapon += hash
        val file = new File(WeaponPath, s"$hash.png")
        ImageIO.write(image, "PNG", file)
      }
      if (preWeaponClass != weaponClass) {
        controller ! WeaponClassChange(weaponClass)
        preWeaponClass = weaponClass
      }
  }
}
object WeaponMonitor {
  object Subscribe
  case class WeaponClassChange(weaponClass: WeaponClass)
}
