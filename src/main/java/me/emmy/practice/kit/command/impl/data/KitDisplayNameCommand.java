package me.emmy.practice.kit.command.impl.data;

import me.emmy.practice.Practice;
import me.emmy.practice.api.command.BaseCommand;
import me.emmy.practice.api.command.Command;
import me.emmy.practice.api.command.CommandArgs;
import me.emmy.practice.kit.Kit;
import me.emmy.practice.kit.KitHandler;
import me.emmy.practice.util.CC;
import org.bukkit.entity.Player;

import java.util.Arrays;

/**
 * @author Emmy
 * @project Practice
 * @date 10/12/2024 - 22:54
 */
public class KitDisplayNameCommand extends BaseCommand {
    @Command(name = "kit.displayname", permission = "practice.kit.setdisplayname")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 2) {
            player.sendMessage(CC.translate("&6Usage: &e/kit setdisplayname &b<kitName> <displayName>"));
            return;
        }

        KitHandler kitHandler = Practice.getInstance().getKitHandler();
        Kit kit = kitHandler.getKitRepository().getKit(args[0]);
        if (kit == null) {
            player.sendMessage(CC.translate("&cA kit with that name does not exist."));
            return;
        }

        String displayName = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        kit.setDisplayName(displayName);
        kitHandler.saveKit(kit);

        player.sendMessage(CC.translate("&aSuccessfully set the display name for the &b" + kit.getName() + "&a kit to &b" + displayName + "&a."));
    }
}