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
import java.util.function.Function;

public class Law {
    public Set<EntityType> preventEntitySpawn;
    public Set<EntityType> preventEntityTeleportByPortal;
    public Set<EntityType> preventEntityExplosion;
    public Set<EntityType> preventEntityBreakBlock;
    public Set<EntityType> preventEntityEnterVehicle;
    public Set<EntityType> preventEntityPickupItem;
    public Set<EntityDamageEvent.DamageCause> preventPlayerDamage;
    public boolean preventItemDamageByExplosion;
    public boolean preventItemDamageByFire;
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
    public Set<Material> preventPlaceBlock;
    public Set<Material> preventLeftClickBlock;
    public Set<Material> preventRightClickBlock;
    public Set<EntityType> preventLeftClickEntity;
    public Set<EntityType> preventRightClickEntity;
    public Set<BlockIgniteEvent.IgniteCause> preventIgniteBlock;

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
        law.preventEntitySpawn = mapToSet(config.getStringList("prevent-entity-spawn"), EntityType::valueOf);
        law.preventEntityTeleportByPortal = mapToSet(config.getStringList("prevent-entity-teleport-by-portal"), EntityType::valueOf);
        law.preventEntityExplosion = mapToSet(config.getStringList("prevent-entity-explosion"), EntityType::valueOf);
        law.preventEntityBreakBlock = mapToSet(config.getStringList("prevent-entity-break-block"), EntityType::valueOf);
        law.preventEntityEnterVehicle = mapToSet(config.getStringList("prevent-entity-enter-vehicle"), EntityType::valueOf);
        law.preventEntityPickupItem = mapToSet(config.getStringList("prevent-entity-pickup-item"), EntityType::valueOf);
        law.preventPlayerDamage = mapToSet(config.getStringList("prevent-player-damage"), EntityDamageEvent.DamageCause::valueOf);
        law.preventItemDamageByExplosion = config.getBoolean("prevent-item-damage-by-explosion");
        law.preventItemDamageByFire = config.getBoolean("prevent-item-damage-by-fire");
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
        law.preventPlaceBlock = mapToSet(config.getStringList("prevent-place-block"), Material::valueOf);
        law.preventLeftClickBlock = mapToSet(config.getStringList("prevent-left-click-block"), Material::valueOf);
        law.preventRightClickBlock = mapToSet(config.getStringList("prevent-right-click-block"), Material::valueOf);
        law.preventLeftClickEntity = mapToSet(config.getStringList("prevent-left-click-entity"), EntityType::valueOf);
        law.preventRightClickEntity = mapToSet(config.getStringList("prevent-right-click-entity"), EntityType::valueOf);
        law.preventIgniteBlock = mapToSet(config.getStringList("prevent-ignite-block"), BlockIgniteEvent.IgniteCause::valueOf);
        return law;
    }

    private static <T, R> Set<R> mapToSet(Collection<T> collection, Function<T, R> mapper) {
        if (collection == null || collection.isEmpty()) {
            return Collections.emptySet();
        }
        HashSet<R> result = new HashSet<>();
        for (T t : collection) {
            try {
                result.add(mapper.apply(t));
            } catch (IllegalArgumentException ignored) {
            }
        }
        return result;
    }
}
