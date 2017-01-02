package com.fuyun.game.common;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * Created by fuyun on 2017/1/2.
 * 映射kmllib.dll
 */
public interface KMLLib extends Library {
    KMLLib INSTANCE = (KMLLib) Native.loadLibrary("kmllib", KMLLib.class);
    // device
    boolean OpenDevice();

    boolean CloseDevice();

    boolean CheckDevice();

    boolean Restart();

    // keyboard
    boolean KeyDown(String key);

    boolean KeyUp(String key);

    boolean KeyPress(String key, int count);

    boolean CombinationKeyDown(String k1, String k2, String k3, String k4, String k5, String k6);

    boolean CombinationKeyUp(String k1, String k2, String k3, String k4, String k5, String k6);

    boolean CombinationKeyPress(String k1, String k2, String k3, String k4, String k5, String k6, int count);

    boolean SimulationPressKey(String keys);

    boolean KeyUpAll();

    boolean GetCapsLock();

    boolean GetNumLock();

    // mouse
    boolean LeftDown();

    boolean LeftUp();

    boolean LeftClick(int count);

    boolean LeftDoubleClick(int count);

    boolean RightDown();

    boolean RightUp();

    boolean RightClick(int count);

    boolean RightDoubleClick(int count);

    boolean MiddleDown();

    boolean MiddleUp();

    boolean MiddleClick(int count);

    boolean MiddleDoubleClick(int count);

    boolean MouseUpAll();

    boolean WheelUp(int count);

    boolean WheelDown(int count);

    boolean MouseWheel(int count);

    boolean SimulationMove(int x, int y);

    boolean MoveToR(int x, int y);

}
