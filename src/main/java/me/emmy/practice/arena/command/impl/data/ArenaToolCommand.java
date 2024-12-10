package me.emmy.practice.arena.command.impl.data;

import me.emmy.practice.arena.selection.ArenaSelection;
import me.emmy.practice.util.CC;
import me.emmy.practice.api.command.BaseCommand;
import me.emmy.practice.api.command.Command;
import me.emmy.practice.api.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author Remi
 * @project Practice
 * @date 5/20/2024
 */
public class ArenaToolCommand extends BaseCommand {

    @Command(name = "arena.tool", aliases = "arena.wand", permission = "practice.admin")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        if (player.getInventory().first(ArenaSelection.SELECTION_TOOL) != -1) {
            player.getInventory().remove(ArenaSelection.SELECTION_TOOL);
            player.sendMessage(CC.translate("&cSelection tool has been removed."));
            player.updateInventory();
            return;
        }

        player.getInventory().addItem(ArenaSelection.SELECTION_TOOL);
        player.sendMessage(CC.translate("&aSelection tool has been added to your inventory."));
        player.updateInventory();
    }
}
