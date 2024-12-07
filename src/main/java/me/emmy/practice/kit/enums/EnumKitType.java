package me.emmy.practice.kit.enums;

import lombok.Getter;

/**
 * @author Emmy
 * @project Practice
 * @date 07/12/2024 - 19:35
 */
@Getter
public enum EnumKitType {
    REGULAR("Regular"),
    ARCADE("Arcade")

    ;

    private final String name;

    /**
     *
     * @param name the readable name of the kit type.
     */
    EnumKitType(String name) {
        this.name = name;
    }
}