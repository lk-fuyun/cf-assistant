package com.fuyun.game.common;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * Created by fuyun on 2017/1/2.
 * 映射kmllib.dll
 */
public interface KMLLib extends Library {
    KMLLib INSTANCE = (KMLLib) Native.loadLibrary("kmllib", KMLLib.class);

}
