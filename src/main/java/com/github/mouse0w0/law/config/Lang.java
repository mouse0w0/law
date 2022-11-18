package com.github.mouse0w0.law.config;

import com.github.mouse0w0.law.Main;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;

public class Lang {
    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    private static Configuration config;
    private static String prefix;

    public static void load() {
        Main plugin = Main.getPlugin();
        plugin.saveResource("lang.yml", false);
        config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "lang.yml"));
        prefix = config.getString("prefix");
    }

    public static String get(String path) {
        return config.getString(path);
    }

    public static String get(String path, Object... args) {
        return new MessageFormat(get(path)).format(args);
    }

    public static String getWithPrefix(String path) {
        return prefix + get(path);
    }

    public static String getWithPrefix(String path, Object... args) {
        return prefix + get(path, args);
    }
}
