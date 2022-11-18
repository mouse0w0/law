package com.github.mouse0w0.law.listener;

import com.github.mouse0w0.law.config.Law;
import com.github.mouse0w0.law.util.EnumUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;

import java.util.Set;

public class BedExplodeListener implements Listener {
    private static final Set<Material> BED = EnumUtils.allMatch(Material.class,
            "BED", "BED_BLOCK", "WHITE_BED", "ORANGE_BED", "MAGENTA_BED",
            "LIGHT_BLUE_BED", "YELLOW_BED", "LIME_BED", "PINK_BED", "GRAY_BED",
            "LIGHT_GRAY_BED", "CYAN_BED", "PURPLE_BED", "BLUE_BED", "BROWN_BED",
            "GREEN_BED", "RED_BED", "BLACK_BED");
    
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockExplode(BlockExplodeEvent e) {
        Block block = e.getBlock();
        if (BED.contains(block.getType())) {
            if (Law.get(block.getLocation()).preventBedExplosion) {
                e.setCancelled(true);
            }
        }
    }
}
