package com.github.mouse0w0.law.listener;

import com.github.mouse0w0.law.config.Law;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.LightningStrikeEvent;

public class LightningStrikeListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onThunderChange(LightningStrikeEvent e) {
        if (e.getCause() == LightningStrikeEvent.Cause.WEATHER && Law.get(e.getWorld()).disableWeatherLightning) {
            e.setCancelled(true);
        }
    }
}
