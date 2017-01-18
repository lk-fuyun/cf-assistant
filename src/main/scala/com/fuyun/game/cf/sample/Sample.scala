package com.fuyun.game.cf.sample

import java.awt.{Rectangle, Robot}

import com.fuyun.game.common.{GDI32, User32}
import com.sun.jna.platform.win32.WinGDI


/**
  * Created by fuyun on 2017/1/2.
  */
object Sample {
  def main(args: Array[String]) {
    val robot = new Robot()
    val times: Int = 2000
    val start = System.currentTimeMillis()
    for (i <- 1 to times) {
            robot.createScreenCapture(rect)
//      sc()
    }
    val end = System.currentTimeMillis()
    println(s"fps: ${times.toDouble / (end - start) * 1000}")
  }

  val rect = new Rectangle(0, 0, 1024, 768)
  private val lineWidth: Int = (rect.width * 3 + 3) & 0x7FFFFFFC
  val bytes = new Array[Byte](lineWidth * rect.height)
  def sc(): Array[Byte] = {
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

        bi.bmiHeader.biWidth = rect.width
        bi.bmiHeader.biHeight = rect.height
        bi.bmiHeader.biPlanes = 1
        bi.bmiHeader.biBitCount = 24
        bi.bmiHeader.biCompression = WinGDI.BI_RGB
        bi.bmiHeader.biSizeImage = 0
        bi.bmiHeader.biXPelsPerMeter = 0
        bi.bmiHeader.biYPelsPerMeter = 0
        bi.bmiHeader.biClrUsed = 0
        bi.bmiHeader.biClrImportant = 0
//        bi.bmiHeader.biSize = bi.bmiHeader.
        val ok = GDI32.INSTANCE.GetDIBits(blitDC, outputBitmap, 0, rect.height, bytes, bi, WinGDI.DIB_RGB_COLORS)
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
