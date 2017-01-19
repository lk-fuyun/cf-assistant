package com.fuyun.game.cf.utils

import java.awt.{Rectangle, Robot}
import java.awt.image.BufferedImage
import java.io.File
import java.security.MessageDigest
import javax.imageio.ImageIO

import ImproveAwt._

/**
  * Created by fuyun on 2017/1/19.
  */
object WeaponUtil {
  val weaponNameColor: Int = (0xFF << 24) + (153 << 16) + (193 << 8) + 193

  def weaponHash(image: BufferedImage): String = {
    val locs = for {
      x <- Locations.Weapon.xRange
      y <- Locations.Weapon.yRange
      if image.getRGB(x, y) == weaponNameColor
    } yield {
      y * Locations.Weapon.width + x
    }
    val weaponId = locs.foldLeft(0)((s, loc) => (s << 1) + loc)
    f"$weaponId%08x"
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
