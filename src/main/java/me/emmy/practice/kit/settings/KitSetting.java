package me.emmy.practice.kit.settings;

import lombok.Getter;
import lombok.Setter;
import me.emmy.practice.kit.settings.annotation.KitSettingData;

/**
 * @author Remi
 * @project Practice
 * @date 5/21/2024
 */
@Getter
@Setter
public class KitSetting {
    private final String name;
    private boolean enabled;

    public KitSetting() {
        KitSettingData data = getClass().getAnnotation(KitSettingData.class);
        this.name = data.name();
        this.enabled = data.enabled();
    }
}