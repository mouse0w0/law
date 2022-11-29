package com.github.mouse0w0.law;

import com.github.mouse0w0.law.command.LawCommand;
import com.github.mouse0w0.law.config.Config;
import com.github.mouse0w0.law.config.Lang;
import com.github.mouse0w0.law.config.Law;
import com.github.mouse0w0.law.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.logging.Level;

public class Main extends JavaPlugin {
    private static Main plugin;

    public static Main getPlugin() {
        return plugin;
    }

    public Main() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        Config.load();
        Lang.load();
        Law.load();
        LawCommand.init();
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new LawListener(), this);
        if (hasClass("org.bukkit.event.entity.EntitySpawnEvent")) {
            pm.registerEvents(new SpawnListener(), this);
        }
        if (hasClass("org.bukkit.event.block.BlockExplodeEvent")) {
            pm.registerEvents(new BedExplodeListener(), this);
        }
        if (hasClass("org.bukkit.event.entity.EntityPickupItemEvent")) {
            pm.registerEvents(new PickupItemListener(), this);
        }
        if (hasClass("org.bukkit.event.entity.EntityTransformEvent")) {
            pm.registerEvents(new TransformListener(), this);
        } else {
            pm.registerEvents(new TransformListenerLegacy(), this);
        }
        if (hasClass("org.bukkit.block.data.Waterlogged")) {
            pm.registerEvents(new LiquidFlowListener(), this);
        } else {
            pm.registerEvents(new LiquidFlowListenerLegacy(), this);
        }
        if (hasClass("org.bukkit.event.weather.LightningStrikeEvent$Cause")) {
            pm.registerEvents(new LightningStrikeListener(), this);
        } else {
            pm.registerEvents(new LightningStrikeListenerLegacy(), this);
        }
        if (hasEnum(Material.class, "RESPAWN_ANCHOR")) {
            pm.registerEvents(new RespawnAnchorExplodeListener(), this);
        }

        new UpdateChecker(this,
                "https://www.mcbbs.net/thread-1397550-1-1.html",
                "https://github.com/mouse0w0/law",
                "https://api.github.com/repos/mouse0w0/law/releases/latest",
                () -> Config.updateCheck,
                () -> Lang.get("update-check-message"));
        new Metrics(this, 16878);
    }

    @Override
    public void saveResource(String resourcePath, boolean replace) {
        if (resourcePath == null || resourcePath.equals("")) {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }

        resourcePath = resourcePath.replace('\\', '/');
        InputStream in = getResource(resourcePath);
        if (in == null) {
            throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in " + getFile());
        }

        File outFile = new File(getDataFolder(), resourcePath);
        int lastIndex = resourcePath.lastIndexOf('/');
        File outDir = new File(getDataFolder(), resourcePath.substring(0, lastIndex >= 0 ? lastIndex : 0));

        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        try {
            if (!outFile.exists() || replace) {
                OutputStream out = new FileOutputStream(outFile);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                in.close();
            }
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, ex);
        }
    }

    private static boolean hasClass(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private static <T extends Enum<T>> boolean hasEnum(Class<T> type, String name) {
        try {
            Enum.valueOf(type, name);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
