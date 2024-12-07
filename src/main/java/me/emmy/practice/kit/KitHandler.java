package me.emmy.practice.kit;

import lombok.Getter;
import me.emmy.practice.kit.enums.EnumKitType;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

/**
 * @author Emmy
 * @project Practice
 * @date 07/12/2024 - 19:57
 */
@Getter
public class KitHandler {
    private final KitRepository kitRepository;

    /**
     * Constructor for the KitHandler class.
     *
     * @param kitRepository the kit repository
     */
    public KitHandler(KitRepository kitRepository) {
        this.kitRepository = kitRepository;
        this.loadKits();
    }

    /**
     * Loads all the kits from the config.
     */
    public void loadKits() {
        FileConfiguration config = null; //TODO: get the config after implementing a confighandler

        for (String key : config.getConfigurationSection("kits").getKeys(false)) {
            Kit kit = new Kit(key);

            kit.setDescription(config.getString("kits." + key + ".description"));
            kit.setDisclaimer(config.getString("kits." + key + ".disclaimer"));
            kit.setIcon(Material.matchMaterial(config.getString("kits." + key + ".icon")));
            kit.setIconData(config.getInt("kits." + key + ".iconData"));
            kit.setInventory((ItemStack[]) config.getList("kits." + key + ".inventory").toArray());
            kit.setArmor((ItemStack[]) config.getList("kits." + key + ".armor").toArray());
            kit.setKitType(EnumKitType.valueOf(config.getString("kits." + key + ".kitType")));
            kit.setEnabled(config.getBoolean("kits." + key + ".enabled"));

            this.kitRepository.addKit(kit);
        }
    }

    /**
     * Creates a kit with the given name.
     *
     * @param kitName the name of the kit
     */
    public void createKit(String kitName) {
        Kit kit = new Kit(kitName);
        kit.setDescription("The " + kitName + " description.");
        kit.setDisclaimer("The " + kitName + " disclaimer.");
        kit.setIconData(0);
        kit.setInventory(new ItemStack[36]);
        kit.setArmor(new ItemStack[4]);
        kit.setKitType(EnumKitType.REGULAR);
        kit.setEnabled(false);

        this.kitRepository.addKit(kit);
    }

    /**
     * Saves a kit to the config.
     *
     * @param kit the kit to save
     */
    public void saveKit(Kit kit) {
        FileConfiguration config = null; //TODO: get the config after implementing a confighandler

        config.set("kits." + kit.getName() + ".description", kit.getDescription());
        config.set("kits." + kit.getName() + ".disclaimer", kit.getDisclaimer());
        config.set("kits." + kit.getName() + ".icon", kit.getIcon().name());
        config.set("kits." + kit.getName() + ".iconData", kit.getIconData());
        config.set("kits." + kit.getName() + ".inventory", kit.getInventory());
        config.set("kits." + kit.getName() + ".armor", kit.getArmor());
        config.set("kits." + kit.getName() + ".kitType", kit.getKitType().name());
        config.set("kits." + kit.getName() + ".enabled", kit.isEnabled());

        //save config
    }
}