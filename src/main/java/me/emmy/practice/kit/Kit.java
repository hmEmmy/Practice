package me.emmy.practice.kit;

import lombok.Getter;
import lombok.Setter;
import me.emmy.practice.kit.enums.EnumKitType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Emmy
 * @project Practice
 * @date 07/12/2024 - 19:33
 */
@Getter
@Setter
public class Kit {
    private String name;
    private String description;
    private String disclaimer;

    private Material icon;
    private int iconData;

    private ItemStack[] inventory;
    private ItemStack[] armor;

    private EnumKitType kitType;

    private boolean enabled;

    /**
     * Constructor for the Kit class.
     *
     * @param name the name of the kit
     */
    public Kit(String name) {
        this.name = name;
        this.description = "The " + name + " description.";
        this.disclaimer = "The " + name + " disclaimer.";
        this.icon = Material.DIAMOND_SWORD;
        this.iconData = 0;
        ItemStack[] inventory = new ItemStack[36];
        ItemStack[] armor = new ItemStack[4];
        this.inventory = inventory;
        this.armor = armor;
        this.kitType = EnumKitType.REGULAR;
        this.enabled = false;
    }
}
