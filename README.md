# 插件介绍
本插件基于Bukkit API开发，用于在服务器中控制实体和方块行为，并为不同世界设置不同规则。本插件基于1.16.5开发，但已完成全版本兼容，除部分功能无法在较低版本运行外，其余功能均可运行。插件功能如下所示：
- 阻止实体生成、通过传送门、爆炸、破坏方块、乘坐载具、捡起物品。
- 阻止玩家受到伤害、放置方块、破坏方块、点击方块和实体。
- 阻止物品受到爆炸、火焰和岩浆伤害。
- 阻止生物转变（村民、女巫、僵尸村民、溺尸、苦力怕、僵尸猪人、蘑菇牛）。
- 阻止火焰蔓延、烧毁方块、熄灭。
- 阻止雪形成、融化。
- 阻止雪傀儡产雪。
- 阻止冰形成、融化。
- 阻止珊瑚失水。
- 阻止踩碎海龟蛋。
- 阻止耕地退化。
- 阻止树叶腐烂。
- 阻止龙蛋传送。
- 阻止床和重生锚爆炸。
- 阻止水和岩浆流动。
- 死亡时保留经验和物品。
- 控制天气。
- 阻止方块被点燃。

# 如何配置
插件初次运行后会在插件安装目录下创建`Law`文件夹，`Law`文件夹的内容如下：
- `config.yml`：插件的配置文件。
- `lang.yml`：插件的语言文件。
- `global.yml`：全局规则配置文件。
- `worlds`：存放各个世界的规则配置文件的文件夹。

`global.yml`内的文件内容如下所示：
```yaml
# 阻止实体生成（1.7及以上版本）
# 可用实体类型：https://hub.spigotmc.org/javadocs/spigot/org/bukkit/entity/EntityType.html
# prevent-entity-spawn: true时匹配所有实体
prevent-entity-spawn: []

# 阻止实体通过传送门
# 可用实体类型：https://hub.spigotmc.org/javadocs/spigot/org/bukkit/entity/EntityType.html
# prevent-entity-teleport-by-portal: true时匹配所有实体
prevent-entity-teleport-by-portal: []

# 阻止实体爆炸
# 苦力怕（CREEPER）、TNT（PRIMED_TNT）、末影水晶（ENDER_CRYSTAL）
# 凋零头颅（WITHER_SKULL）、恶魂火球（FIREBALL）、TNT矿车（MINECART_TNT）
# 可用实体类型：https://hub.spigotmc.org/javadocs/spigot/org/bukkit/entity/EntityType.html
# prevent-entity-explosion: true时匹配所有实体
prevent-entity-explosion: []

# 阻止实体破坏方块、画以及展示框
# 苦力怕（CREEPER）、TNT（PRIMED_TNT）、末影水晶（ENDER_CRYSTAL）、凋零头颅（WITHER_SKULL）
# 恶魂火球（FIREBALL）、烈焰人火球（SMALL_FIREBALL）、TNT矿车（MINECART_TNT）、末影人（ENDERMAN）
# 僵尸（ZOMBIE）、凋零（WITHER）、羊（SHEEP）、劫掠兽（RAVAGER）
# 可用实体类型：https://hub.spigotmc.org/javadocs/spigot/org/bukkit/entity/EntityType.html
# prevent-entity-break-block: true时匹配所有实体
prevent-entity-break-block: [CREEPER, PRIMED_TNT, ENDER_CRYSTAL, WITHER, WITHER_SKULL, FIREBALL, SMALL_FIREBALL, MINECART_TNT, ENDERMAN]

# 阻止实体乘坐载具
# 可用实体类型：https://hub.spigotmc.org/javadocs/spigot/org/bukkit/entity/EntityType.html
# prevent-entity-enter-vehicle: true时匹配所有实体
prevent-entity-enter-vehicle: []

# 阻止实体捡起物品
# 可用实体类型：https://hub.spigotmc.org/javadocs/spigot/org/bukkit/entity/EntityType.html
# prevent-entity-pickup-item: true时匹配所有实体
prevent-entity-pickup-item: []

# 阻止实体受到伤害
# 对画和展示框无效
# 可用实体类型：https://hub.spigotmc.org/javadocs/spigot/org/bukkit/entity/EntityType.html
# 可用伤害类型：https://hub.spigotmc.org/javadocs/spigot/org/bukkit/event/entity/EntityDamageEvent.DamageCause.html
# prevent-entity-damage: true时阻止所有实体受到伤害
prevent-entity-damage:
  # 阻止盔甲架受到爆炸伤害
  ARMOR_STAND: [ENTITY_EXPLOSION, BLOCK_EXPLOSION]
  # 阻止玩家受到伤害
  # PLAYER: []
  # 阻止物品受到爆炸伤害
  # DROPPED_ITEM: [ENTITY_EXPLOSION, BLOCK_EXPLOSION]
  # 阻止物品受到火焰和岩浆伤害
  # DROPPED_ITEM: [LAVA, FIRE, FIRE_TICK]

# 阻止实体受到实体伤害
# 可用实体类型：https://hub.spigotmc.org/javadocs/spigot/org/bukkit/entity/EntityType.html
# prevent-entity-damage-by-entity: true时阻止所有实体受到实体伤害
prevent-entity-damage-by-entity:
  # 示例
  _UNKNOWN: []
  # 阻止玩家受到铁傀儡伤害
  # PLAYER: [IRON_GOLEM]

# 阻止村民转变为女巫（1.13及以上版本）
prevent-villager-to-witch: false

# 阻止村民转变为僵尸村民（1.13及以上版本）
prevent-villager-infection: false

# 阻止僵尸村民转变为村民（1.13及以上版本）
prevent-villager-cure: false

# 阻止僵尸转变为溺尸
prevent-zombie-drowning: false

# 阻止苦力怕充能
prevent-creeper-charge: false

# 阻止猪转变为僵尸猪人
prevent-pig-to-zombie-pigman: false

# 阻止蘑菇牛切换颜色
prevent-mooshroom-switch: false

# 阻止火焰蔓延
prevent-fire-spread: false

# 阻止火焰摧毁方块
prevent-fire-burn: false

# 阻止火焰熄灭
prevent-fire-fade: false

# 阻止雪形成
prevent-snow-form: false

# 阻止雪傀儡产雪
prevent-snowman-generate-snow: false

# 阻止雪融化
prevent-snow-melt: false

# 阻止冰形成
prevent-ice-form: false

# 阻止冰融化
prevent-ice-melt: false

# 阻止珊瑚失水
prevent-coral-death: false

# 阻止踩碎海龟蛋
prevent-turtle-egg-trampling: false

# 阻止耕地退化
prevent-farmland-decay: false

# 阻止树叶腐烂
prevent-leaves-decay: false

# 阻止龙蛋传送
prevent-dragon-egg-teleport: false

# 阻止床爆炸
prevent-bed-explosion: false

# 阻止重生锚爆炸
prevent-respawn-anchor-explosion: false

# 阻止水流动
prevent-water-flow: false

# 阻止岩浆流动
prevent-lava-flow: false

# 死亡时保留物品
keep-inventory-on-death: false

# 死亡时保留经验
keep-exp-on-death: false

# 禁用下雨（包括雷暴）
disable-weather-raining: false

# 禁用雷暴
disable-weather-thunder: false

# 禁用下雨和雷暴打雷
disable-weather-lightning: false

# 阻止玩家放置方块
# 可用方块类型：https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html
# prevent-place-block: true时匹配所有方块
prevent-place-block: []

# 阻止玩家破坏方块
# 可用方块类型：https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html
# prevent-break-block: true时匹配所有方块
prevent-break-block: []

# 阻止玩家左键交互方块
# 可用方块类型：https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html
# prevent-left-click-block: true时匹配所有方块
prevent-left-click-block: []

# 阻止玩家右键交互方块
# 可用方块类型：https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html
# prevent-right-click-block: true时匹配所有方块
prevent-right-click-block: []

# 阻止玩家左键交互实体
# 可用实体类型：https://hub.spigotmc.org/javadocs/spigot/org/bukkit/entity/EntityType.html
# prevent-left-click-entity: true时匹配所有实体
prevent-left-click-entity: []

# 阻止玩家右键交互实体
# 可用实体类型：https://hub.spigotmc.org/javadocs/spigot/org/bukkit/entity/EntityType.html
# prevent-right-click-entity: true时匹配所有实体
prevent-right-click-entity: []

# 阻止点燃方块
# 可用点燃类型：https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/event/block/BlockIgniteEvent.IgniteCause.html
# prevent-ignite-block: true时匹配所有点燃原因
prevent-ignite-block: []
```

如果要为每个世界单独配置规则，需要在`worlds`文件夹下创建对应世界名的`yml`文件，例如主世界：`world.yml`，下界：`DIM-1.yml`。并将需要覆盖的全局配置的配置项，写入到相应世界的配置文件中，然后使用`UTF-8`编码格式保存配置文件。

# 指令列表
- `/law help` - 查看插件指令帮助
- `/law reload` - 重载插件的配置文件
- `/law world` - 查看当前所在世界名
- `/law query` - 切换查询模式（点击方块或实体查看其名）

# 权限列表
- `law.admin.help` - 查看插件指令帮助。
- `law.admin.reload` - 重载插件的配置文件。
- `law.admin.world` - 查看当前所在世界名。
- `law.bypass.place-block` - 绕过放置方块检查。
- `law.bypass.break-block` - 绕过方块破坏检查。
- `law.bypass.left-click-block` - 绕过左键点击方块检查。
- `law.bypass.right-click-block` - 绕过右键点击方块检查。
- `law.bypass.left-click-entity` - 绕过左键点击实体检查。
- `law.bypass.right-click-entity` - 绕过右键点击实体检查。

# 下载插件

- [百度网盘下载](https://pan.baidu.com/s/1V3YTptQWiRpBMlSglfwy-A?pwd=ffjc)
- [Github 加速下载](https://ghproxy.com/?q=https%3A%2F%2Fgithub.com%2Fmouse0w0%2Flaw%2Freleases%2Fdownload%2F2.0.0%2FLaw-2.0.0.jar)
- [Github 官方下载](https://github.com/mouse0w0/law/releases/download/2.0.0/Law-2.0.0.jar)

# 开源信息
本插件所有源代码托管于Github，使用**BSD-3-Clause license**开源协议。

https://github.com/mouse0w0/law

# 统计信息
![bStats](https://bstats.org/signatures/bukkit/Law.svg)

**本插件所用所有代码均为原创,不存在借用/抄袭等行为**
