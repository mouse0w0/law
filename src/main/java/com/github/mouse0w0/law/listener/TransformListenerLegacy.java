package com.github.mouse0w0.law.listener;

import com.github.mouse0w0.law.config.Law;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PigZapEvent;

public class TransformListenerLegacy implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPigZap(PigZapEvent e) {
        Entity entity = e.getEntity();
        if (Law.get(entity.getWorld()).preventPigToZombiePigman) {
            e.setCancelled(true);
        }
    }
}
