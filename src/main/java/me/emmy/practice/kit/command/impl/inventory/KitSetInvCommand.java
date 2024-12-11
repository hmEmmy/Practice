package me.emmy.practice.kit.command.impl.inventory;

import me.emmy.practice.Practice;
import me.emmy.practice.api.command.BaseCommand;
import me.emmy.practice.api.command.annotation.Command;
import me.emmy.practice.api.command.CommandArgs;
import me.emmy.practice.kit.Kit;
import me.emmy.practice.kit.KitHandler;
import me.emmy.practice.util.CC;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Practice
 * @date 08/12/2024 - 09:54
 */
public class KitSetInvCommand extends BaseCommand {
    @Command(name = "kit.setinv", permission = "practice.kit.setinv")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&6Usage: &e/kit setinv &b<kitName>"));
            return;
        }

        KitHandler kitHandler = Practice.getInstance().getKitHandler();
        Kit kit = kitHandler.getRepository().getKit(args[0]);
        if (kit == null) {
            player.sendMessage(CC.translate("&cA kit with that name does not exist."));
            return;
        }

        if (player.getGameMode() == GameMode.CREATIVE) {
            player.sendMessage(CC.translate("&cYou cannot set the inventory of a kit while in creative mode."));
            return;
        }

        kit.setInventory(player.getInventory().getContents());
        kit.setArmor(player.getInventory().getArmorContents());
        kitHandler.saveKit(kit);

        player.sendMessage(CC.translate("&aSuccessfully set the inventory for the kit &b" + kit.getName() + "&a."));
    }
}
