package me.emmy.practice.kit.command.impl;

import me.emmy.practice.Practice;
import me.emmy.practice.api.command.BaseCommand;
import me.emmy.practice.api.command.Command;
import me.emmy.practice.api.command.CommandArgs;
import me.emmy.practice.kit.KitHandler;
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
        KitRepository kitRepository = Practice.getInstance().getKitRepository();

        player.sendMessage("");
        player.sendMessage(CC.translate("&bKits:"));
        kitRepository.getKits().forEach(kit -> player.sendMessage(CC.translate("&b" + kit.getName() + " &7- &b" + kit.getKitType().name() + "&7(" + (kit.isEnabled() ? "&a&lEnabled" : "&c&lDisabled")) + "&7)"));
        player.sendMessage(CC.translate("&bTotal Kits: &7" + kitRepository.getKits().size()));
        player.sendMessage("");
    }
}
