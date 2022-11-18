package com.github.mouse0w0.law.config;

import com.github.mouse0w0.law.Main;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    public static boolean updateCheck;

    public static void load() {
        Main plugin = Main.getPlugin();
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();
        updateCheck = config.getBoolean("update-check");
    }
}
