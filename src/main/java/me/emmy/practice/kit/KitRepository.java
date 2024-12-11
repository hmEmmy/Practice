package me.emmy.practice.kit;

import lombok.Getter;
import me.emmy.practice.Practice;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Emmy
 * @project Practice
 * @date 07/12/2024 - 19:51
 */
@Getter
public class KitRepository {
    private final List<Kit> kits;

    public KitRepository() {
        this.kits = new ArrayList<>();
    }

    /**
     * Get a kit from the list of kits by the Kit object.
     *
     * @param kitName the name of the kit
     * @return the kit
     */
    public Kit getKit(Kit kitName) {
        return this.kits.stream().filter(kit -> kit.equals(kitName)).findFirst().orElse(null);
    }

    /**
     * Get a kit from the list of kits by the name of the kit.
     *
     * @param kitName the name of the kit
     * @return the kit
     */
    public Kit getKit(String kitName) {
        return this.kits.stream().filter(kit -> kit.getName().equalsIgnoreCase(kitName)).findFirst().orElse(null);
    }

    /**
     * Add a kit to the list of kits.
     *
     * @param kitName the kit to add
     */
    public void addKit(Kit kitName) {
        this.kits.add(kitName);
    }

    /**
     * Remove a kit from the list of kits.
     *
     * @param kitName the kit to remove
     */
    public void removeKit(Kit kitName) {
        this.kits.remove(kitName);

        FileConfiguration config = Practice.getInstance().getConfigHandler().getKitsConfig();

        config.set("kits." + kitName.getName(), null);
        Practice.getInstance().getConfigHandler().saveConfig(Practice.getInstance().getConfigHandler().getConfigFile("storage/kits.yml"), config);
    }
}