package me.emmy.practice.arena.enums;

import lombok.Getter;

/**
 * @author Emmy
 * @project Practice
 * @date 20/05/2024 - 19:34
 */
@Getter
public enum EnumArenaType {
    SHARED, // Multiple players can play at the same time
    STANDALONE, //One player plays at a time and the arena resets after each game (for kits with building such as BedFight or BuildUHC. etc...)
    FFA // Free for all arena, no building, no breaking, no placing blocks, just pvp
}