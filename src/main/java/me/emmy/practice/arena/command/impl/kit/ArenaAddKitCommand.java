package me.emmy.practice.arena.command.impl.kit;

import me.emmy.practice.Practice;
import me.emmy.practice.arena.ArenaType;
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
public class ArenaAddKitCommand extends BaseCommand {

    @Completer(name = "arena.addkit")
    public List<String> arenaAddKitCompleter(CommandArgs command) {
        List<String> completion = new ArrayList<>();

        if (command.getArgs().length == 1 && command.getPlayer().hasPermission("practice.admin")) {
            Practice.getInstance().getArenaHandler().getRepository().getArenas().forEach(arena -> completion.add(arena.getName()));
        }

        return completion;
    }

    @Command(name = "arena.addkit", permission = "practice.admin")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 2) {
            player.sendMessage(CC.translate("&6Usage: &e/arena addkit &b<arenaName> <kitName>"));
            return;
        }

        String arenaName = args[0];
        String kitName = args[1];

        if (Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName) == null) {
            player.sendMessage(CC.translate("&cAn arena with that name does not exist!"));
            return;
        }

        if (Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName).getType() == ArenaType.FFA) {
            player.sendMessage(CC.translate("&cYou cannot add kits to Free-For-All arenas!"));
            return;
        }

        if (Practice.getInstance().getKitHandler().getRepository().getKit(kitName) == null) {
            player.sendMessage(CC.translate("&cA kit with that name does not exist!"));
            return;
        }

        if (Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName).getKits().contains(kitName)) {
            player.sendMessage(CC.translate("&cThis arena already has this kit!"));
            return;
        }

        player.sendMessage(CC.translate("&aKit &b" + kitName + "&a has been added to arena &b" + arenaName + "&a!"));
        Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName).getKits().add(Practice.getInstance().getKitHandler().getRepository().getKit(kitName).getName());
        Practice.getInstance().getArenaHandler().getRepository().saveArena(Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName));
    }
}
