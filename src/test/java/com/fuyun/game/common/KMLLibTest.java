package com.fuyun.game.common;

import static org.junit.Assert.*;

public class KMLLibTest {

    @org.junit.Test
    public void openDevice() throws Exception {
        assertEquals(true, KMLLib.INSTANCE.OpenDevice());
    }

    @org.junit.Test
    public void middleUp() throws Exception {

    }

    @org.junit.Test
    public void middleClick() throws Exception {

    }

    @org.junit.Test
    public void middleDoubleClick() throws Exception {
        KMLLib.INSTANCE.OpenDevice();
        assertTrue(KMLLib.INSTANCE.RightClick(1));
        KMLLib.INSTANCE.CloseDevice();
    }

}