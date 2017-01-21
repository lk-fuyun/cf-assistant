package com.fuyun.game.cf.base;

/**
 * Created by fuyun on 2017/1/20.
 *
 */
public class Weapon {
    public String name;
    public int hashId;
    public WeaponClass weaponClass;

    @Override
    public String toString() {
        return "Weapon{" +
                "name='" + name + '\'' +
                ", hashId=" + hashId +
                ", weaponClass=" + weaponClass +
                '}';
    }
}
