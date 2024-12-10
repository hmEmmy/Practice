package me.emmy.practice.arena.command.impl.kit;

import me.emmy.practice.Practice;
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
public class ArenaKitListCommand extends BaseCommand {

    @Completer(name = "arena.kitlist")
    public List<String> arenaKitListCompleter(CommandArgs command) {
        List<String> completion = new ArrayList<>();

        if (command.getArgs().length == 1 && command.getPlayer().hasPermission("practice.admin")) {
            Practice.getInstance().getArenaHandler().getRepository().getArenas().forEach(arena -> completion.add(arena.getName()));
        }

        return completion;
    }

    @Command(name = "arena.kitlist", permission = "practice.admin")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&6Usage: &e/arena kitlist &b<arenaName>"));
            return;
        }

        String arenaName = args[0];
        if (Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName) == null) {
            player.sendMessage(CC.translate("&cAn arena with that name does not exist!"));
            return;
        }

        player.sendMessage("");
        player.sendMessage(CC.translate("     &b&l" + arenaName + " Kit List &f(" + Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName).getKits().size() + "&f)"));
        if (Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName).getKits().isEmpty()) {
            player.sendMessage(CC.translate("      &f● &cNo Arena Kits available."));
        }
        Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName).getKits().forEach(kit -> player.sendMessage(CC.translate("      &f● &b" + kit)));
        player.sendMessage("");
    }
}
