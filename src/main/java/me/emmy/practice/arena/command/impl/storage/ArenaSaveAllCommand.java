package me.emmy.practice.arena.command.impl.storage;

import me.emmy.practice.Practice;
import me.emmy.practice.arena.Arena;
import me.emmy.practice.util.CC;
import me.emmy.practice.api.command.BaseCommand;
import me.emmy.practice.api.command.annotation.Command;
import me.emmy.practice.api.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author Remi
 * @project Practice
 * @date 5/20/2024
 */
public class ArenaSaveAllCommand extends BaseCommand {
    @Command(name = "arena.saveall", permission = "practice.admin")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        for (Arena arena : Practice.getInstance().getArenaHandler().getRepository().getArenas()) {
            Practice.getInstance().getArenaHandler().getRepository().saveArena(arena);
        }

        player.sendMessage(CC.translate("&aAll arenas have been saved!"));
    }
}
