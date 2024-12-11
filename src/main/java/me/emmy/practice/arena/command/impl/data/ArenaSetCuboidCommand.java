package me.emmy.practice.arena.command.impl.data;

import me.emmy.practice.Practice;
import me.emmy.practice.arena.enums.EnumArenaType;
import me.emmy.practice.arena.selection.ArenaSelection;
import me.emmy.practice.util.CC;
import me.emmy.practice.api.command.BaseCommand;
import me.emmy.practice.api.command.annotation.Command;
import me.emmy.practice.api.command.CommandArgs;
import me.emmy.practice.api.command.annotation.Completer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Remi
 * @project Practice
 * @date 5/20/2024
 */
public class ArenaSetCuboidCommand extends BaseCommand {

    @Completer(name = "arena.setcuboid")
    public List<String> arenaCuboidCompleter(CommandArgs command) {
        List<String> completion = new ArrayList<>();

        if (command.getArgs().length == 1 && command.getPlayer().hasPermission("practice.admin")) {
            Practice.getInstance().getArenaHandler().getRepository().getArenas().forEach(arena -> completion.add(arena.getName()));
        }

        return completion;
    }

    @Command(name = "arena.setcuboid", permission = "practice.admin")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&6Usage: &e/arena setcuboid &b<arenaName>"));
            return;
        }

        ArenaSelection arenaSelection = ArenaSelection.createSelection(player);
        if (!arenaSelection.hasSelection()) {
            player.sendMessage(CC.translate("&cYou must select the minimum and maximum locations for the arena."));
            return;
        }

        String arenaName = args[0];
        if (Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName) == null) {
            player.sendMessage(CC.translate("&cAn arena with that name does not exist!"));
            return;
        }

        if (Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName).getType() == EnumArenaType.FFA) {
            player.sendMessage(CC.translate("&cYou cannot set cuboids for Free-For-All arenas! You must use: &4/arena setsafezone pos1/pos2&c."));
            return;
        }

        Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName).setMinimum(arenaSelection.getMinimum());
        Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName).setMaximum(arenaSelection.getMaximum());
        player.sendMessage(CC.translate("&aCuboid has been set for arena &b" + arenaName + "&a!"));

        Practice.getInstance().getArenaHandler().getRepository().saveArena(Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName));
    }
}
