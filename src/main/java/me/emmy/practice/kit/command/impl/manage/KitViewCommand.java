package me.emmy.practice.kit.command.impl.manage;

import me.emmy.practice.Practice;
import me.emmy.practice.api.command.BaseCommand;
import me.emmy.practice.api.command.Command;
import me.emmy.practice.api.command.CommandArgs;
import me.emmy.practice.kit.Kit;
import me.emmy.practice.kit.KitHandler;
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

        //String inventory = Arrays.stream(kit.getInventory()).map(item -> item.getType().name()).reduce((a, b) -> a + ", " + b).orElse("None");
        //String armor = Arrays.stream(kit.getArmor()).map(item -> item.getType().name()).reduce((a, b) -> a + ", " + b).orElse("None");

        Arrays.asList(
                "&b&lKit Information &7(&3" + kit.getKitType().name() + "&7)",
                " > &bName: &f" + kit.getName(),
                " > &bDisplay Name: &f" + kit.getDisplayName(),
                " > &bDisclaimer: &f" + kit.getDisclaimer(),
                " > &bEnabled: &f" + kit.isEnabled(),
                " > &bIcon: &f" + kit.getIcon().toString().toLowerCase(),
                " > &bIcon Durability: &f" + kit.getIconData()
                /*" > &bInventory:",
                "   - &f" + inventory,
                " > &bArmor:",
                "   - &f" + armor*/
        ).forEach(line -> sender.sendMessage(CC.translate(line)));
    }
}
