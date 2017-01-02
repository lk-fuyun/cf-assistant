package com.fuyun.game.common;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by fuyun on 2017/1/2.
 */
public class User32Test {
    @Test
    public void getKeyState() throws Exception {
        while (true) {
            System.out.println("asn: " + User32.INSTANCE.GetAsyncKeyState('E'));
            System.out.println("normal: " + User32.INSTANCE.GetKeyState('E'));
            Thread.sleep(500);
        }
    }

}