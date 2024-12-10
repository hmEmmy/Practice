package me.emmy.practice;

import lombok.Getter;
import me.emmy.practice.api.command.CommandFramework;
import me.emmy.practice.api.menu.MenuListener;
import me.emmy.practice.arena.ArenaHandler;
import me.emmy.practice.arena.ArenaRepository;
import me.emmy.practice.arena.command.ArenaCommand;
import me.emmy.practice.arena.command.impl.data.*;
import me.emmy.practice.arena.command.impl.kit.ArenaAddKitCommand;
import me.emmy.practice.arena.command.impl.kit.ArenaKitListCommand;
import me.emmy.practice.arena.command.impl.kit.ArenaRemoveKitCommand;
import me.emmy.practice.arena.command.impl.manage.*;
import me.emmy.practice.arena.command.impl.storage.ArenaSaveAllCommand;
import me.emmy.practice.arena.listener.ArenaListener;
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
import me.emmy.practice.util.ServerUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

@Getter
public class Practice extends JavaPlugin {

    @Getter
    private static Practice instance;

    private CommandFramework commandFramework;
    private ConfigHandler configHandler;
    private KitHandler kitHandler;
    private ArenaHandler arenaHandler;

    @Override
    public void onEnable() {
        instance = this;

        this.registerManagersAndRepositories();
        this.registerCommands();
        this.registerListeners();

        ServerUtil.setupWorld();
    }

    @Override
    public void onDisable() {
        ServerUtil.disconnectPlayers();
        ServerUtil.clearEntities();

        this.kitHandler.saveKits();
        this.arenaHandler.saveArenas();
    }

    private void registerManagersAndRepositories() {
        this.commandFramework = new CommandFramework(this);
        this.configHandler = new ConfigHandler();
        this.kitHandler = new KitHandler(new KitRepository());
        this.arenaHandler = new ArenaHandler(new ArenaRepository());
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

        new ArenaCommand();
        new ArenaSetSafeZoneCommand();
        new ArenaSetCenterCommand();
        new ArenaCreateCommand();
        new ArenaSetCuboidCommand();
        new ArenaTeleportCommand();
        new ArenaDeleteCommand();
        new ArenaAddKitCommand();
        new ArenaKitListCommand();
        new ArenaListCommand();
        new ArenaRemoveKitCommand();
        new ArenaSaveAllCommand();
        new ArenaSetSpawnCommand();
        new ArenaToggleCommand();
        new ArenaToolCommand();
        new ArenaSetDisplayNameCommand();
        new ArenaViewCommand();
    }

    private void registerListeners() {
        Arrays.asList(
                new MenuListener(),
                new ArenaListener()
        ).forEach(listener -> this.getServer().getPluginManager().registerEvents(listener, this));
    }
}