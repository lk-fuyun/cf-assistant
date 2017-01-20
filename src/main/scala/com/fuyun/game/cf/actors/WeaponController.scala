package com.fuyun.game.cf.actors

import akka.actor.{Actor, ActorRef}
import com.fuyun.game.cf.actors.WeaponController.Test

/**
  * Created by fuyun on 2017/1/19.
  */
class WeaponController(moveDetector: ActorRef, kMController: ActorRef) extends Actor {
  val sniper: Receive = {
    case MoveDetector.MovingPoint(x, y) =>
  }

  override def receive: Receive = {
    case Test =>
      moveDetector ! MoveDetector.StartDetect
      context.become(sniper)
  }
}
object WeaponController {
  type WeaponClass = Actor.Receive
  case class ChangeWeaponClass(weaponClass: WeaponClass)
  object Test


//  object Sniper extends WeaponClass {
//    case
//  }
}
