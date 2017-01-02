package com.fuyun.game.common;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;

/**
 * Created by fuyun on 2017/1/2.
 */
public interface User32 extends com.sun.jna.platform.win32.User32 {
    User32 INSTANCE = (User32) Native.loadLibrary(User32.class);
    short GetKeyState(int nVirtKey);
    WinDef.HWND GetDesktopWindow();

}
