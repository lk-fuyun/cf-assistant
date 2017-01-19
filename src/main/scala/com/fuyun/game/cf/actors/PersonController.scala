package com.fuyun.game.cf.actors

import akka.actor.{Actor, ActorRef}
import com.fuyun.game.cf.actors.PersonController.Test

/**
  * Created by fuyun on 2017/1/19.
  */
class PersonController(moveDetector: ActorRef, kMController: ActorRef) extends Actor {
  val sniper: Receive = {
    case MoveDetector.MovingPoint(x, y) =>
      kMController ! KMController.MouseMoveTo(x, y)
  }

  override def receive: Receive = {
    case Test =>
      moveDetector ! MoveDetector.StartDetect
      context.become(sniper)
  }
}
object PersonController {
  object Test
}
