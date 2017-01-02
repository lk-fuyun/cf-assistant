package com.fuyun.game.common;


import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinGDI.*;

/**
 * Created by fuyun on 2017/1/2.
 * native gdi
 */
public interface GDI32 extends com.sun.jna.platform.win32.GDI32 {
    GDI32 INSTANCE = (GDI32) Native.loadLibrary(GDI32.class);
    boolean BitBlt(HDC hdcDest, int nXDest, int nYDest, int nWidth, int nHeight, HDC hdcSrc, int nXSrc, int nYSrc, int dwRop);

    HDC GetDC(HWND hWnd);

    boolean GetDIBits(HDC dc, HBITMAP bmp, int startScan, int scanLines, byte[] pixels, BITMAPINFO bi, int usage);

    boolean GetDIBits(HDC dc, HBITMAP bmp, int startScan, int scanLines, short[] pixels, BITMAPINFO bi, int usage);

    boolean GetDIBits(HDC dc, HBITMAP bmp, int startScan, int scanLines, int[] pixels, BITMAPINFO bi, int usage);

    int SRCCOPY = 0xCC0020;
}
