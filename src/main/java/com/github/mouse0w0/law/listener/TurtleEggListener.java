package com.github.mouse0w0.law.listener;

import com.github.mouse0w0.law.config.Law;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class TurtleEggListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityInteract(EntityInteractEvent e) {
        Block block = e.getBlock();
        if (block.getType() == Material.TURTLE_EGG && Law.get(block.getWorld()).preventTurtleEggTrampling) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.PHYSICAL) {
            Block block = e.getClickedBlock();
            if (block.getType() == Material.TURTLE_EGG && Law.get(block.getWorld()).preventTurtleEggTrampling) {
                e.setCancelled(true);
            }
        }
    }
}
