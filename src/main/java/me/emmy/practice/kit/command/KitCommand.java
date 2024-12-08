package me.emmy.practice.kit.command;

import me.emmy.practice.api.command.BaseCommand;
import me.emmy.practice.api.command.Command;
import me.emmy.practice.api.command.CommandArgs;
import me.emmy.practice.util.CC;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

/**
 * @author Emmy
 * @project Practice
 * @date 07/12/2024 - 20:05
 */
public class KitCommand extends BaseCommand {
    @Command(name = "kit", permission = "practice.owner")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        List<String> message = Arrays.asList(
                "&6&lKit Commands",
                "&e/kit create &b<name> &7- Create a new kit",
                "&e/kit delete &b<name> &7- Delete a kit",
                "&e/kit enable &b<name> &7- Enable a kit",
                "&e/kit disable &b<name> &7- Disable a kit",
                "&e/kit getinv &b<name> &7- Get the inventory of a kit",
                "&e/kit setinv &b<name> &7- Set the inventory of a kit",
                "&e/kit icon &b<name> &7- Set the icon of a kit",
                "&e/kit description &b<name> &7- Set the description of a kit",
                "&e/kit disclaimer &b<name> &7- Set the disclaimer of a kit",
                "&e/kit type &b<name> &7- Set the type of a kit",
                "&e/kit list &7- List all kits"
        );

        message.forEach(msg -> player.sendMessage(CC.translate(msg)));
    }
}