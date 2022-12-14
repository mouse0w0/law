package com.github.mouse0w0.law.listener;

import com.github.mouse0w0.law.config.Law;
import com.github.mouse0w0.law.util.EnumUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.util.Set;

public class LawListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityPortal(EntityPortalEvent e) {
        Entity entity = e.getEntity();
        if (Law.get(entity.getLocation()).preventEntityTeleportByPortal.contains(entity.getType())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent e) {
        Entity entity = e.getEntity();
        EntityType type = entity.getType();
        Location location = entity.getLocation();
        if (Law.get(location).preventEntityExplosion.contains(type)) {
            e.setCancelled(true);
        } else if (Law.get(location).preventEntityBreakBlock.contains(type)) {
            e.blockList().clear();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onHangingBreakByEntity(HangingBreakByEntityEvent e) {
        Entity entity = e.getEntity();
        EntityType type = entity.getType();
        Law law = Law.get(entity.getLocation());
        if (type == EntityType.PLAYER) {
            if (law.preventLeftClickEntity.contains(type) && !entity.hasPermission("law.bypass.left-click-entity")) {
                e.setCancelled(true);
            }
        } else {
            if (law.preventEntityBreakBlock.contains(type) || law.preventEntityExplosion.contains(type)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityChangeBlock(EntityChangeBlockEvent e) {
        Entity entity = e.getEntity();
        if (Law.get(entity.getLocation()).preventEntityBreakBlock.contains(entity.getType())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onVehicleEnter(VehicleEnterEvent e) {
        Entity entity = e.getEntered();
        if (Law.get(entity.getLocation()).preventEntityEnterVehicle.contains(entity.getType())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        switch (entity.getType()) {
            case DROPPED_ITEM:
                EntityDamageEvent.DamageCause cause = e.getCause();
                switch (e.getCause()) {
                    case ENTITY_EXPLOSION:
                    case BLOCK_EXPLOSION:
                        if (Law.get(entity.getLocation()).preventItemDamageByExplosion) {
                            e.setCancelled(true);
                        }
                        break;
                    case LAVA:
                    case FIRE:
                    case FIRE_TICK:
                        if (Law.get(entity.getLocation()).preventItemDamageByFire) {
                            e.setCancelled(true);
                        }
                        break;
                }
                break;
            case PLAYER:
                if (Law.get(entity.getLocation()).preventPlayerDamage.contains(e.getCause())) {
                    e.setCancelled(true);
                }
                break;
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCreeperPower(CreeperPowerEvent e) {
        if (e.getCause() == CreeperPowerEvent.PowerCause.LIGHTNING) {
            if (Law.get(e.getEntity().getLocation()).preventCreeperCharge) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockSpread(BlockSpreadEvent e) {
        Block block = e.getSource();
        if (block.getType() == Material.FIRE && Law.get(block.getLocation()).preventFireSpread) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBurn(BlockBurnEvent e) {
        if (Law.get(e.getBlock().getLocation()).preventFireBurn) {
            e.setCancelled(true);
        }
    }

    private static final Set<Material> CORAL = EnumUtils.allOf(Material.class,
            "TUBE_CORAL_BLOCK", "BRAIN_CORAL_BLOCK", "BUBBLE_CORAL_BLOCK", "FIRE_CORAL_BLOCK", "HORN_CORAL_BLOCK",
            "TUBE_CORAL", "BRAIN_CORAL", "BUBBLE_CORAL", "FIRE_CORAL", "HORN_CORAL",
            "TUBE_CORAL_FAN", "BRAIN_CORAL_FAN", "BUBBLE_CORAL_FAN", "FIRE_CORAL_FAN", "HORN_CORAL_FAN",
            "TUBE_CORAL_WALL_FAN", "BRAIN_CORAL_WALL_FAN", "BUBBLE_CORAL_WALL_FAN", "FIRE_CORAL_WALL_FAN", "HORN_CORAL_WALL_FAN");

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockFade(BlockFadeEvent e) {
        Block block = e.getBlock();
        Material type = block.getType();
        if (type == Material.FIRE) {
            if (Law.get(block.getLocation()).preventFireFade) {
                e.setCancelled(true);
            }
        } else if (type == Material.SNOW) {
            if (Law.get(block.getLocation()).preventSnowMelt) {
                e.setCancelled(true);
            }
        } else if (type == Material.ICE) {
            if (Law.get(block.getLocation()).preventIceMelt) {
                e.setCancelled(true);
            }
        } else if (CORAL.contains(type)) {
            if (Law.get(block.getLocation()).preventCoralDeath) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockForm(BlockFormEvent e) {
        if (e instanceof EntityBlockFormEvent) {
            Entity entity = ((EntityBlockFormEvent) e).getEntity();
            if (entity.getType() == EntityType.SNOWMAN) {
                if (Law.get(entity.getLocation()).preventSnowmanGenerateSnow) {
                    e.setCancelled(true);
                }
            }
        } else {
            Block block = e.getBlock();
            switch (block.getType()) {
                case SNOW:
                    if (Law.get(block.getLocation()).preventSnowForm) {
                        e.setCancelled(true);
                    }
                    break;
                case ICE:
                    if (Law.get(block.getLocation()).preventIceForm) {
                        e.setCancelled(true);
                    }
                    break;
            }
        }
    }

    private static final Material FARMLAND = EnumUtils.oneOf(Material.class,
            "FARMLAND", "SOIL");
    private static final Material DRAGON_EGG = EnumUtils.oneOf(Material.class,
            "DRAGON_EGG");

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityInteract(EntityInteractEvent e) {
        Block block = e.getBlock();
        if (block.getType() == FARMLAND && Law.get(block.getLocation()).preventFarmlandDecay) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent e) {
        switch (e.getAction()) {
            case PHYSICAL: {
                Block block = e.getClickedBlock();
                if (block.getType() == FARMLAND && Law.get(block.getLocation()).preventFarmlandDecay) {
                    e.setCancelled(true);
                }
                break;
            }
            case RIGHT_CLICK_BLOCK: {
                Block block = e.getClickedBlock();
                if (block.getType() == DRAGON_EGG && Law.get(block.getLocation()).preventDragonEggTeleport) {
                    e.setCancelled(true);
                }
                break;
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onLeavesDecay(LeavesDecayEvent e) {
        if (Law.get(e.getBlock().getLocation()).preventLeavesDecay) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent e) {
        Law law = Law.get(e.getEntity().getLocation());
        if (law.keepInventoryOnDeath) {
            e.getDrops().clear();
            e.setKeepInventory(true);
        }
        if (law.keepExpOnDeath) {
            e.setDroppedExp(0);
            e.setKeepLevel(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onWeatherChange(WeatherChangeEvent e) {
        if (e.toWeatherState() && Law.get(e.getWorld()).disableWeatherRaining) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onThunderChange(ThunderChangeEvent e) {
        if (e.toThunderState() && Law.get(e.getWorld()).disableWeatherThunder) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent e) {
        Block block = e.getBlock();
        if (Law.get(block.getLocation()).preventPlaceBlock.contains(block.getType()) && !e.getPlayer().hasPermission("law.bypass.place-block")) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerInteractBlock(PlayerInteractEvent e) {
        Action action = e.getAction();
        if (action == Action.LEFT_CLICK_BLOCK) {
            Block block = e.getClickedBlock();
            if (Law.get(block.getLocation()).preventLeftClickBlock.contains(block.getType()) && !e.getPlayer().hasPermission("law.bypass.left-click-block")) {
                e.setCancelled(true);
            }
        } else if (action == Action.RIGHT_CLICK_BLOCK) {
            Block block = e.getClickedBlock();
            if (Law.get(block.getLocation()).preventRightClickBlock.contains(block.getType()) && !e.getPlayer().hasPermission("law.bypass.right-click-block")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerInteractEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager().getType() == EntityType.PLAYER) {
            Entity entity = e.getEntity();
            if (Law.get(entity.getLocation()).preventLeftClickEntity.contains(entity.getType()) && !entity.hasPermission("law.bypass.left-click-entity")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        Entity entity = e.getRightClicked();
        if (Law.get(entity.getLocation()).preventRightClickEntity.contains(entity.getType()) && !entity.hasPermission("law.bypass.right-click-entity")) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockIgnite(BlockIgniteEvent e) {
        if (Law.get(e.getBlock().getLocation()).preventIgniteBlock.contains(e.getCause())) {
            e.setCancelled(true);
        }
    }
}
