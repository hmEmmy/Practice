package me.emmy.practice.kit.settings.impl;

import me.emmy.practice.kit.settings.KitSetting;
import me.emmy.practice.kit.settings.annotation.KitSettingData;

/**
 * @author Emmy
 * @project Practice
 * @date 27/08/2024 - 19:08
 */
@KitSettingData(name = "DenyMovement", description = "This denies the player movement during countdown.", enabled = false)
public class KitSettingDenyMovementImpl extends KitSetting {
}