package me.emmy.practice.kit;

import lombok.Getter;
import me.emmy.practice.Practice;
import me.emmy.practice.kit.enums.EnumKitType;
import me.emmy.practice.kit.settings.KitSetting;
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

            this.loadKitSettings(this.config, kitName, kit);
            this.addMissingKitSettings(this.config, kitName, kit);
            this.repository.addKit(kit);
            this.addKitToQueue(kit);
        }
    }

    /**
     * Method to load the settings of a kit.
     *
     * @param config The configuration file.
     * @param key    The path key.
     * @param kit    The kit.
     */
    private void loadKitSettings(FileConfiguration config, String key, Kit kit) {
        ConfigurationSection settingsSection = config.getConfigurationSection(key + ".settings");
        if (settingsSection == null) {
            this.applyDefaultSettings(config, key, kit);
            return;
        }

        for (String settingName : settingsSection.getKeys(false)) {
            boolean enabled = config.getBoolean(key + ".settings." + settingName);

            KitSetting kitSetting = Practice.getInstance().getKitSettingRepository().createSettingByName(settingName);
            if (kitSetting != null) {
                kitSetting.setEnabled(enabled);
                kit.addKitSetting(kitSetting);
            }
        }
    }

    /**
     * Handle creation in config for each kit that has missing settings.
     *
     * @param kit    The kit.
     * @param config The configuration file.
     * @param key    The path key.
     */
    private void addMissingKitSettings(FileConfiguration config, String key, Kit kit) {
        Practice.getInstance().getKitSettingRepository().getSettings().forEach(setting -> {
            if (kit.getKitSettings().stream().noneMatch(kitSetting -> kitSetting.getName().equals(setting.getName()))) {
                kit.addKitSetting(setting);
                Bukkit.getConsoleSender().sendMessage(CC.translate("&cAdded missing setting " + setting.getName() + " to kit " + kit.getName() + ". Now saving it into the kits config..."));
                this.saveKitSettings(config, key, kit);
            }
        });
    }

    /**
     * Method to apply the default settings to a kit.
     *
     * @param config The configuration file.
     * @param key    The path key.
     * @param kit    The kit.
     */
    public void applyDefaultSettings(FileConfiguration config, String key, Kit kit) {
        Practice.getInstance().getKitSettingRepository().getSettings().forEach(setting -> {
            kit.addKitSetting(setting);
            config.set(key + ".settings." + setting.getName(), setting.isEnabled());
        });

        Practice.getInstance().getConfigHandler().saveConfig(Practice.getInstance().getConfigHandler().getConfigFile("storage/kits.yml"), config);
    }

    /**
     * Method to save the settings of a kit.
     *
     * @param config The configuration file.
     * @param key    The path key.
     * @param kit    The kit.
     */
    private void saveKitSettings(FileConfiguration config, String key, Kit kit) {
        for (KitSetting kitSetting : kit.getKitSettings()) {
            config.set(key + ".settings." + kitSetting.getName(), kitSetting.isEnabled());
        }
    }

    /**
     * Method to add a kit to the queue.
     *
     * @param kit The kit to add.
     */
    private void addKitToQueue(Kit kit) {
        if (!kit.isEnabled()) return;
        //new Queue(kit, false);

        //if (kit.isSettingEnabled(KitSettingRankedImpl.class)) {
        //   new Queue(kit, true);
        //}
    }

    /**
     * Creates a kit with the given name.
     *
     * @param kitName the name of the kit
     */
    public void createKit(String kitName) {
        Kit kit = new Kit(kitName);
        Practice.getInstance().getKitSettingRepository().applyAllSettingsToKit(kit);
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

        if (kit.getKitSettings() == null) {
            this.applyDefaultSettings(config, "kits." + kit.getName(), kit);
        } else {
            this.saveKitSettings(config, "kits." + kit.getName(), kit);
        }

        Practice.getInstance().getConfigHandler().saveConfig(Practice.getInstance().getConfigHandler().getConfigFile("storage/kits.yml"), this.config);
    }

    public void saveKits() {
        for (Kit kit : this.repository.getKits()) {
            this.saveKit(kit);
        }
    }
}