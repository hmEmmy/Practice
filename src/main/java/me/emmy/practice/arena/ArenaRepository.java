package me.emmy.practice.arena;

import lombok.Getter;
import me.emmy.practice.arena.impl.StandAloneArena;
import me.emmy.practice.kit.Kit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @author Emmy
 * @project Practice
 * @date 10/12/2024 - 23:14
 */
@Getter
public class ArenaRepository {
    private final List<Arena> arenas;

    public ArenaRepository() {
        this.arenas = new ArrayList<>();
    }

    /**
     * Save an arena
     *
     * @param arena the arena to save
     */
    public void saveArena(Arena arena) {
        arena.saveArena();
    }

    /**
     * Delete an arena
     *
     * @param arena the arena to delete
     */
    public void deleteArena(Arena arena) {
        arena.deleteArena();
    }

    /**
     * Get an arena by its name
     *
     * @param name the name of the arena
     * @return the arena
     */
    public Arena getArenaByName(String name) {
        return this.arenas.stream().filter(arena -> arena.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    /**
     * Get a random arena by its kit
     *
     * @param kit the kit
     * @return the arena
     */
    public Arena getRandomArena(Kit kit) {
        List<Arena> availableArenas = this.arenas.stream()
                .filter(arena -> arena.getKits().contains(kit.getName()))
                .filter(Arena::isEnabled)
                .filter(arena -> !(arena instanceof StandAloneArena) || !((StandAloneArena) arena).isActive())
                .collect(Collectors.toList());

        if (availableArenas.isEmpty()) {
            return null;
        }

        Arena selectedArena = availableArenas.get(ThreadLocalRandom.current().nextInt(availableArenas.size()));
        if (selectedArena instanceof StandAloneArena) {
            ((StandAloneArena) selectedArena).setActive(true);
        }
        return selectedArena;
    }
}