package me.emmy.practice.kit.command.impl.settings;

import me.emmy.practice.Practice;
import me.emmy.practice.api.command.BaseCommand;
import me.emmy.practice.api.command.CommandArgs;
import me.emmy.practice.api.command.annotation.Command;
import me.emmy.practice.util.CC;
import org.bukkit.entity.Player;

/**
 * @author Remi
 * @project Practice
 * @date 5/26/2024
 */
public class KitSettingsCommand extends BaseCommand {
    @Command(name = "kit.settings", permission = "practice.admin")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        player.sendMessage("");
        player.sendMessage(CC.translate("     &b&lKit Settings List &f(" + Practice.getInstance().getKitSettingRepository().getSettings().size() + "&f)"));
        if (Practice.getInstance().getKitSettingRepository().getSettings().isEmpty()) {
            player.sendMessage(CC.translate("      &f● &cNo Kit Settings available."));
        }
        Practice.getInstance().getKitSettingRepository().getSettings().forEach(setting -> player.sendMessage(CC.translate("      &f● &b" + setting.getName())));
        player.sendMessage("");
    }
}