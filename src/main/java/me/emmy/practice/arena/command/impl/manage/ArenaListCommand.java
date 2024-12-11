package me.emmy.practice.arena.command.impl.manage;

import me.emmy.practice.Practice;
import me.emmy.practice.arena.enums.EnumArenaType;
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
public class ArenaListCommand extends BaseCommand {
    @Command(name = "arena.list", aliases = {"arenas"},  permission = "practice.admin")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        player.sendMessage("");
        player.sendMessage(CC.translate("     &b&lArena List &f(" + Practice.getInstance().getArenaHandler().getRepository().getArenas().size() + "&f)"));
        if (Practice.getInstance().getArenaHandler().getRepository().getArenas().isEmpty()) {
            player.sendMessage(CC.translate("      &f● &cNo Arenas available."));
        }

        Practice.getInstance().getArenaHandler().getRepository().getArenas().stream().filter(arena -> arena.getType() != EnumArenaType.FFA).forEach(arena ->
                player.sendMessage(CC.translate("      &f● &b" + arena.getName() + " &7(" + arena.getType().name() + ")" + (arena.isEnabled() ? " &aEnabled" : " &cDisabled"))))
        ;
        Practice.getInstance().getArenaHandler().getRepository().getArenas().stream().filter(arena -> arena.getType() == EnumArenaType.FFA).forEach(arena ->
                player.sendMessage(CC.translate("      &f● &b" + arena.getName() + " &7(" + arena.getType().name() + ")")))
        ;

        player.sendMessage("");
    }
}
