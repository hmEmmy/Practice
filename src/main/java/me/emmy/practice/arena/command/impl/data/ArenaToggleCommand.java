package me.emmy.practice.arena.command.impl.data;

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
public class ArenaToggleCommand extends BaseCommand {

    @Completer(name = "arena.toggle")
    public List<String> arenaToggleCompleter(CommandArgs command) {
        List<String> completion = new ArrayList<>();

        if (command.getArgs().length == 1 && command.getPlayer().hasPermission("practice.admin")) {
            Practice.getInstance().getArenaHandler().getRepository().getArenas().forEach(arena -> completion.add(arena.getName()));
        }

        return completion;
    }

    @Command(name = "arena.toggle", permission = "practice.admin")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&6Usage: &e/arena toggle &b<arenaName>"));
            return;
        }

        String arenaName = args[0];
        if (Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName) == null) {
            player.sendMessage(CC.translate("&cAn arena with that name does not exist!"));
            return;
        }

        if (Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName).getType() == ArenaType.FFA) {
            player.sendMessage(CC.translate("&cYou cannot enable or disable Free-For-All arenas!"));
            return;
        }

        if (Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName).getMinimum() == null || Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName).getMaximum() == null || Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName).getPos1() == null || Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName).getPos2() == null) {
            player.sendMessage(CC.translate("&cYou must finish configuring this arena before enabling or disabling!"));
            return;
        }

        if (Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName).getKits().isEmpty()) {
            player.sendMessage(CC.translate("&cYou must add at least one kit to this arena before enabling or disabling!"));
            return;
        }

        Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName).setEnabled(!Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName).isEnabled());
        Practice.getInstance().getArenaHandler().getRepository().saveArena(Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName));
        player.sendMessage(CC.translate("&aArena &b" + arenaName + "&a has been " + (Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName).isEnabled() ? "enabled" : "disabled") + "&a!"));
    }
}
