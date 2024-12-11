package me.emmy.practice.arena.command.impl.data;

import me.emmy.practice.Practice;
import me.emmy.practice.arena.enums.EnumArenaType;
import me.emmy.practice.util.CC;
import me.emmy.practice.api.command.BaseCommand;
import me.emmy.practice.api.command.annotation.Command;
import me.emmy.practice.api.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Practice
 * @date 12/06/2024 - 21:56
 */
public class ArenaSetSafeZoneCommand extends BaseCommand {
    @Override
    @Command(name = "arena.setsafezone", permission = "practice.admin")
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 2) {
            player.sendMessage(CC.translate("&6Usage: &e/arena setsafezone &b<arenaName> <pos1/pos2>"));
            return;
        }

        String arenaName = args[0];
        String spawnType = args[1];

        if (Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName) == null) {
            player.sendMessage(CC.translate("&cAn arena with that name does not exist!"));
            return;
        }

        if (Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName).getType() != EnumArenaType.FFA) {
            player.sendMessage(CC.translate("&cYou can only set the safezone for Free-For-All arenas!"));
            return;
        }

        if (!spawnType.equalsIgnoreCase("pos1") && !spawnType.equalsIgnoreCase("pos2")) {
            player.sendMessage(CC.translate("&cInvalid spawn type! Valid types: pos1, pos2"));
            return;
        }

        if (spawnType.equalsIgnoreCase("pos1")) {
            Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName).setMaximum(player.getLocation());
            player.sendMessage(CC.translate("&aSafe Zone position 1 has been set for arena &b" + arenaName + "&a!"));
        } else {
            Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName).setMinimum(player.getLocation());
            player.sendMessage(CC.translate("&aSafe Zone position 2 has been set for arena &b" + arenaName + "&a!"));
        }

        Practice.getInstance().getArenaHandler().getRepository().saveArena(Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName));
    }
}
