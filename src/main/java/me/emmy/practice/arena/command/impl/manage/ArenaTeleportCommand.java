package me.emmy.practice.arena.command.impl.manage;

import me.emmy.practice.Practice;
import me.emmy.practice.arena.Arena;
import me.emmy.practice.util.CC;
import me.emmy.practice.api.command.BaseCommand;
import me.emmy.practice.api.command.annotation.Command;
import me.emmy.practice.api.command.CommandArgs;
import me.emmy.practice.api.command.annotation.Completer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Emmy
 * @project Practice
 * @date 01/06/2024 - 00:08
 */
public class ArenaTeleportCommand extends BaseCommand {

    @Completer(name = "arena.teleport", aliases = "arena.tp")
    public List<String> arenaTeleportCompleter(CommandArgs command) {
        List<String> completion = new ArrayList<>();

        if (command.getArgs().length == 1 && command.getPlayer().hasPermission("practice.admin")) {
            Practice.getInstance().getArenaHandler().getRepository().getArenas().forEach(arena -> completion.add(arena.getName()));
        }

        return completion;
    }

    @Override
    @Command(name = "arena.teleport", aliases = "arena.tp", permission = "practice.admin")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&6Usage: &e/arena teleport &b<arenaName>"));
            return;
        }

        String arenaName = args[0];
        Arena arena = Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName);

        if (arena == null) {
            player.sendMessage(CC.translate("&cAn arena with that name does not exist."));
            return;
        }

        player.teleport(arena.getCenter());

    }
}
