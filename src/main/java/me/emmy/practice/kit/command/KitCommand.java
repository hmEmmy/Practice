package me.emmy.practice.kit.command;

import me.emmy.practice.api.command.BaseCommand;
import me.emmy.practice.api.command.annotation.Command;
import me.emmy.practice.api.command.CommandArgs;
import me.emmy.practice.util.CC;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

/**
 * @author Emmy
 * @project Practice
 * @date 07/12/2024 - 20:05
 */
public class KitCommand extends BaseCommand {
    @Command(name = "kit", permission = "practice.owner", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();

        Arrays.asList(
                "&b&lKit Commands",
                "&e/kit create &b<name> &6- Create a new kit",
                "&e/kit delete &b<name> &6- Delete a kit",
                "&e/kit enable &b<name> &6- Enable a kit",
                "&e/kit disable &b<name> &6- Disable a kit",
                "&e/kit getinv &b<name> &6- Get the inventory of a kit",
                "&e/kit setinv &b<name> &6- Set the inventory of a kit",
                "&e/kit icon &b<name> &6- Set the icon of a kit",
                "&e/kit disclaimer &b<name> &6- Set the disclaimer of a kit",
                "&e/kit description &b<name> &6- Set the description of a kit",
                "&e/kit displayname &b<name> &6- Set the display name of a kit",
                "&e/kit type &b<name> &6- Set the type of a kit",
                "&e/kit list &6- List all kits"
        ).forEach(line -> sender.sendMessage(CC.translate(line)));
    }
}