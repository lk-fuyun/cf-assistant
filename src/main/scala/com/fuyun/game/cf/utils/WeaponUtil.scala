package com.fuyun.game.cf.utils

import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

import com.fasterxml.jackson.databind.ObjectMapper
import com.fuyun.game.cf.base.Weapon
import com.fuyun.game.cf.utils.ImproveAwt._

import scala.collection.JavaConverters._

/**
  * Created by fuyun on 2017/1/19.
  */
object WeaponUtil {
  val weaponNameColor: Int = (0xFF << 24) + (153 << 16) + (193 << 8) + 193
  val weaponRect = new Rectangle(816, 695, 185, 10)
  val weapons: List[Weapon] = {
    val weaponFile = getClass.getClassLoader.getResourceAsStream("weapons.json")
    val objectMapper = new ObjectMapper()
    objectMapper.readValue[java.util.List[Weapon]](
      weaponFile,
      objectMapper.getTypeFactory.constructCollectionType(classOf[java.util.List[_]], classOf[Weapon])
    ).asScala.toList
  }

  val hash2Weapon: Map[Int, Weapon] = {
    weapons.groupBy(_.hashId).mapValues {wps =>
      assert(wps.tail == Nil)
      wps.head
    }
  }

  def weaponHash(image: BufferedImage): Int = {
    val locations = for {
      x <- weaponRect.xRange
      y <- weaponRect.yRange
      if image.getRGB(x, y) == weaponNameColor
    } yield {
      y * weaponRect.width + x
    }
    locations.sum
  }

  def getWeapon(weaponHash: Int): Option[Weapon] = {
    hash2Weapon.get(weaponHash)
  }

  def main(args: Array[String]): Unit = {
    val img = ImageIO.read(new File("C:\\Users\\fuyun\\Documents\\CFSystem\\Crossfire20170119_0002.bmp"))
    //    println(f"0x${34}%08x")
    for (elem <- 1 to 200) {
      weaponHash(img)
    }
    val startTime = System.currentTimeMillis()
    val times: Int = 10000
    for (elem <- 1 to times) {
      weaponHash(img)
    }
    val stopTime = System.currentTimeMillis()
    println((stopTime - startTime) * 1.0 / times)
    println(weaponHash(img))
    if (args.length != 1) {
      println("please input path")
    }
  }
}
