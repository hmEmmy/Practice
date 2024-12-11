package me.emmy.practice.kit;

import lombok.Getter;
import lombok.Setter;
import me.emmy.practice.kit.enums.EnumKitType;
import me.emmy.practice.kit.settings.KitSetting;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Emmy
 * @project Practice
 * @date 07/12/2024 - 19:33
 */
@Getter
@Setter
public class Kit {
    private String name;
    private String disclaimer;
    private String description;
    private String displayName;

    private Material icon;
    private int iconData;

    private ItemStack[] inventory;
    private ItemStack[] armor;

    private EnumKitType kitType;

    private boolean enabled;

    private final List<KitSetting> kitSettings;

    /**
     * Constructor for the Kit class.
     *
     * @param name the name of the kit
     */
    public Kit(String name) {
        this.enabled = false;
        this.name = name;
        this.disclaimer = "The " + name + " disclaimer.";
        this.description = "The " + name + " description.";
        this.displayName = "&7" + name;
        this.kitType = EnumKitType.REGULAR;
        this.icon = Material.DIAMOND_SWORD;
        this.iconData = 0;
        ItemStack[] inventory = new ItemStack[36];
        ItemStack[] armor = new ItemStack[4];
        this.inventory = inventory;
        this.armor = armor;
        this.kitSettings = new ArrayList<>();
    }

    /**
     * Method to add a kit setting.
     *
     * @param kitSetting The kit setting to add.
     */
    public void addKitSetting(KitSetting kitSetting) {
        kitSettings.add(kitSetting);
    }

    /**
     * Method to check if a setting is enabled.
     *
     * @param name The name of the setting.
     * @return Whether the setting is enabled.
     */
    public boolean isSettingEnabled(String name) {
        KitSetting kitSetting = kitSettings.stream()
                .filter(setting -> setting.getName().equals(name))
                .findFirst()
                .orElse(null);

        return kitSetting != null && kitSetting.isEnabled();
    }

    /**
     * Method to check if a setting is enabled.
     *
     * @param clazz The class of the setting.
     * @return Whether the setting is enabled.
     */
    public boolean isSettingEnabled(Class<? extends KitSetting> clazz) {
        KitSetting kitSetting = kitSettings.stream()
                .filter(setting -> setting.getClass().equals(clazz))
                .findFirst()
                .orElse(null);

        return kitSetting != null && kitSetting.isEnabled();
    }
}
