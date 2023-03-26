package com.github.mouse0w0.law.listener;

import com.github.mouse0w0.law.config.Law;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTransformEvent;

public class TransformListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityTransform(EntityTransformEvent e) {
        Entity entity = e.getEntity();
        switch (e.getTransformReason()) {
            case CURED:
                if (Law.get(entity.getWorld()).preventVillagerCure) {
                    e.setCancelled(true);
                }
                break;
            case INFECTION:
                if (Law.get(entity.getWorld()).preventVillagerInfection) {
                    e.setCancelled(true);
                }
                break;
            case DROWNED:
                if (Law.get(entity.getWorld()).preventZombieDrowning) {
                    e.setCancelled(true);
                }
                break;
            case LIGHTNING:
                EntityType type = entity.getType();
                if (type == EntityType.VILLAGER) {
                    if (Law.get(entity.getWorld()).preventVillagerToWitch) {
                        e.setCancelled(true);
                    }
                } else if (type == EntityType.PIG) {
                    if (Law.get(entity.getWorld()).preventPigToZombiePigman) {
                        e.setCancelled(true);
                    }
                } else if (type == EntityType.MUSHROOM_COW) {
                    if (Law.get(entity.getWorld()).preventMooshroomSwitch) {
                        e.setCancelled(true);
                    }
                }
                break;
        }

    }
}
