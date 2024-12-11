package me.emmy.practice.kit.settings.impl;

import me.emmy.practice.kit.settings.KitSetting;
import me.emmy.practice.kit.settings.annotation.KitSettingData;

/**
 * @author Emmy
 * @project Practice
 * @date 16/10/2024 - 17:35
 */
@KitSettingData(name = "NoHunger", description = "Prevent the player from getting hungry", enabled = false)
public class KitSettingNoHungerImpl extends KitSetting {
}