package com.github.mouse0w0.law.listener;

import com.github.mouse0w0.law.config.Law;
import com.github.mouse0w0.law.util.DamageSourceUtils;
import com.github.mouse0w0.law.util.EnumUtils;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.util.Set;

public class LawListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityPortal(EntityPortalEvent e) {
        Entity entity = e.getEntity();
        if (Law.get(entity.getWorld()).preventEntityTeleportByPortal.test(entity.getType())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent e) {
        Entity entity = e.getEntity();
        EntityType type = entity.getType();
        World world = entity.getWorld();
        if (Law.get(world).preventEntityExplosion.test(type)) {
            e.setCancelled(true);
            e.blockList().clear();
        } else if (Law.get(world).preventEntityBreakBlock.test(type)) {
            e.blockList().clear();
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onHangingBreak(HangingBreakEvent e) {
        Entity entity = e.getEntity();
        Entity remover = DamageSourceUtils.getEntityDamage();
        if (remover == null && e instanceof HangingBreakByEntityEvent) {
            remover = ((HangingBreakByEntityEvent) e).getRemover();
        }
        if (remover == null) {
            return;
        }
        EntityType removerType = remover.getType();
        if (removerType == EntityType.PLAYER) {
            if (Law.get(remover.getWorld()).preventLeftClickEntity.test(entity.getType()) && !remover.hasPermission("law.bypass.left-click-entity")) {
                e.setCancelled(true);
            }
        } else {
            Law law = Law.get(remover.getWorld());
            if (law.preventEntityBreakBlock.test(removerType) || law.preventEntityExplosion.test(removerType)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityChangeBlock(EntityChangeBlockEvent e) {
        Entity entity = e.getEntity();
        if (Law.get(entity.getWorld()).preventEntityBreakBlock.test(entity.getType())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onVehicleEnter(VehicleEnterEvent e) {
        Entity entity = e.getEntered();
        if (Law.get(entity.getWorld()).preventEntityEnterVehicle.test(entity.getType())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        if (Law.get(entity.getWorld()).preventEntityDamage.test(entity.getType(), e.getCause())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onCreeperPower(CreeperPowerEvent e) {
        if (e.getCause() == CreeperPowerEvent.PowerCause.LIGHTNING) {
            if (Law.get(e.getEntity().getWorld()).preventCreeperCharge) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockSpread(BlockSpreadEvent e) {
        Block block = e.getSource();
        if (block.getType() == Material.FIRE && Law.get(block.getWorld()).preventFireSpread) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockBurn(BlockBurnEvent e) {
        if (Law.get(e.getBlock().getWorld()).preventFireBurn) {
            e.setCancelled(true);
        }
    }

    private static final Set<Material> CORAL = EnumUtils.allOf(Material.class,
            "TUBE_CORAL_BLOCK", "BRAIN_CORAL_BLOCK", "BUBBLE_CORAL_BLOCK", "FIRE_CORAL_BLOCK", "HORN_CORAL_BLOCK",
            "TUBE_CORAL", "BRAIN_CORAL", "BUBBLE_CORAL", "FIRE_CORAL", "HORN_CORAL",
            "TUBE_CORAL_FAN", "BRAIN_CORAL_FAN", "BUBBLE_CORAL_FAN", "FIRE_CORAL_FAN", "HORN_CORAL_FAN",
            "TUBE_CORAL_WALL_FAN", "BRAIN_CORAL_WALL_FAN", "BUBBLE_CORAL_WALL_FAN", "FIRE_CORAL_WALL_FAN", "HORN_CORAL_WALL_FAN");

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockFade(BlockFadeEvent e) {
        Block block = e.getBlock();
        Material type = block.getType();
        if (type == Material.FIRE) {
            if (Law.get(block.getWorld()).preventFireFade) {
                e.setCancelled(true);
            }
        } else if (type == Material.SNOW) {
            if (Law.get(block.getWorld()).preventSnowMelt) {
                e.setCancelled(true);
            }
        } else if (type == Material.ICE) {
            if (Law.get(block.getWorld()).preventIceMelt) {
                e.setCancelled(true);
            }
        } else if (CORAL.contains(type)) {
            if (Law.get(block.getWorld()).preventCoralDeath) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockForm(BlockFormEvent e) {
        if (e instanceof EntityBlockFormEvent) {
            Entity entity = ((EntityBlockFormEvent) e).getEntity();
            if (entity.getType() == EntityType.SNOWMAN) {
                if (Law.get(entity.getWorld()).preventSnowmanGenerateSnow) {
                    e.setCancelled(true);
                }
            }
        } else {
            Block block = e.getBlock();
            switch (block.getType()) {
                case SNOW:
                    if (Law.get(block.getWorld()).preventSnowForm) {
                        e.setCancelled(true);
                    }
                    break;
                case ICE:
                    if (Law.get(block.getWorld()).preventIceForm) {
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

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityInteract(EntityInteractEvent e) {
        Block block = e.getBlock();
        if (block.getType() == FARMLAND && Law.get(block.getWorld()).preventFarmlandDecay) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent e) {
        switch (e.getAction()) {
            case PHYSICAL: {
                Block block = e.getClickedBlock();
                if (block.getType() == FARMLAND && Law.get(block.getWorld()).preventFarmlandDecay) {
                    e.setCancelled(true);
                }
                break;
            }
            case RIGHT_CLICK_BLOCK: {
                Block block = e.getClickedBlock();
                if (block.getType() == DRAGON_EGG && Law.get(block.getWorld()).preventDragonEggTeleport) {
                    e.setCancelled(true);
                }
                break;
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onLeavesDecay(LeavesDecayEvent e) {
        if (Law.get(e.getBlock().getWorld()).preventLeavesDecay) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent e) {
        Law law = Law.get(e.getEntity().getWorld());
        if (law.keepInventoryOnDeath) {
            e.getDrops().clear();
            e.setKeepInventory(true);
        }
        if (law.keepExpOnDeath) {
            e.setDroppedExp(0);
            e.setKeepLevel(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onWeatherChange(WeatherChangeEvent e) {
        if (e.toWeatherState() && Law.get(e.getWorld()).disableWeatherRaining) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onThunderChange(ThunderChangeEvent e) {
        if (e.toThunderState() && Law.get(e.getWorld()).disableWeatherThunder) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent e) {
        Block block = e.getBlock();
        if (Law.get(block.getWorld()).preventPlaceBlock.test(block.getType()) && !e.getPlayer().hasPermission("law.bypass.place-block")) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        if (Law.get(block.getWorld()).preventBreakBlock.test(block.getType()) && !e.getPlayer().hasPermission("law.bypass.break-block")) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerInteractBlock(PlayerInteractEvent e) {
        switch (e.getAction()) {
            case LEFT_CLICK_BLOCK: {
                Block block = e.getClickedBlock();
                if (Law.get(block.getWorld()).preventLeftClickBlock.test(block.getType()) && !e.getPlayer().hasPermission("law.bypass.left-click-block")) {
                    e.setCancelled(true);
                }
                break;
            }
            case RIGHT_CLICK_BLOCK: {
                Block block = e.getClickedBlock();
                if (Law.get(block.getWorld()).preventRightClickBlock.test(block.getType()) && !e.getPlayer().hasPermission("law.bypass.right-click-block")) {
                    e.setCancelled(true);
                }
                break;
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteractItem(PlayerInteractEvent e) {
        if (e.hasItem()) {
            Player player = e.getPlayer();
            if (Law.get(player.getWorld()).preventUseItem.test(e.getItem().getType()) && !player.hasPermission("law.bypass.use-item")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockDispense(BlockDispenseEvent e) {
        Block block = e.getBlock();
        if (Law.get(block.getWorld()).preventDispenseItem.test(e.getItem().getType())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        Entity entity = e.getEntity();
        Entity damager = e.getDamager();
        Law law = Law.get(entity.getWorld());
        if (law.preventEntityDamageByEntity.test(entity.getType(), damager.getType())) {
            e.setCancelled(true);
            return;
        }

        if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
            if (law.preventEntityExplosion.test(damager.getType())) {
                e.setCancelled(true);
            }
        } else {
            if (damager.getType() == EntityType.PLAYER) {
                if (law.preventLeftClickEntity.test(entity.getType()) && !damager.hasPermission("law.bypass.left-click-entity")) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        Entity entity = e.getRightClicked();
        if (Law.get(entity.getWorld()).preventRightClickEntity.test(entity.getType()) && !entity.hasPermission("law.bypass.right-click-entity")) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockIgnite(BlockIgniteEvent e) {
        if (Law.get(e.getBlock().getWorld()).preventIgniteBlock.test(e.getCause())) {
            e.setCancelled(true);
        }
    }
}
