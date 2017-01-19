package com.fuyun.game.cf.actors

import java.awt.Rectangle
import java.awt.image.BufferedImage

import akka.actor.{Actor, ActorRef}

/**
  * Created by fuyun on 2017/1/18.
  */
class MoveDetector(imageDispatcher: ActorRef) extends Actor {

  import MoveDetector._

  @scala.throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    imageDispatcher ! ImageDispatcher.Subscribe
  }

  var preImage: BufferedImage = _
  var receiver: ActorRef = _

  def onReceive(image: BufferedImage): Unit = {
    if (preImage != null) {
      val diffs = for {
        x <- detectBound.x to (detectBound.x + detectBound.width)
        y <- detectBound.y to (detectBound.y + detectBound.height)
        if preImage.getRGB(x, y) != image.getRGB(x, y)
      } yield (x, y)
      if (diffs.nonEmpty) {
        val (ax, ay) = diffs.unzip
        receiver ! MovingPoint(ax.sum / diffs.size, ay.sum / diffs.size)
        context.unbecome()
        preImage = null
        return
      }
    }
    preImage = image
  }

  val moveDetector: Receive = {
    case ImageDispatcher.OneCapture(image) =>
      onReceive(image)
    case StopDetect =>
      context.unbecome()
  }

  override def receive: Receive = {
    case StartDetect =>
      receiver = sender()
      imageDispatcher ! ImageDispatcher.Subscribe
      context.become(moveDetector, discardOld = false)
  }
}

object MoveDetector {
  private val detectBound = new Rectangle(50, 50, 100, 100)

  object StartDetect

  object StopDetect

  case class MovingPoint(x: Int, y: Int)

}
