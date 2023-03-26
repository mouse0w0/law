package com.github.mouse0w0.law.listener;

import com.github.mouse0w0.law.config.Law;
import com.github.mouse0w0.law.util.EnumUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import java.util.Set;

public class LiquidFlowListenerLegacy implements Listener {
    private static final Set<Material> WATER = EnumUtils.allOf(Material.class,
            "WATER", "STATIONARY_WATER");
    private static final Set<Material> LAVA = EnumUtils.allOf(Material.class,
            "LAVA", "STATIONARY_LAVA");

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockFromTo(BlockFromToEvent e) {
        Block block = e.getBlock();
        Material type = block.getType();
        if (WATER.contains(type)) {
            if (Law.get(block.getWorld()).preventWaterFlow) {
                e.setCancelled(true);
            }
        } else if (LAVA.contains(type)) {
            if (Law.get(block.getWorld()).preventLavaFlow) {
                e.setCancelled(true);
            }
        }
    }
}
