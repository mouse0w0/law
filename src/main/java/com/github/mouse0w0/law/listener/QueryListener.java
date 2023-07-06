package com.github.mouse0w0.law.listener;

import com.github.mouse0w0.law.Main;
import com.github.mouse0w0.law.config.Lang;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class QueryListener implements Listener {
    private static QueryListener instance;

    public static boolean add(UUID uuid) {
        if (instance == null) {
            instance = new QueryListener();
            Bukkit.getPluginManager().registerEvents(instance, Main.getPlugin());
        }
        return instance.players.add(uuid);
    }

    public static void remove(UUID uuid) {
        if (instance == null) {
            return;
        }
        if (instance.players.remove(uuid)) {
            if (instance.players.isEmpty()) {
                HandlerList.unregisterAll(instance);
                instance = null;
            }
        }
    }

    private final Set<UUID> players = new HashSet<>();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent e) {
        remove(e.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (!players.contains(player.getUniqueId())) return;
        Block block = e.getClickedBlock();
        if (block == null) return;
        player.sendMessage(Lang.getWithPrefix("click-block-message", block.getType()));
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();
        if (!players.contains(player.getUniqueId())) return;
        Entity entity = e.getRightClicked();
        player.sendMessage(Lang.getWithPrefix("click-entity-message", entity.getType()));
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        if (!players.contains(damager.getUniqueId())) return;
        Entity entity = e.getEntity();
        damager.sendMessage(Lang.getWithPrefix("click-entity-message", entity.getType()));
        e.setCancelled(true);
    }
}
