package com.github.mouse0w0.law.util;

import org.bukkit.entity.Entity;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DamageSourceUtils {
    private static boolean initialized;
    private static MethodHandle getEntityDamage;
    private static MethodHandle getBukkitEntity;

    static {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            Class<?> classCraftEventFactory = Class.forName(NmsUtils.packageCraftBukkit + ".event.CraftEventFactory");
            Field fieldEntityDamage = classCraftEventFactory.getDeclaredField("entityDamage");
            getEntityDamage = lookup.unreflectGetter(fieldEntityDamage);
            Class<?> classEntity = Class.forName(NmsUtils.packageMinecraft + ".Entity");
            Method methodGetBukkitEntity = classEntity.getMethod("getBukkitEntity");
            getBukkitEntity = lookup.unreflect(methodGetBukkitEntity);
            initialized = true;
        } catch (Throwable e) {
            initialized = false;
        }
    }

    public static Entity getEntityDamage() {
        if (!initialized) return null;
        try {
            Object entity = getEntityDamage.invoke();
            if (entity == null) return null;
            return (Entity) getBukkitEntity.invoke(entity);
        } catch (Throwable ignored) {
            return null;
        }
    }
}
