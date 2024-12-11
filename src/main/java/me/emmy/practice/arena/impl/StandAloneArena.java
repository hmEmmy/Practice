package me.emmy.practice.arena.impl;

import lombok.Getter;
import lombok.Setter;
import me.emmy.practice.Practice;
import me.emmy.practice.arena.Arena;
import me.emmy.practice.arena.enums.EnumArenaType;
import me.emmy.practice.util.LocationUtil;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author Emmy
 * @project Practice
 * @date 20/05/2024 - 19:14
 */
@Setter
@Getter
public class StandAloneArena extends Arena {
    private boolean active = false;

    /**
     * Constructor for the StandAloneArena class.
     *
     * @param name The name of the arena.
     * @param minimum The minimum location of the arena.
     * @param maximum The maximum location of the arena.
     */
    public StandAloneArena(String name, Location minimum, Location maximum) {
        super(name, minimum, maximum);
    }

    @Override
    public EnumArenaType getType() {
        return EnumArenaType.STANDALONE;
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
        config.set(name + ".minimum", LocationUtil.serialize(getMinimum()));
        config.set(name + ".maximum", LocationUtil.serialize(getMaximum()));
        config.set(name + ".center", LocationUtil.serialize(getCenter()));
        config.set(name + ".pos1", LocationUtil.serialize(getPos1()));
        config.set(name + ".pos2", LocationUtil.serialize(getPos2()));
        config.set(name + ".kits", getKits());
        config.set(name + ".enabled", isEnabled());
        config.set(name + ".display-name", getDisplayName());
        
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