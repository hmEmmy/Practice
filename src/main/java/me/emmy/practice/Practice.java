package me.emmy.practice;

import lombok.Getter;
import me.emmy.practice.api.command.CommandFramework;
import me.emmy.practice.api.menu.MenuListener;
import me.emmy.practice.kit.KitHandler;
import me.emmy.practice.kit.KitRepository;
import me.emmy.practice.kit.command.KitCommand;
import me.emmy.practice.kit.command.impl.KitCreateCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

@Getter
public class Practice extends JavaPlugin {

    @Getter
    private static Practice instance;

    private CommandFramework commandFramework;
    private KitRepository kitRepository;
    private KitHandler kitHandler;

    @Override
    public void onEnable() {
        instance = this;

        this.registerManagersAndRepositories();
        this.registerCommands();
        this.registerListeners();
    }

    @Override
    public void onDisable() {
        this.kitRepository.getKits().forEach(this.kitHandler::saveKit);
    }

    private void registerManagersAndRepositories() {
        this.commandFramework = new CommandFramework(this);
        this.kitRepository = new KitRepository();
        this.kitHandler = new KitHandler(this.kitRepository);
    }

    private void registerCommands() {
        new KitCommand();
        new KitCreateCommand();
    }

    private void registerListeners() {
        Arrays.asList(
                new MenuListener()
        ).forEach(listener -> this.getServer().getPluginManager().registerEvents(listener, this));
    }
}