package me.emmy.practice.kit.settings.impl;

import me.emmy.practice.kit.settings.KitSetting;
import me.emmy.practice.kit.settings.annotation.KitSettingData;

/**
 * @author Emmy
 * @project Practice
 * @date 16/10/2024 - 14:01
 */
@KitSettingData(name = "NoDamage", description = "Prevent the player from taking damage", enabled = false)
public class KitSettingNoDamageImpl extends KitSetting {
}