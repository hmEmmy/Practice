package me.emmy.practice.arena.impl;

import me.emmy.practice.Practice;
import me.emmy.practice.arena.Arena;
import me.emmy.practice.arena.ArenaType;
import me.emmy.practice.util.LocationUtil;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author Emmy
 * @project Practice
 * @date 20/05/2024 - 19:15
 */
public class FreeForAllArena extends Arena {
    /**
     * Constructor for the FreeForAllArena class.
     *
     * @param name The name of the arena.
     * @param minimum The minimum location of the arena.
     * @param maximum The maximum location of the arena.
     */
    public FreeForAllArena(String name, Location minimum, Location maximum) {
        super(name, minimum, maximum);
    }

    @Override
    public ArenaType getType() {
        return ArenaType.FFA;
    }

    @Override
    public void createArena() {
        Practice.getInstance().getArenaHandler().getRepository().getArenas().add(this);
        this.saveArena();
    }

    @Override
    public void saveArena() {
        String name = "arenas." + getName();
        FileConfiguration config = Practice.getInstance().getConfigHandler().getArenasConfig();

        config.set(name, null);
        config.set(name + ".type", getType().name());
        config.set(name + ".safezone.pos1", LocationUtil.serialize(getMinimum()));
        config.set(name + ".safezone.pos2", LocationUtil.serialize(getMaximum()));
        config.set(name + ".center", LocationUtil.serialize(getCenter()));
        config.set(name + ".pos1", LocationUtil.serialize(getPos1()));
        config.set(name + ".enabled", isEnabled());
        config.set(name + ".displayName", getDisplayName());

        Practice.getInstance().getConfigHandler().saveConfig(Practice.getInstance().getConfigHandler().getConfigFile("storage/arenas.yml"), config);
    }

    @Override
    public void deleteArena() {
        FileConfiguration config = Practice.getInstance().getConfigHandler().getArenasConfig();
        config.set("arenas." + getName(), null);

        Practice.getInstance().getArenaHandler().getRepository().getArenas().remove(this);
        Practice.getInstance().getConfigHandler().saveConfig(Practice.getInstance().getConfigHandler().getConfigFile("storage/arenas.yml"), config);
    }
}