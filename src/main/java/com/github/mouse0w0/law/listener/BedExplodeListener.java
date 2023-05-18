package com.github.mouse0w0.law.listener;

import com.github.mouse0w0.law.config.Law;
import com.github.mouse0w0.law.util.EnumUtils;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Set;

public class BedExplodeListener implements Listener {
    private static final Set<Material> BED = EnumUtils.allOf(Material.class,
            "BED", "BED_BLOCK", "WHITE_BED", "ORANGE_BED", "MAGENTA_BED",
            "LIGHT_BLUE_BED", "YELLOW_BED", "LIME_BED", "PINK_BED", "GRAY_BED",
            "LIGHT_GRAY_BED", "CYAN_BED", "PURPLE_BED", "BLUE_BED", "BROWN_BED",
            "GREEN_BED", "RED_BED", "BLACK_BED");

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockExplode(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        Block block = e.getClickedBlock();
        if (block.getWorld().getEnvironment() == Environment.NORMAL) {
            return;
        }
        if (BED.contains(block.getType())) {
            if (Law.get(block.getWorld()).preventBedExplosion) {
                e.setCancelled(true);
            }
        }
    }
}
