package me.emmy.practice;

import lombok.Getter;
import me.emmy.practice.api.command.CommandFramework;
import me.emmy.practice.api.menu.MenuListener;
import me.emmy.practice.config.ConfigHandler;
import me.emmy.practice.kit.KitHandler;
import me.emmy.practice.kit.KitRepository;
import me.emmy.practice.kit.command.KitCommand;
import me.emmy.practice.kit.command.impl.manage.KitCreateCommand;
import me.emmy.practice.kit.command.impl.manage.KitDeleteCommand;
import me.emmy.practice.kit.command.impl.data.*;
import me.emmy.practice.kit.command.impl.inventory.KitGetInvCommand;
import me.emmy.practice.kit.command.impl.inventory.KitSetInvCommand;
import me.emmy.practice.kit.command.impl.manage.KitListCommand;
import me.emmy.practice.kit.command.impl.manage.KitViewCommand;
import me.emmy.practice.kit.command.impl.toggle.KitDisableCommand;
import me.emmy.practice.kit.command.impl.toggle.KitEnableCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

@Getter
public class Practice extends JavaPlugin {

    @Getter
    private static Practice instance;

    private CommandFramework commandFramework;
    private ConfigHandler configHandler;
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
        this.configHandler = new ConfigHandler();
        this.kitRepository = new KitRepository();
        this.kitHandler = new KitHandler(this.kitRepository);
    }

    private void registerCommands() {
        new KitCommand();
        new KitCreateCommand();
        new KitDeleteCommand();
        new KitDescriptionCommand();
        new KitDisableCommand();
        new KitDisclaimerCommand();
        new KitEnableCommand();
        new KitGetInvCommand();
        new KitSetInvCommand();
        new KitIconCommand();
        new KitListCommand();
        new KitDisplayNameCommand();
        new KitTypeCommand();
        new KitViewCommand();
    }

    private void registerListeners() {
        Arrays.asList(
                new MenuListener()
        ).forEach(listener -> this.getServer().getPluginManager().registerEvents(listener, this));
    }
}