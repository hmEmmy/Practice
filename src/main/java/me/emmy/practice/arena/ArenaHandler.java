package me.emmy.practice.arena;

import lombok.Getter;
import me.emmy.practice.Practice;
import me.emmy.practice.arena.enums.EnumArenaType;
import me.emmy.practice.arena.impl.FreeForAllArena;
import me.emmy.practice.arena.impl.SharedArena;
import me.emmy.practice.arena.impl.StandAloneArena;
import me.emmy.practice.util.LocationUtil;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author Emmy
 * @project Practice
 * @date 20/05/2024 - 16:54
 */
@Getter
public class ArenaHandler {
    private final ArenaRepository repository;
    private final FileConfiguration config;

    /**
     * Constructor for the ArenaHandler class.
     *
     * @param repository the arena repository
     */
    public ArenaHandler(ArenaRepository repository) {
        this.repository = repository;
        this.config = Practice.getInstance().getConfigHandler().getArenasConfig();
        this.loadArenas();
    }
    
    /**
     * Load all arenas from the arenas.yml file
     */
    public void loadArenas() {
        ConfigurationSection configSection = this.config.getConfigurationSection("arenas");
        if (configSection == null) {
            return;
        }

        for (String arenaName : configSection.getKeys(false)) {
            String name = "arenas." + arenaName;

            EnumArenaType arenaType = EnumArenaType.valueOf(this.config.getString(name + ".type"));
            Location minimum = LocationUtil.deserialize(this.config.getString(name + ".minimum"));
            Location maximum = LocationUtil.deserialize(this.config.getString(name + ".maximum"));

            Arena arena;
            switch (arenaType) {
                case SHARED:
                    arena = new SharedArena(
                            arenaName,
                            minimum,
                            maximum
                    );
                    break;
                case STANDALONE:
                    arena = new StandAloneArena(
                            arenaName,
                            minimum,
                            maximum
                    );
                    break;
                case FFA:
                    Location safeZonePos1 = LocationUtil.deserialize(this.config.getString(name + ".safezone.pos1"));
                    Location safeZonePos2 = LocationUtil.deserialize(this.config.getString(name + ".safezone.pos2"));

                    arena = new FreeForAllArena(
                            arenaName,
                            safeZonePos1,
                            safeZonePos2
                    );
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + arenaType);
            }

            if (this.config.contains(name + ".kits")) {
                for (String kitName : this.config.getStringList(name + ".kits")) {
                    if (Practice.getInstance().getKitHandler().getRepository().getKit(kitName) != null) {
                        arena.getKits().add(kitName);
                    }
                }
            }

            if (this.config.contains(name + ".pos1")) {
                arena.setPos1(LocationUtil.deserialize(this.config.getString(name + ".pos1")));
            }

            if (this.config.contains(name + ".pos2")) {
                arena.setPos2(LocationUtil.deserialize(this.config.getString(name + ".pos2")));
            }

            if (this.config.contains(name + ".center")) {
                arena.setCenter(LocationUtil.deserialize(this.config.getString(name + ".center")));
            }

            if (this.config.contains(name + ".display-name")) {
                arena.setDisplayName(this.config.getString(name + ".display-name"));
            }

            if (this.config.contains(name + ".enabled")) {
                arena.setEnabled(this.config.getBoolean(name + ".enabled"));
            }

            this.repository.getArenas().add(arena);
        }
    }
}