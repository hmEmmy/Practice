package me.emmy.practice;

import lombok.Getter;

import me.emmy.practice.api.command.*;
import me.emmy.practice.arena.*;
import me.emmy.practice.kit.*;

import me.emmy.practice.arena.listener.ArenaListener;
import me.emmy.practice.api.menu.MenuListener;
import me.emmy.practice.config.ConfigHandler;

import me.emmy.practice.arena.command.*;
import me.emmy.practice.arena.command.impl.kit.*;
import me.emmy.practice.arena.command.impl.data.*;
import me.emmy.practice.arena.command.impl.manage.*;
import me.emmy.practice.arena.command.impl.storage.*;

import me.emmy.practice.kit.command.*;
import me.emmy.practice.kit.command.impl.data.*;
import me.emmy.practice.kit.command.impl.inventory.*;
import me.emmy.practice.kit.command.impl.manage.*;
import me.emmy.practice.kit.command.impl.settings.KitSetSettingCommand;
import me.emmy.practice.kit.command.impl.settings.KitSettingsCommand;
import me.emmy.practice.kit.command.impl.toggle.*;

import me.emmy.practice.kit.settings.KitSettingRepository;
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
    private KitSettingRepository kitSettingRepository;

    @Override
    public void onEnable() {
        instance = this;

        this.initializeManagers();
        this.registerCommands();
        this.registerListeners();

        ServerUtil.setupWorld();
    }

    @Override
    public void onDisable() {
        ServerUtil.disconnectPlayers();
        ServerUtil.clearEntities();

        this.kitHandler.saveKits();
        this.arenaHandler.getRepository().getArenas().forEach(Arena::saveArena);
    }

    private void initializeManagers() {
        this.commandFramework = new CommandFramework(this);
        this.configHandler = new ConfigHandler();

        this.kitSettingRepository = new KitSettingRepository();

        KitRepository kitRepository = new KitRepository();
        this.kitHandler = new KitHandler(kitRepository);

        ArenaRepository arenaRepository = new ArenaRepository();
        this.arenaHandler = new ArenaHandler(arenaRepository);
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
        new KitSetSettingCommand();
        new KitSettingsCommand();

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