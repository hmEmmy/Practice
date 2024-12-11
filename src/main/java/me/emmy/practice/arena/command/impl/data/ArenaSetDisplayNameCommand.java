package me.emmy.practice.arena.command.impl.data;

import me.emmy.practice.Practice;
import me.emmy.practice.api.command.BaseCommand;
import me.emmy.practice.api.command.annotation.Command;
import me.emmy.practice.api.command.CommandArgs;
import me.emmy.practice.util.CC;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

/**
 * @author Emmy
 * @project Practice
 * @date 15/09/2024 - 11:45
 */
public class ArenaSetDisplayNameCommand extends BaseCommand {
    @Command(name = "arena.setdisplayname", permission = "Practice.command.arena.setdisplayname", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length < 2) {
            sender.sendMessage(CC.translate("&6Usage: &e/arena setdisplayname &b<arenaName> <displayName>"));
            return;
        }

        String arenaName = args[0];
        String displayName = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        if (Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName) == null) {
            sender.sendMessage(CC.translate("&cAn arena with that name does not exist!"));
            return;
        }

        Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName).setDisplayName(displayName);
        Practice.getInstance().getArenaHandler().getRepository().getArenaByName(arenaName).saveArena();
        sender.sendMessage(CC.translate("&aSuccessfully set the display name of the arena &e" + arenaName + " &ato &e" + displayName + "&a."));
    }
}
