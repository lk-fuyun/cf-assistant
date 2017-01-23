package com.fuyun.game.cf.actors

import java.awt.image.BufferedImage

import akka.actor.{Actor, ActorRef}
import com.fuyun.game.cf.actors.WeaponMonitor.WeaponClassChange
import com.fuyun.game.cf.base.WeaponClass
import com.fuyun.game.cf.utils.WeaponUtil

/**
  * Created by fuyun on 2017/1/18.
  */
class WeaponMonitor(imageDispatcher: ActorRef, controller: ActorRef) extends Actor {

  @scala.throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    imageDispatcher ! ImageDispatcher.Subscribe
  }
  var preWeaponClass: WeaponClass = _

  def hasCursor(image: BufferedImage): Boolean = {
    ???
  }
  override def receive: Receive = {
    case ImageDispatcher.OneCapture(image) =>
      val hash = WeaponUtil.weaponHash(image)
      val weaponClass = WeaponUtil.getWeapon(hash).map(_.weaponClass).orElse {
        if (hasCursor(image)) Some(WeaponClass.SNIPE) else None
      }.getOrElse(WeaponClass.UNKNOWN)
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
