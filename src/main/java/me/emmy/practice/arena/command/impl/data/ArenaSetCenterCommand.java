package me.emmy.practice.arena.command.impl.data;

import me.emmy.practice.Practice;
import me.emmy.practice.arena.Arena;
import me.emmy.practice.arena.ArenaHandler;
import me.emmy.practice.arena.ArenaRepository;
import me.emmy.practice.util.CC;
import me.emmy.practice.api.command.BaseCommand;
import me.emmy.practice.api.command.Command;
import me.emmy.practice.api.command.CommandArgs;
import me.emmy.practice.api.command.Completer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Remi
 * @project Practice
 * @date 5/20/2024
 */
public class ArenaSetCenterCommand extends BaseCommand {

    @Completer(name = "arena.setcenter")
    public List<String> arenaSetCenterCompleter(CommandArgs command) {
        List<String> completion = new ArrayList<>();

        if (command.getArgs().length == 1 && command.getPlayer().hasPermission("practice.admin")) {
            Practice.getInstance().getArenaHandler().getRepository().getArenas().forEach(arena -> completion.add(arena.getName()));
        }

        return completion;
    }


    @Command(name = "arena.setcenter", permission = "practice.admin")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&6Usage: &e/arena setcenter &b<arenaName>"));
            return;
        }

        ArenaRepository arenaRepository = Practice.getInstance().getArenaHandler().getRepository();
        Arena arena = arenaRepository.getArenaByName(args[0]);
        if (arena == null) {
            player.sendMessage(CC.translate("&cAn arena with that name does not exist!"));
            return;
        }

        Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arena.getName()).setCenter(player.getLocation());
        Practice.getInstance().getArenaHandler().getRepository().saveArena(arena);
        player.sendMessage(CC.translate("&aCenter has been set for arena &b" + arena.getName() + "&a!"));
    }
}