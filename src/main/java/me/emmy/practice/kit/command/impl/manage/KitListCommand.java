package me.emmy.practice.kit.command.impl.manage;

import me.emmy.practice.Practice;
import me.emmy.practice.api.command.BaseCommand;
import me.emmy.practice.api.command.Command;
import me.emmy.practice.api.command.CommandArgs;
import me.emmy.practice.kit.KitRepository;
import me.emmy.practice.util.CC;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Practice
 * @date 08/12/2024 - 10:44
 */
public class KitListCommand extends BaseCommand {
    @Command(name = "kit.list", permission = "practice.kit.list")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        KitRepository kitRepository = Practice.getInstance().getKitHandler().getRepository();

        player.sendMessage("");
        player.sendMessage(CC.translate("&b&lPractice Kits: &7(Total: &f" + kitRepository.getKits().size() + "&7)"));
        kitRepository.getKits().forEach(kit -> player.sendMessage(CC.translate(" &f▢ &b" + kit.getName() + " &7- &b" + kit.getKitType().getName() + " &7| " + (kit.isEnabled() ? "&aEnabled" : "&cDisabled"))));
        player.sendMessage("");
    }
}