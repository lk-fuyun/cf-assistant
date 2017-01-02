package com.fuyun.game.cf.sample

import java.awt.image.BufferedImage
import java.awt.{Image, Rectangle, Robot}
import java.io.File
import javax.imageio.ImageIO

import com.fuyun.game.common.{GDI32, User32}
import com.sun.jna.platform.win32.WinDef.HWND
import com.sun.jna.platform.win32.WinGDI


/**
  * Created by fuyun on 2017/1/2.
  */
object Sample {
  def main(args: Array[String]) {
    val robot = new Robot()
    val rect = new Rectangle(0, 0, 1024, 768)
    val times: Int = 500
    val start = System.currentTimeMillis()
    for (i <- 1 to times) {
      robot.createScreenCapture(rect)
//      sc(rect)
    }
    val end = System.currentTimeMillis()
    println(s"fps: ${times.toDouble / (end - start) * 1000}")
  }

  def sc(rect: Rectangle): Array[Byte] = {
    val windowDC = GDI32.INSTANCE.GetDC(User32.INSTANCE.GetDesktopWindow())
    val outputBitmap = GDI32.INSTANCE.CreateCompatibleBitmap(windowDC, rect.width, rect.height)
    try {
      val blitDC = GDI32.INSTANCE.CreateCompatibleDC(windowDC)
      try {
        val oldBitmap = GDI32.INSTANCE.SelectObject(blitDC, outputBitmap)
        try {
          GDI32.INSTANCE.BitBlt(blitDC, 0, 0, rect.width, rect.height, windowDC, rect.x, rect.y, GDI32.SRCCOPY)
        } finally {
          GDI32.INSTANCE.SelectObject(blitDC, oldBitmap)
        }
        val bi = new WinGDI.BITMAPINFO(40)
        bi.bmiHeader.biSize = 40
        val bytes = new Array[Byte](rect.width * rect.height * 3)
        val ok = GDI32.INSTANCE.GetDIBits(blitDC, outputBitmap, 0, rect.height, null.asInstanceOf[Array[Byte]], bi, WinGDI.DIB_RGB_COLORS)
        if (!ok) {
          sys.error("copy failure")
        }
        bytes
      } finally {
        GDI32.INSTANCE.DeleteObject(blitDC)
      }
    } finally {
      GDI32.INSTANCE.DeleteObject(outputBitmap)
    }
  }
}
