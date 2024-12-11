package me.emmy.practice.kit.command.impl.manage;

import me.emmy.practice.Practice;
import me.emmy.practice.api.command.BaseCommand;
import me.emmy.practice.api.command.annotation.Command;
import me.emmy.practice.api.command.CommandArgs;
import me.emmy.practice.kit.Kit;
import me.emmy.practice.kit.KitHandler;
import me.emmy.practice.kit.settings.KitSetting;
import me.emmy.practice.util.CC;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

/**
 * @author Emmy
 * @project Practice
 * @date 10/12/2024 - 22:59
 */
public class KitViewCommand extends BaseCommand {
    @Command(name = "kit.view", permission = "practice.kit.view", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length < 1) {
            sender.sendMessage(CC.translate("&6Usage: &e/kit view &b<kitName>"));
            return;
        }

        KitHandler kitHandler = Practice.getInstance().getKitHandler();
        Kit kit = kitHandler.getRepository().getKit(args[0]);
        if (kit == null) {
            sender.sendMessage(CC.translate("&cA kit with that name does not exist."));
            return;
        }

        sender.sendMessage("");
        sender.sendMessage(CC.translate("&b&lKit " + kit.getName() +  " &f(" + (kit.isEnabled() ? "&aEnabled" : "&cDisabled") + "&f)"));
        sender.sendMessage(CC.translate(" &f● &bName: &f" + kit.getName()));
        sender.sendMessage(CC.translate(" &f● &bIcon: &f" + kit.getIcon().name().toLowerCase() + " &7(" + kit.getIconData() + ")"));
        sender.sendMessage(CC.translate(" &f● &bDisplay Name: &f" + kit.getDisplayName()));
        sender.sendMessage(CC.translate(" &f● &bDescription: &f" + kit.getDescription()));
        sender.sendMessage(CC.translate(" &f● &bDisclaimer: &f" + kit.getDisclaimer()));
        sender.sendMessage(CC.translate(" &f● &bSettings:"));
        kit.getKitSettings().forEach(setting -> sender.sendMessage(CC.translate("  &f- &b" + setting.getName() + " &f(" + (setting.isEnabled() ? "&aEnabled" : "&cDisabled") + "&f)")));
        sender.sendMessage("");
    }
}
