package me.emmy.practice.kit.command.impl;

import me.emmy.practice.Practice;
import me.emmy.practice.api.command.BaseCommand;
import me.emmy.practice.api.command.Command;
import me.emmy.practice.api.command.CommandArgs;
import me.emmy.practice.util.CC;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Practice
 * @date 07/12/2024 - 19:49
 */
public class KitCreateCommand extends BaseCommand {
    @Command(name = "kit.create", permission = "practice.owner")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&6Usage: &e/kit create &b<name>"));
            return;
        }

        String kitName = args[0];
        if (Practice.getInstance().getKitRepository().getKit(kitName) != null) {
            player.sendMessage(CC.translate("&cA kit with that name already exists."));
            return;
        }

        Practice.getInstance().getKitHandler().createKit(kitName);
        player.sendMessage(CC.translate("&aSuccessfully created a new kit called &b" + kitName + "&a."));
    }
}