package me.emmy.practice.kit.settings;

import lombok.Getter;
import lombok.Setter;
import me.emmy.practice.kit.Kit;
import me.emmy.practice.kit.settings.impl.*;
import me.emmy.practice.util.CC;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Remi
 * @project Practice
 * @date 5/21/2024
 */
@Getter
@Setter
public class KitSettingRepository {
    private final List<KitSetting> settings = new ArrayList<>();
    private final Map<String, Class<? extends KitSetting>> settingClasses = new HashMap<>();

    public KitSettingRepository() {
        this.registerSetting(KitSettingBuildImpl.class);
        this.registerSetting(KitSettingRankedImpl.class);
        this.registerSetting(KitSettingBoxingImpl.class);
        this.registerSetting(KitSettingSumoImpl.class);
        this.registerSetting(KitSettingSpleefImpl.class);
        this.registerSetting(KitSettingDenyMovementImpl.class);
        this.registerSetting(KitSettingParkourImpl.class);
        this.registerSetting(KitSettingLivesImpl.class);
        this.registerSetting(KitSettingNoDamageImpl.class);
        this.registerSetting(KitSettingNoHungerImpl.class);
        this.registerSetting(KitSettingBattleRushImpl.class);
        this.registerSetting(KitSettingStickFightImpl.class);
    }

    /**
     * Method to register a setting class.
     *
     * @param clazz The setting class.
     */
    public void registerSetting(Class<? extends KitSetting> clazz) {
        try {
            KitSetting instance = clazz.getDeclaredConstructor().newInstance();
            this.settings.add(instance);
            this.settingClasses.put(instance.getName(), clazz);
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(CC.translate("&cFailed to register setting class " + clazz.getSimpleName() + "!"));
        }
    }

    /**
     * Method to create a new setting instance by its name.
     *
     * @param name The name of the setting.
     * @return A new instance of the setting.
     */
    public KitSetting createSettingByName(String name) {
        Class<? extends KitSetting> clazz = this.settingClasses.get(name);
        if (clazz != null) {
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(CC.translate("&cFailed to create setting instance for " + name + "!"));
            }
        }
        return null;
    }

    /**
     * Method to get a setting by its name.
     *
     * @param name The name of the setting.
     * @return The setting.
     */
    public KitSetting getSettingByName(String name) {
        return this.settings.stream().filter(setting -> setting.getName().equals(name)).findFirst().orElse(null);
    }

    /**
     * Method to get a setting by its class.
     *
     * @param clazz The class of the setting.
     * @return The setting.
     */
    public KitSetting getSettingByClass(Class<? extends KitSetting> clazz) {
        return this.settings.stream().filter(setting -> setting.getClass().equals(clazz)).findFirst().orElse(null);
    }

    /**
     * Method to apply all settings to a kit.
     *
     * @param kit The kit to apply the settings to.
     */
    public void applyAllSettingsToKit(Kit kit) {
        for (KitSetting setting : this.settings) {
            kit.addKitSetting(setting);
        }
    }
}