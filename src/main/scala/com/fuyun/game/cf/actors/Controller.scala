package com.fuyun.game.cf.actors

import akka.actor.{Actor, ActorRef, Props}
import com.fuyun.game.cf.actors.weapons.{Pistol, Sniper}
import com.fuyun.game.cf.base.WeaponClass

/**
  * Created by fuyun on 2017/1/19.
  */
class Controller(imageDispatcher: ActorRef) extends Actor {

  val weaponClass2Motion = Map(
    WeaponClass.SNIPE -> Props[Sniper],
    WeaponClass.PISTOL -> Props[Pistol],
    WeaponClass.UNKNOWN -> Props[Pistol]
  )


  @scala.throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    imageDispatcher ! ImageDispatcher.Subscribe
  }

  private def getOrCreateActor(c: WeaponClass): ActorRef = {
    context.child(c.name()).getOrElse(context.actorOf(weaponClass2Motion(c), c.name()))
  }

  var curGun: ActorRef = getOrCreateActor(WeaponClass.PISTOL)

  override def receive: Receive = {
    case x: ImageDispatcher.OneCapture =>
      curGun ! x
    case WeaponMonitor.WeaponClassChange(c) =>
      curGun = getOrCreateActor(c)
  }
}
