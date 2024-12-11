package me.emmy.practice.arena.command.impl.data;

import me.emmy.practice.Practice;
import me.emmy.practice.arena.enums.EnumArenaType;
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
public class ArenaSetSpawnCommand extends BaseCommand {

    @Completer(name = "arena.setspawn")
    public List<String> arenaSetSpawnCompleter(CommandArgs command) {
        List<String> completion = new ArrayList<>();

        if (command.getArgs().length == 1 && command.getPlayer().hasPermission("practice.admin")) {
            Practice.getInstance().getArenaHandler().getRepository().getArenas().forEach(arena -> completion.add(arena.getName()));
        }

        return completion;
    }

    @Command(name = "arena.setspawn", permission = "practice.admin")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 2) {
            player.sendMessage(CC.translate("&6Usage: &e/arena setspawn &b<arenaName> <pos1/pos2/ffa>"));
            return;
        }

        String arenaName = args[0];
        String spawnType = args[1];

        if (Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName) == null) {
            player.sendMessage(CC.translate("&cAn arena with that name does not exist!"));
            return;
        }

        if (!spawnType.equalsIgnoreCase("pos1") && !spawnType.equalsIgnoreCase("pos2") && !spawnType.equalsIgnoreCase("ffa")) {
            player.sendMessage(CC.translate("&cInvalid spawn type! Valid types: pos1, pos2, ffa"));
            return;
        }

        switch (spawnType.toLowerCase()) {
            case "pos1":
                if (Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName).getType() == EnumArenaType.FFA) {
                    player.sendMessage(CC.translate("&cFFA Arenas do not need a spawn position!"));
                    return;
                }
                Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName).setPos1(player.getLocation());
                player.sendMessage(CC.translate("&aSpawn Position 1 has been set for arena &b" + arenaName + "&a!"));
                break;
            case "ffa":
                if (Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName).getType() != EnumArenaType.FFA) {
                    player.sendMessage(CC.translate("&cThis arena is not an FFA arena!"));
                    return;
                }
                Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName).setPos1(player.getLocation());
                player.sendMessage(CC.translate("&aSpawn Position has been set for arena &b" + arenaName + "&a!"));
                break;
            default:
                if (Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName).getType() == EnumArenaType.FFA) {
                    player.sendMessage(CC.translate("&cFFA Arenas do not need a spawn position!"));
                    return;
                }
                Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName).setPos2(player.getLocation());
                player.sendMessage(CC.translate("&aSpawn Position 2 has been set for arena &b" + arenaName + "&a!"));
                break;
        }

        Practice.getInstance().getArenaHandler().getRepository().saveArena(Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName));
    }
}
