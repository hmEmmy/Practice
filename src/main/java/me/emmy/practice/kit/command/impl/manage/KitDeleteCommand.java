package me.emmy.practice.kit.command.impl.manage;

import me.emmy.practice.Practice;
import me.emmy.practice.api.command.BaseCommand;
import me.emmy.practice.api.command.annotation.Command;
import me.emmy.practice.api.command.CommandArgs;
import me.emmy.practice.kit.Kit;
import me.emmy.practice.kit.KitRepository;
import me.emmy.practice.util.CC;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Practice
 * @date 08/12/2024 - 09:46
 */
public class KitDeleteCommand extends BaseCommand {
    @Command(name = "kit.delete", permission = "practice.kit.delete")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&6Usage: &e/kit delete &b<kitName>"));
            return;
        }

        KitRepository kitRepository = Practice.getInstance().getKitHandler().getRepository();
        Kit kit = kitRepository.getKit(args[0]);
        if (kit == null) {
            player.sendMessage(CC.translate("&cA kit with that name does not exist."));
            return;
        }

        kitRepository.removeKit(kit);
        player.sendMessage(CC.translate("&aSuccessfully deleted the kit &b" + kit.getName() + "&a."));
    }
}