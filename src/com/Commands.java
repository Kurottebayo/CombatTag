package com;

import com.Config.Configuration;
import com.Constants.Permissions;
import com.Others.Data;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.Config.Configuration.getDisabledWorlds;
import static com.Others.Functions.replaceColors;

public class Commands implements CommandExecutor {
    @Override
    @SuppressWarnings("all")
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] arguments) {
        // Tag Command
        if (label.equalsIgnoreCase("tag")) {
            if(!(commandSender instanceof Player)){
                String message = replaceColors(Configuration.getPrefix() + Configuration.getOnlyPlayerMessage());
                message = message.replace("<player>", "null");
                message = message.replace("<tag-cooldown>", "null");
                commandSender.sendMessage(message);
                return false;
            }
            Player player = (Player) commandSender;
            if(getDisabledWorlds().contains(player.getWorld().getName().toLowerCase())){
                String message = replaceColors(Configuration.getPrefix() + Configuration.getDisabledWorldMessage());
                message = message.replace("<player>", "null");
                message = message.replace("<tag-cooldown>", "null");
                player.sendMessage(message);
                return false;
            }
            String message;
            if (Data.getPlayerTag(player) > -1) {
                message = replaceColors(Configuration.getPrefix() + Configuration.getTagCommandInCombatMessage());
            } else message = replaceColors(Configuration.getPrefix() + Configuration.getTagCommandOutCombatMessage());
            ;
            message = message.replace("<player>", player.getName());
            message = message.replace("<tag-cooldown>", String.valueOf(Data.getPlayerTag(player)));
            player.sendMessage(message);

        } else if ((label.equalsIgnoreCase("ct") || label.equalsIgnoreCase("combattag"))) {
            if (!commandSender.hasPermission(Permissions.ALL)) {
                String message = replaceColors(Configuration.getPrefix() + Configuration.getMissingPermissionsMessage());
                message = message.replace("<player>", "null");
                message = message.replace("<tag-cooldown>", "null");
                commandSender.sendMessage(message);
                return false;
            }
            // Help Command
            if (arguments == null || arguments.length == 0) {
                for (String s : Configuration.getHelpMessage()) commandSender.sendMessage(replaceColors(s));
            } else {
                // Reload Command /ct reload
                if (arguments[0].equalsIgnoreCase("reload") || arguments[0].equalsIgnoreCase("rl")) {
                    if (arguments.length > 1 && (arguments[1].equalsIgnoreCase("messages") || arguments[1].equalsIgnoreCase("config"))) {
                        if (arguments[1].equalsIgnoreCase("messages")) {
                            Configuration.loadMessages();
                            String message = replaceColors(Configuration.getPrefix() + Configuration.getReloadedMessagesMessage());
                            message = message.replace("<player>", "null");
                            message = message.replace("<tag-cooldown>", "null");
                            commandSender.sendMessage(message);
                        }
                        if (arguments[1].equalsIgnoreCase("config")) {
                            Configuration.loadConfiguration();
                            String message = replaceColors(Configuration.getPrefix() + Configuration.getReloadedConfigMessage());
                            message = message.replace("<player>", "null");
                            message = message.replace("<tag-cooldown>", "null");
                            commandSender.sendMessage(message);
                        }
                    } else {
                        Configuration.loadConfiguration();
                        String message = replaceColors(Configuration.getPrefix() + Configuration.getReloadedConfigMessage());
                        message = message.replace("<player>", "null");
                        message = message.replace("<tag-cooldown>", "null");
                        commandSender.sendMessage(message);
                        Configuration.loadMessages();
                        message = replaceColors(Configuration.getPrefix() + Configuration.getReloadedMessagesMessage());
                        message = message.replace("<player>", "null");
                        message = message.replace("<tag-cooldown>", "null");
                        commandSender.sendMessage(message);
                    }
                } else if (arguments[0].equalsIgnoreCase("help")) {
                    for (String s : Configuration.getHelpMessage()) commandSender.sendMessage(replaceColors(s));
                } else {
                    String message = replaceColors(Configuration.getPrefix() + Configuration.getArgumentNotFound());
                    message = message.replace("<player>", "null");
                    message = message.replace("<tag-cooldown>", "null");
                    commandSender.sendMessage(message);
                }
            }
        }
        return false;
    }
}
