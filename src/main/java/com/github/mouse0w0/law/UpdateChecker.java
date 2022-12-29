package com.github.mouse0w0.law;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class UpdateChecker implements Listener {
    private final JavaPlugin plugin;
    private final String pluginUrl;
    private final String repositoryUrl;
    private final String releaseUrl;
    private final BooleanSupplier enabled;
    private final Supplier<String> message;

    private BukkitTask task;
    private Version currentVersion;
    private Version latestVersion;
    private boolean outdated;

    public UpdateChecker(JavaPlugin plugin, String pluginUrl, String repositoryUrl, String releaseUrl, BooleanSupplier enabled, Supplier<String> message) {
        this.plugin = plugin;
        this.pluginUrl = pluginUrl;
        this.repositoryUrl = repositoryUrl;
        this.releaseUrl = releaseUrl;
        this.enabled = enabled;
        this.message = message;
        this.currentVersion = new Version(plugin.getDescription().getVersion());
        this.latestVersion = new Version("0");
        Bukkit.getPluginManager().registerEvents(this, plugin);
        start();
    }

    private boolean isEnabled() {
        return enabled.getAsBoolean();
    }

    private void start() {
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                if (isEnabled()) {
                    latestVersion = getLatestVersion();
                    outdated = latestVersion.compareTo(currentVersion) > 0;
                    if (outdated) {
                        Bukkit.getConsoleSender().sendMessage(getMessage());
                    }
                } else {
                    task.cancel();
                    task = null;
                }
            }
        }, 0, 6 * 60 * 60 * 20);
    }

    private String getMessage() {
        return new MessageFormat(message.get()).format(new Object[]{
                latestVersion, currentVersion, pluginUrl, repositoryUrl
        });
    }

    private Version getLatestVersion() {
        try {
            String s = get(releaseUrl);
            int start = s.indexOf("\"tag_name\"");
            if (start == -1) return new Version("0");
            start = start + 12;
            int end = s.indexOf("\"", start);
            if (end == -1) return new Version("0");
            return new Version(s.substring(start, end));
        } catch (IOException e) {
            return new Version("0");
        }
    }

    private static String get(String url) throws IOException {
        URLConnection connection = new URL(url).openConnection();
        StringBuilder sb = new StringBuilder();
        char[] buffer = new char[8192];
        int n;
        try (InputStreamReader isr = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)) {
            while ((n = isr.read(buffer)) != -1) {
                sb.append(buffer, 0, n);
            }
        }
        return sb.toString();
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (player.isOp() && isEnabled()) {
            if (task == null) {
                start();
            }
            if (outdated) {
                player.sendMessage(getMessage());
            }
        }
    }

    private static class Version implements Comparable<Version> {
        private final String value;
        private final int[] items;

        private Version(String version) {
            String[] split = version.split("\\.");
            int size = split.length;
            int[] items = new int[size];
            for (int i = 0; i < size; i++) {
                items[i] = Integer.parseInt(split[i]);
            }
            this.value = version;
            this.items = items;
        }

        @Override
        public int compareTo(Version o) {
            int[] tItems = items, oItems = o.items;
            int tSize = tItems.length, oSize = oItems.length;
            int maxSize = tSize > oSize ? tSize : oSize;
            for (int i = 0; i < maxSize; i++) {
                int result = Integer.compare(i < tSize ? tItems[i] : 0, i < oSize ? oItems[i] : 0);
                if (result != 0) {
                    return result;
                }
            }
            return 0;
        }

        @Override
        public String toString() {
            return value;
        }

    }
}
