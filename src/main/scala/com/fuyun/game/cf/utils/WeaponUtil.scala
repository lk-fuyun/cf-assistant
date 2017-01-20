package com.fuyun.game.cf.utils

import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.io.File
import java.util.Properties
import javax.imageio.ImageIO

import com.fuyun.game.cf.utils.ImproveAwt._

import scala.collection.JavaConverters._

/**
  * Created by fuyun on 2017/1/19.
  */
object WeaponUtil {
  val weaponNameColor: Int = (0xFF << 24) + (153 << 16) + (193 << 8) + 193
  val weaponRect = new Rectangle(816, 695, 185, 10)
  val weapons: Map[String, String] = {
    val weaponFile = getClass.getClassLoader.getResourceAsStream("weapon.properties")
    val props = new Properties()
    props.load(weaponFile)
    props.asScala.toMap
  }

  val hash2Weapon: Map[String, String] = {
    val hashes = weapons.values
    // insure no duplicated weapon hash
    assert(hashes.toSet.size == hashes.size)
    weapons.map(_.swap)
  }

  def weaponHash(image: BufferedImage): String = {
    val locations = for {
      x <- weaponRect.xRange
      y <- weaponRect.yRange
      if image.getRGB(x, y) == weaponNameColor
    } yield {
      y * weaponRect.width + x
    }
    val weaponId = locations.sum
    f"$weaponId%06x"
  }

  def getWeaponName(image: BufferedImage): Option[String] = {
    hash2Weapon.get(weaponHash(image))
  }

  def main(args: Array[String]): Unit = {
    val img = ImageIO.read(new File("C:\\Users\\fuyun\\Documents\\CFSystem\\Crossfire20170119_0002.bmp"))
    //    println(f"0x${34}%08x")
    println(weaponHash(img))
    if (args.length != 1) {
      println("please input path")
    }
  }
}
