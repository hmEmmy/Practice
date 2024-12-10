package me.emmy.practice.kit;

import lombok.Getter;
import me.emmy.practice.Practice;
import me.emmy.practice.kit.enums.EnumKitType;
import me.emmy.practice.util.CC;
import me.emmy.practice.util.InventoryUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author Emmy
 * @project Practice
 * @date 07/12/2024 - 19:57
 */
@Getter
public class KitHandler {
    private final KitRepository repository;
    private final FileConfiguration config;

    /**
     * Constructor for the KitHandler class.
     *
     * @param repository the kit repository
     */
    public KitHandler(KitRepository repository) {
        this.repository = repository;
        this.config = Practice.getInstance().getConfigHandler().getKitsConfig();
        this.loadKits();
    }

    /**
     * Loads all the kits from the config.
     */
    public void loadKits() {
        ConfigurationSection configSection = this.config.getConfigurationSection("kits");
        if (configSection == null) {
            return;
        }

        for (String kitName : configSection.getKeys(false)) {
            Kit kit = new Kit(kitName);

            kit.setEnabled(this.config.getBoolean("kits." + kitName + ".enabled"));
            kit.setDisclaimer(this.config.getString("kits." + kitName + ".disclaimer"));
            kit.setDescription(this.config.getString("kits." + kitName + ".description"));
            kit.setDisplayName(this.config.getString("kits." + kitName + ".display-name"));
            kit.setKitType(EnumKitType.valueOf(this.config.getString("kits." + kitName + ".kit-type")));
            kit.setIcon(Material.matchMaterial(this.config.getString("kits." + kitName + ".icon.material")));
            kit.setIconData(this.config.getInt("kits." + kitName + ".icon.durability"));
            try {
                kit.setInventory(InventoryUtil.itemStackArrayFromBase64(this.config.getString("kits." + kitName + ".inventory")));
                kit.setArmor(InventoryUtil.itemStackArrayFromBase64(this.config.getString("kits." + kitName + ".armor")));
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(CC.translate("&cFailed to load kit " + kitName + " inventory."));
            }

            this.repository.addKit(kit);
        }
    }

    /**
     * Creates a kit with the given name.
     *
     * @param kitName the name of the kit
     */
    public void createKit(String kitName) {
        Kit kit = new Kit(kitName);
        this.repository.addKit(kit);
        this.saveKit(kit);
    }

    /**
     * Saves a kit to the config.
     *
     * @param kit the kit to save
     */
    public void saveKit(Kit kit) {
        this.config.set("kits." + kit.getName() + ".enabled", kit.isEnabled());
        this.config.set("kits." + kit.getName() + ".disclaimer", kit.getDisclaimer());
        this.config.set("kits." + kit.getName() + ".description", kit.getDescription());
        this.config.set("kits." + kit.getName() + ".display-name", kit.getDisplayName());
        this.config.set("kits." + kit.getName() + ".kit-type", kit.getKitType().name());
        this.config.set("kits." + kit.getName() + ".icon.material", kit.getIcon().name());
        this.config.set("kits." + kit.getName() + ".icon.durability", kit.getIconData());
        this.config.set("kits." + kit.getName() + ".inventory", InventoryUtil.itemStackArrayToBase64(kit.getInventory()));
        this.config.set("kits." + kit.getName() + ".armor", InventoryUtil.itemStackArrayToBase64(kit.getArmor()));

        Practice.getInstance().getConfigHandler().saveConfig(Practice.getInstance().getConfigHandler().getConfigFile("kits.yml"), this.config);
    }

    public void saveKits() {
        for (Kit kit : this.repository.getKits()) {
            this.saveKit(kit);
        }
    }
}