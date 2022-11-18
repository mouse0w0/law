package com.github.mouse0w0.law.listener;

import com.github.mouse0w0.law.config.Law;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;

public class RespawnAnchorExplodeListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockExplode(BlockExplodeEvent e) {
        Block block = e.getBlock();
        if (block.getType() == Material.RESPAWN_ANCHOR) {
            if (Law.get(block.getLocation()).preventRespawnAnchorExplosion) {
                e.setCancelled(true);
            }
        }
    }
}
