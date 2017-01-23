package com.fuyun.game.cf.actors

import java.awt.image.BufferedImage

import akka.actor.{Actor, ActorRef, Props}
import com.fuyun.game.cf.base.WeaponClass

/**
  * Created by fuyun on 2017/1/19.
  */
class Controller(imageDispatcher: ActorRef) extends Actor {

  val kMController = context.actorOf(Props[KMController], "KMController")

  def switchSnip(): Unit = {
    kMController ! KMController.MouseWheel(1)
    kMController ! KMController.MouseWheel(-1)
  }

  def fire(): Unit = {
//    kMController ! KMController.MouseClick
  }

  def isRoom(implicit image: BufferedImage): Boolean = {
    ???
  }

  def isInRange(implicit image: BufferedImage): Boolean = {
    ???
  }

  def isMoving(implicit image: BufferedImage): Boolean = {
    ???
  }

  val weaponClass2Motion = Map(
    WeaponClass.SNIPE -> sniper
  )

  def withWeaponChange(weaponLogic: Receive): Receive = {
    val weaponChange: Receive = {
      case WeaponMonitor.WeaponClassChange(changedClass) =>
        context.become(weaponClass2Motion(changedClass))
    }
    weaponLogic.orElse(weaponChange)
  }

  def sniper: Receive = withWeaponChange {
    case ImageDispatcher.OneCapture(image) =>
      implicit val i = image
      if (isInRange && isRoom && isMoving) {
        fire()
        switchSnip()
      }
  }

  def rifle = withWeaponChange {
    case ImageDispatcher.OneCapture(image) =>
      implicit val i = image
//      if (isInRange && )
  }

  override def receive: Receive = {
    ???
  }
}

object Controller {
  type WeaponClass = Actor.Receive

  case class ChangeWeaponClass(weaponClass: WeaponClass)

  object Test


  //  object Sniper extends WeaponClass {
  //    case
  //  }
}
