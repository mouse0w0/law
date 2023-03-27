package com.github.mouse0w0.law.util;

import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.lang.reflect.Field;

public class NmsUtils {
    public static final String packageCraftBukkit;
    public static final String packageMinecraft;

    static {
        try {
            Server server = Bukkit.getServer();
            Class<?> classCraftServer = server.getClass();
            Field fieldConsole = classCraftServer.getDeclaredField("console");
            fieldConsole.setAccessible(true);
            Object console = fieldConsole.get(server);
            Class<?> classMinecraftServer = console.getClass();
            String classNameCraftServer = classCraftServer.getName();
            String classNameMinecraftServer = classMinecraftServer.getName();
            packageCraftBukkit = classNameCraftServer.substring(0, classNameCraftServer.lastIndexOf('.'));
            packageMinecraft = classNameMinecraftServer.substring(0, classNameMinecraftServer.lastIndexOf('.'));
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
