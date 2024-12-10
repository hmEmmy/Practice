package me.emmy.practice.kit.command.impl.data;

import me.emmy.practice.Practice;
import me.emmy.practice.api.command.BaseCommand;
import me.emmy.practice.api.command.Command;
import me.emmy.practice.api.command.CommandArgs;
import me.emmy.practice.kit.Kit;
import me.emmy.practice.kit.KitHandler;
import me.emmy.practice.util.CC;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Practice
 * @date 08/12/2024 - 10:38
 */
public class KitDescriptionCommand extends BaseCommand {
    @Command(name = "kit.description", permission = "practice.kit.description")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&6Usage: &e/kit description &b<kitName> <description>"));
            return;
        }

        KitHandler kitHandler = Practice.getInstance().getKitHandler();
        Kit kit = kitHandler.getRepository().getKit(args[0]);
        if (kit == null) {
            player.sendMessage(CC.translate("&cA kit with that name does not exist."));
            return;
        }

        String description = String.join(" ", args);
        kit.setDescription(description);
        kitHandler.saveKit(kit);
        player.sendMessage(CC.translate("&aSuccessfully set the description for the &b" + kit.getName() + "&a kit to &b:\n &r" + description));
    }
}