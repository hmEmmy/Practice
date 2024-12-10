package me.emmy.practice.kit.command.impl.data;

import me.emmy.practice.Practice;
import me.emmy.practice.api.command.BaseCommand;
import me.emmy.practice.api.command.Command;
import me.emmy.practice.api.command.CommandArgs;
import me.emmy.practice.kit.Kit;
import me.emmy.practice.kit.KitHandler;
import me.emmy.practice.kit.enums.EnumKitType;
import me.emmy.practice.util.CC;
import org.bukkit.entity.Player;

/**
 * @author Emmy
 * @project Practice
 * @date 08/12/2024 - 10:38
 */
public class KitTypeCommand extends BaseCommand {
    @Command(name = "kit.type", permission = "practice.kit.type")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            player.sendMessage(CC.translate("&6Usage: &e/kit type &b<kitName> <type>"));
            return;
        }

        KitHandler kitHandler = Practice.getInstance().getKitHandler();
        Kit kit = kitHandler.getRepository().getKit(args[0]);
        if (kit == null) {
            player.sendMessage(CC.translate("&cA kit with that name does not exist."));
            return;
        }

        EnumKitType kitType;
        try {
            kitType = EnumKitType.valueOf(args[1].toUpperCase());
        } catch (IllegalArgumentException e) {
            player.sendMessage(CC.translate("&cInvalid kit type. Available types: &b" + EnumKitType.getTypes()));
            return;
        }

        kit.setKitType(kitType);
        kitHandler.saveKit(kit);
        player.sendMessage(CC.translate("&aSuccessfully set the type for the &b" + kit.getName() + "&a kit to &b" + kitType.name() + "&a."));
    }
}