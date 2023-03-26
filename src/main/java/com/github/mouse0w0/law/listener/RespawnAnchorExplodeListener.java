package com.github.mouse0w0.law.listener;

import com.github.mouse0w0.law.config.Law;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.RespawnAnchor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RespawnAnchorExplodeListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockExplode(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        Block block = e.getClickedBlock();
        if (block.getWorld().getEnvironment() == World.Environment.NETHER) {
            return;
        }
        if (block.getType() == Material.RESPAWN_ANCHOR) {
            if (Law.get(block.getWorld()).preventRespawnAnchorExplosion) {
                RespawnAnchor respawnAnchor = (RespawnAnchor) block.getBlockData();
                int charges = respawnAnchor.getCharges();
                if (charges == 0) {
                    return;
                }
                ItemStack item = e.getItem();
                if (item == null || item.getType() != Material.GLOWSTONE || charges == respawnAnchor.getMaximumCharges()) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
