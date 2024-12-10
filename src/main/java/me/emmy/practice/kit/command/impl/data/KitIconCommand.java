package me.emmy.practice.kit.command.impl.data;

import me.emmy.practice.Practice;
import me.emmy.practice.api.command.BaseCommand;
import me.emmy.practice.api.command.Command;
import me.emmy.practice.api.command.CommandArgs;
import me.emmy.practice.kit.Kit;
import me.emmy.practice.kit.KitHandler;
import me.emmy.practice.util.CC;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Practice
 * @date 08/12/2024 - 10:35
 */
public class KitIconCommand extends BaseCommand {
    @Command(name = "kit.icon", permission = "practice.kit.icon")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&6Usage: &e/kit icon &b<kitName>"));
            return;
        }

        KitHandler kitHandler = Practice.getInstance().getKitHandler();
        Kit kit = kitHandler.getRepository().getKit(args[0]);
        if (kit == null) {
            player.sendMessage(CC.translate("&cA kit with that name does not exist."));
            return;
        }

        if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
            player.sendMessage(CC.translate("&cYou must be holding an item to set it as the icon."));
            return;
        }

        Material material = player.getItemInHand().getType();

        kit.setIcon(material);
        kit.setIconData(player.getItemInHand().getDurability());
        kitHandler.saveKit(kit);

        player.sendMessage(CC.translate("&aSuccessfully set the icon for the kit &b" + kit.getName() + "&a."));
    }
}
