package me.emmy.practice.kit.command.impl.settings;

import me.emmy.practice.Practice;
import me.emmy.practice.api.command.annotation.Command;
import me.emmy.practice.kit.Kit;
import me.emmy.practice.util.CC;
import me.emmy.practice.api.command.BaseCommand;
import me.emmy.practice.api.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author Remi
 * @project Practice
 * @date 5/21/2024
 */
public class KitSetSettingCommand extends BaseCommand {
    @Command(name = "kit.setsetting", aliases = {"kit.setting"}, permission = "practice.admin")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length != 3) {
            player.sendMessage(CC.translate("&6Usage: &e/kit setsetting &b<kit> <setting> <true/false>"));
            return;
        }

        Kit kit = Practice.getInstance().getKitHandler().getRepository().getKit(args[0]);
        if (kit == null) {
            player.sendMessage(CC.translate("&cA kit with that name does not exist."));
            return;
        }

        String settingName = args[1];
        boolean enabled = Boolean.parseBoolean(args[2]);

        if (Practice.getInstance().getKitSettingRepository().getSettings().stream().filter(setting -> setting.getName().equalsIgnoreCase(settingName)).findFirst().orElse(null) == null) {
            player.sendMessage(CC.translate("&cA setting with that name does not exist."));
            return;
        }

        kit.getKitSettings().stream().filter(setting -> setting.getName().equalsIgnoreCase(settingName)).findFirst().ifPresent(setting -> setting.setEnabled(enabled));
        Practice.getInstance().getKitHandler().saveKit(kit);
        player.sendMessage(CC.translate("&aSuccessfully set the setting &b" + settingName + " &ato &b" + enabled + " &afor the kit &b" + kit.getName() + "&a."));
    }
}