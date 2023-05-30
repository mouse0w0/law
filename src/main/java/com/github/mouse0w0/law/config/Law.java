package com.github.mouse0w0.law.config;

import com.github.mouse0w0.law.Main;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.io.File;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Law {
    public Predicate<EntityType> preventEntitySpawn;
    public Predicate<EntityType> preventEntityTeleportByPortal;
    public Predicate<EntityType> preventEntityExplosion;
    public Predicate<EntityType> preventEntityBreakBlock;
    public Predicate<EntityType> preventEntityEnterVehicle;
    public Predicate<EntityType> preventEntityPickupItem;
    public BiPredicate<EntityType, EntityDamageEvent.DamageCause> preventEntityDamage;
    public BiPredicate<EntityType, EntityType> preventEntityDamageByEntity;
    public boolean preventVillagerToWitch;
    public boolean preventVillagerInfection;
    public boolean preventVillagerCure;
    public boolean preventZombieDrowning;
    public boolean preventCreeperCharge;
    public boolean preventPigToZombiePigman;
    public boolean preventMooshroomSwitch;
    public boolean preventFireSpread;
    public boolean preventFireBurn;
    public boolean preventFireFade;
    public boolean preventSnowForm;
    public boolean preventSnowMelt;
    public boolean preventSnowmanGenerateSnow;
    public boolean preventIceForm;
    public boolean preventIceMelt;
    public boolean preventCoralDeath;
    public boolean preventTurtleEggTrampling;
    public boolean preventFarmlandDecay;
    public boolean preventLeavesDecay;
    public boolean preventDragonEggTeleport;
    public boolean preventBedExplosion;
    public boolean preventRespawnAnchorExplosion;
    public boolean preventWaterFlow;
    public boolean preventLavaFlow;
    public boolean keepInventoryOnDeath;
    public boolean keepExpOnDeath;
    public boolean disableWeatherRaining;
    public boolean disableWeatherThunder;
    public boolean disableWeatherLightning;
    public Predicate<Material> preventPlaceBlock;
    public Predicate<Material> preventBreakBlock;
    public Predicate<Material> preventLeftClickBlock;
    public Predicate<Material> preventRightClickBlock;
    public Predicate<EntityType> preventLeftClickEntity;
    public Predicate<EntityType> preventRightClickEntity;
    public Predicate<BlockIgniteEvent.IgniteCause> preventIgniteBlock;

    private static Law globalLaw;
    private static Map<String, Law> worldLaws;

    public static Law get(World world) {
        Law worldLaw = worldLaws.get(world.getName());
        return worldLaw != null ? worldLaw : globalLaw;
    }

    public static void load() {
        Main plugin = Main.getPlugin();
        plugin.saveResource("global.yml", false);
        File dataFolder = plugin.getDataFolder();
        YamlConfiguration globalConfig = YamlConfiguration.loadConfiguration(new File(dataFolder, "global.yml"));
        globalLaw = load(globalConfig);
        worldLaws = new HashMap<>();
        File worldsFolder = new File(dataFolder, "worlds");
        if (!worldsFolder.exists()) worldsFolder.mkdirs();
        for (File file : worldsFolder.listFiles()) {
            String fileName = file.getName();
            if (file.isFile() && fileName.endsWith(".yml")) {
                String worldName = fileName.substring(0, fileName.length() - 4);
                YamlConfiguration worldConfig = YamlConfiguration.loadConfiguration(file);
                worldConfig.setDefaults(globalConfig);
                worldLaws.put(worldName, load(worldConfig));
            }
        }
    }

    private static Law load(ConfigurationSection config) {
        Law law = new Law();
        law.preventEntitySpawn = getPredicate(config, "prevent-entity-spawn", EntityType.class);
        law.preventEntityTeleportByPortal = getPredicate(config, "prevent-entity-teleport-by-portal", EntityType.class);
        law.preventEntityExplosion = getPredicate(config, "prevent-entity-explosion", EntityType.class);
        law.preventEntityBreakBlock = getPredicate(config, "prevent-entity-break-block", EntityType.class);
        law.preventEntityEnterVehicle = getPredicate(config, "prevent-entity-enter-vehicle", EntityType.class);
        law.preventEntityPickupItem = getPredicate(config, "prevent-entity-pickup-item", EntityType.class);
        law.preventEntityDamage = getBiPredicate(config, "prevent-entity-damage", EntityType.class, EntityDamageEvent.DamageCause.class);
        law.preventEntityDamageByEntity = getBiPredicate(config, "prevent-entity-damage-by-entity", EntityType.class, EntityType.class);
        law.preventVillagerToWitch = config.getBoolean("prevent-villager-to-witch");
        law.preventVillagerInfection = config.getBoolean("prevent-villager-infection");
        law.preventVillagerCure = config.getBoolean("prevent-villager-cure");
        law.preventZombieDrowning = config.getBoolean("prevent-zombie-drowning");
        law.preventCreeperCharge = config.getBoolean("prevent-creeper-charge");
        law.preventPigToZombiePigman = config.getBoolean("prevent-pig-to-zombie-pigman");
        law.preventMooshroomSwitch = config.getBoolean("prevent-mooshroom-switch");
        law.preventFireSpread = config.getBoolean("prevent-fire-spread");
        law.preventFireBurn = config.getBoolean("prevent-fire-burn");
        law.preventFireFade = config.getBoolean("prevent-fire-fade");
        law.preventSnowForm = config.getBoolean("prevent-snow-form");
        law.preventSnowmanGenerateSnow = config.getBoolean("prevent-snowman-generate-snow");
        law.preventSnowMelt = config.getBoolean("prevent-snow-melt");
        law.preventIceForm = config.getBoolean("prevent-ice-form");
        law.preventIceMelt = config.getBoolean("prevent-ice-melt");
        law.preventCoralDeath = config.getBoolean("prevent-coral-death");
        law.preventTurtleEggTrampling = config.getBoolean("prevent-turtle-egg-trampling");
        law.preventFarmlandDecay = config.getBoolean("prevent-farmland-decay");
        law.preventLeavesDecay = config.getBoolean("prevent-leaves-decay");
        law.preventDragonEggTeleport = config.getBoolean("prevent-dragon-egg-teleport");
        law.preventBedExplosion = config.getBoolean("prevent-bed-explosion");
        law.preventRespawnAnchorExplosion = config.getBoolean("prevent-respawn-anchor-explosion");
        law.preventWaterFlow = config.getBoolean("prevent-water-flow");
        law.preventLavaFlow = config.getBoolean("prevent-lava-flow");
        law.keepInventoryOnDeath = config.getBoolean("keep-inventory-on-death");
        law.keepExpOnDeath = config.getBoolean("keep-exp-on-death");
        law.disableWeatherRaining = config.getBoolean("disable-weather-raining");
        law.disableWeatherThunder = config.getBoolean("disable-weather-thunder");
        law.disableWeatherLightning = config.getBoolean("disable-weather-lightning");
        law.preventPlaceBlock = getPredicate(config, "prevent-place-block", Material.class);
        law.preventBreakBlock = getPredicate(config, "prevent-break-block", Material.class);
        law.preventLeftClickBlock = getPredicate(config, "prevent-left-click-block", Material.class);
        law.preventRightClickBlock = getPredicate(config, "prevent-right-click-block", Material.class);
        law.preventLeftClickEntity = getPredicate(config, "prevent-left-click-entity", EntityType.class);
        law.preventRightClickEntity = getPredicate(config, "prevent-right-click-entity", EntityType.class);
        law.preventIgniteBlock = getPredicate(config, "prevent-ignite-block", BlockIgniteEvent.IgniteCause.class);
        return law;
    }

    @SuppressWarnings("unchecked")
    private static <T extends Enum<T>>
    Predicate<T> getPredicate(ConfigurationSection config, String path, Class<T> enumClass) {
        Object value = config.get(path);
        if (Boolean.TRUE.equals(value)) {
            return t -> true;
        } else if (value instanceof List) {
            List<String> enumList = (List<String>) value;
            Set<T> set = new HashSet<>();
            for (String enumName : enumList) {
                try {
                    set.add(Enum.valueOf(enumClass, enumName));
                } catch (IllegalArgumentException ignored) {
                }
            }
            if (set.isEmpty()) {
                return t -> false;
            }
            return set::contains;
        } else {
            return t -> false;
        }
    }

    private static <T extends Enum<T>, U extends Enum<U>>
    BiPredicate<T, U> getBiPredicate(ConfigurationSection config, String path, Class<T> enumClass0, Class<U> enumClass1) {
        Object value = config.get(path);
        if (Boolean.TRUE.equals(value)) {
            return (t, u) -> true;
        } else if (value instanceof ConfigurationSection) {
            ConfigurationSection section = (ConfigurationSection) value;
            Map<T, Predicate<U>> map = new HashMap<>();
            for (String key : section.getKeys(false)) {
                try {
                    map.put(Enum.valueOf(enumClass0, key), getPredicate(section, key, enumClass1));
                } catch (IllegalArgumentException ignored) {
                }
            }
            if (map.isEmpty()) {
                return (t, u) -> false;
            }
            return (t, u) -> {
                Predicate<U> uPredicate = map.get(t);
                return uPredicate != null && uPredicate.test(u);
            };
        } else {
            return (t, u) -> false;
        }
    }
}
