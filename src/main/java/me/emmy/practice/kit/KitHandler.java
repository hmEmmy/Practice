package me.emmy.practice.kit;

import lombok.Getter;
import me.emmy.practice.Practice;
import me.emmy.practice.kit.enums.EnumKitType;
import me.emmy.practice.util.CC;
import me.emmy.practice.util.InventoryUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author Emmy
 * @project Practice
 * @date 07/12/2024 - 19:57
 */
@Getter
public class KitHandler {
    private final KitRepository kitRepository;
    private final FileConfiguration config;

    /**
     * Constructor for the KitHandler class.
     *
     * @param kitRepository the kit repository
     */
    public KitHandler(KitRepository kitRepository) {
        this.kitRepository = kitRepository;
        this.config = Practice.getInstance().getConfigHandler().getKitsConfig();
        this.loadKits();
    }

    /**
     * Loads all the kits from the config.
     */
    public void loadKits() {
        if (!this.config.contains("kits")) {
            return;
        }

        for (String key : this.config.getConfigurationSection("kits").getKeys(false)) {
            Kit kit = new Kit(key);

            kit.setEnabled(this.config.getBoolean("kits." + key + ".enabled"));
            kit.setDisclaimer(this.config.getString("kits." + key + ".disclaimer"));
            kit.setDescription(this.config.getString("kits." + key + ".description"));
            kit.setDisplayName(this.config.getString("kits." + key + ".display-name"));
            kit.setKitType(EnumKitType.valueOf(this.config.getString("kits." + key + ".kit-type")));
            kit.setIcon(Material.matchMaterial(this.config.getString("kits." + key + ".icon.material")));
            kit.setIconData(this.config.getInt("kits." + key + ".icon.durability"));
            try {
                kit.setInventory(InventoryUtil.itemStackArrayFromBase64(this.config.getString("kits." + key + ".inventory")));
                kit.setArmor(InventoryUtil.itemStackArrayFromBase64(this.config.getString("kits." + key + ".armor")));
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(CC.translate("&cFailed to load kit " + key + " inventory."));
            }

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
        this.kitRepository.addKit(kit);
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
}