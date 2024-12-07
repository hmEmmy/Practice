package me.emmy.practice.util;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

/**
 * @author Emmy
 * @project Practice
 * @date 07/12/2024 - 19:32
 */
@UtilityClass
public class CC {
    public String MENU_BAR = translate("&7&m------------------------");

    /**
     * Translate a string with the '&' character
     *
     * @param string the string to translate
     * @return the translated string
     */
    public String translate(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
