package com.Config;

import com.Constants.Permissions;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Vector;

import static kuro.Yaml.*;
import static com.Constants.Paths.*;

public class Configuration {

    public static void main(String[] args){
        loadConfiguration();
    }

    /**
     *
     * Config.yml configuration
     *
     */

    private static String prefix;
    private static int cooldown;
    private static String cancelTeleport;
    private static String punishment;
    private static String punishmentCommand;
    private static boolean broadcast;
    private static boolean enableBypass;
    private static String broadcastMessage;
    private static List<String> whitelistedCommands;

    public static void createConfiguration() {
        Bukkit.getConsoleSender().sendMessage("§9CombatTag> §6Creating Config.yml...");
        try {
            Files.deleteIfExists(Paths.get(config));
            Files.createDirectories(Paths.get(config.substring(0, config.lastIndexOf('\\'))));
            Files.createFile(Paths.get(config));
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> header = new Vector<>();
        header.add("############################################################################");
        header.add("                                                                          #");
        header.add("                           Plugin Configuration                           #");
        header.add("                                By: Kuro                                  #");
        header.add("                                                                          #");
        header.add("############################################################################");
        header.add("## Variables: ");
        header.add("##    - <player>: Return a player's name, can be null if the event is non-related to a player");
        header.add("##    - <tag-cooldown>: Return the current time of the user tag related to the event. Can be between -1 and the defined tag number in config.yml");
        header.add(" ");
        setHeader(header, config);
        addNode("Prefix", "&9CombatTag > ", config);
        setComment("Prefix", " ", config);
        addComment("Prefix", " Plugin Prefix", config, AUTOINDENT);
        addComment("Prefix", " ", config, AUTOINDENT);
        addNode("Cooldown", 15, config);
        setComment("Cooldown", " ", config);
        addComment("Cooldown", " The required cooldown for the player to log out of the server/execute commands/teleport", config, AUTOINDENT);
        addComment("Cooldown", " ", config, AUTOINDENT);
        addNode("Enable Bypass", true, config);
        setComment("Enable Bypass", " ", config);
        addComment("Enable Bypass", " Allow users with " + Permissions.BYPASS + " or " + Permissions.ALL + " permission to not trigger tag blocking", config, AUTOINDENT);
        addComment("Enable Bypass", " ", config, AUTOINDENT);
        addNode("Cancel Teleport", "PLUGIN, COMMAND", config);
        setComment("Cancel Teleport", " ", config);
        addComment("Cancel Teleport", " Cancel a specific teleport while in combat", config, AUTOINDENT);
        addComment("Cancel Teleport", "   - Nodes: ENDER_PEARL, PLUGIN, COMMAND, UNKNOWN", config, AUTOINDENT);
        addComment("Cancel Teleport", " ", config, AUTOINDENT);
        addNode("Punishment Type", "Kill", config);
        setComment("Punishment Type", " ", config);
        addComment("Punishment Type", " Choose how the user will be punished if he goes out in combat. Kill and Clear are the performable actions", config, AUTOINDENT);
        addComment("Punishment Type", " ", config, AUTOINDENT);
        addNode("Punishment Command", "null", config);
        setComment("Punishment Command", " ", config);
        addComment("Punishment Command", " Choose a command to trigger if a player logs out of combat. Type null to inform that you do not want any commands to be executed", config, AUTOINDENT);
        addComment("Punishment Command", " ", config, AUTOINDENT);
        addNode("Broadcast", true, config);
        setComment("Broadcast", " ", config);
        addComment("Broadcast", " Broadcast to the entire server that a player logs out of in combat?", config, AUTOINDENT);
        addComment("Broadcast", " ", config, AUTOINDENT);
        addNode("Broadcast Message", "&4<player> &chas disconnected in combat.", config);
        setComment("Broadcast Message", " ", config);
        addComment("Broadcast Message", " Message to be broadcast if a player logs out of combat, and if the \"Broadcast\" option is true", config, AUTOINDENT);
        addComment("Broadcast Message", " ", config, AUTOINDENT);
        List<String> commands = new Vector<>();
        commands.add("tell");
        commands.add("m");
        commands.add("r");
        commands.add("msg");
        commands.add("tag");
        addNode("Whitelist Commands", commands, config);
        setComment("Whitelist Commands", " ", config);
        addComment("Whitelist Commands", " Commands enabled during combat", config, AUTOINDENT);
        addComment("Whitelist Commands", " ", config, AUTOINDENT);
        Bukkit.getConsoleSender().sendMessage("§9CombatTag> §aConfig.yml created!");
    }

    @SuppressWarnings("all")
    public static void loadConfiguration() {
        Runnable run = () -> {
            Bukkit.getConsoleSender().sendMessage("§9CombatTag> §6Loading configuration...");
            if (!configurationExists()) createConfiguration();
            prefix = (String) getNode("Prefix", config);
            cooldown = Integer.parseInt((String) getNode("Cooldown", config));
            cancelTeleport = ((String) getNode("Cancel Teleport", config)).toLowerCase();
            enableBypass = Boolean.parseBoolean((String) getNode("Enable Bypass", config));
            punishment = (String) getNode("Punishment Type", config);
            punishmentCommand = (String) getNode("Punishment Command", config);
            broadcast = Boolean.parseBoolean((String) getNode("Broadcast", config));
            broadcastMessage = (String) getNode("Broadcast Message", config);
            whitelistedCommands = (List<String>) getNode("Whitelist Commands", config);
            for (int i = 0; i < whitelistedCommands.size(); i++)
                whitelistedCommands.set(i, whitelistedCommands.get(i).toLowerCase());
            Bukkit.getConsoleSender().sendMessage("§9CombatTag> §aConfiguration loaded!");
        };
        new Thread(run).start();
    }

    private static boolean configurationExists(){ return Files.exists(Paths.get(config)); }

    public static String getPrefix() { return prefix; }

    public static int getCooldown() { return cooldown; }

    public static String getCancelTeleport() { return cancelTeleport; }

    public static boolean getBypass() { return enableBypass; }

    public static String getPunishment() { return punishment; }

    public static String getPunishmentCommand() { if(punishmentCommand.startsWith("/")) { return punishmentCommand; } else return "/"+punishmentCommand; }

    public static boolean isBroadcast() { return broadcast; }

    public static String getBroadcastMessage() { return broadcastMessage; }

    public static List<String> getWhitelistedCommands() { return whitelistedCommands; }

    /**
     *
     * Messages.yml configuration
     *
     */

    private static String tag;
    private static String tagOut;
    private static String tagCommandInCombat;
    private static String tagCommandOutCombat;
    private static String cantUseCommandsInCombat;
    private static String cantTeleportInCombat;
    private static String joinAfterPunishment;
    private static String log;
    private static String reloadedConfig;
    private static String reloadedMessages;
    private static String missingPermissions;
    private static String onlyPlayer;
    private static List<String> help;

    public static void createMessages() {
        Bukkit.getConsoleSender().sendMessage("§9CombatTag> §6Creating Messages.yml...");
        try {
            Files.deleteIfExists(Paths.get(messages));
            Files.createDirectories(Paths.get(messages.substring(0, messages.lastIndexOf('\\'))));
            Files.createFile(Paths.get(messages));
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> header = new Vector<>();
        header.add("############################################################################");
        header.add("                                                                          #");
        header.add("                             Plugin Messages                              #");
        header.add("                                By: Kuro                                  #");
        header.add("                                                                          #");
        header.add("############################################################################");
        header.add("## Variables: ");
        header.add("##    - <player>: Return a player's name, can be null if the event is non-related to a player");
        header.add("##    - <tag-cooldown>: Return the current time of the user tag related to the event. Can be between -1 and the defined tag number in config.yml");
        setHeader(header, messages);
        addNode("Tag", "&7You are now in combat for &c<tag-cooldown> seconds&7!", messages);
        addNode("Tag Out", "&aYou went out of combat, you can execute commands or leave the server safely!", messages);
        addNode("Tag Command in Combat", "&7You still have &c<tag-cooldown> seconds &7to go out of combat!", messages);
        addNode("Tag Command out Combat", "&aYou are not in combat!", messages);
        addNode("Cant Teleport in Combat", "&cYou cannot teleport in combat!", messages);
        addNode("Cant Use Commands in Combat", "&cYou cannot use this command while in combat!", messages);
        addNode("Join After Punishment", "&cOnce you went into combat earlier, the appropriate punishments were applied!", messages);
        addNode("Log", "&8[LOG] &7The player &8<player> &7has disconnected in combat!", messages);
        addNode("Reloaded Config", "&aConfiguration reloaded!", messages);
        addNode("Reloaded Messages", "&aMessages reloaded!", messages);
        addNode("Missing Permissions", "&cYou do not have permissions to use this command.", messages);
        addNode("Only Player", "&cOnly players can execute this command.", messages);
        List<String> helpMessage = new Vector<>();
        helpMessage.add("&6&m---------------------------&r &eCombat Tag &6&m---------------------------&r");
        helpMessage.add("  &eAliases &a/ct");
        helpMessage.add("   &a/combattag reload [<config|messages>] &f- &7Reloads the configuration, messages or one of two");
        helpMessage.add("   &a/combattag &f- &7Show this message");
        helpMessage.add("&6&m----------------------------------------------------------------&r");
        addNode("Help", helpMessage, messages);
        Bukkit.getConsoleSender().sendMessage("§9CombatTag> §aMessages.yml created!");
    }

    @SuppressWarnings("all")
    public static void loadMessages() {
        Runnable run = () -> {
            Bukkit.getConsoleSender().sendMessage("§9CombatTag> §6Loading messages...");
            if (!messagesExists()) createMessages();
            tag = (String) getNode("Tag", messages);
            tagOut = (String) getNode("Tag Out", messages);
            tagCommandInCombat = (String) getNode("Tag Command in Combat", messages);
            tagCommandOutCombat = (String) getNode("Tag Command out Combat", messages);
            cantUseCommandsInCombat = (String) getNode("Cant Use Commands in Combat", messages);
            cantTeleportInCombat = (String) getNode("Cant Teleport in Combat", messages);
            joinAfterPunishment = (String) getNode("Join After Punishment", messages);
            log = (String) getNode("Log", messages);
            reloadedConfig = (String) getNode("Reloaded Config", messages);
            reloadedMessages = (String) getNode("Reloaded Messages", messages);
            missingPermissions = (String) getNode("Missing Permissions", messages);
            onlyPlayer = (String) getNode("Only Player", messages);
            help = (List<String>) getNode("Help", messages);
            Bukkit.getConsoleSender().sendMessage("§9CombatTag> §aMessages loaded!");
        };
        new Thread(run).start();
    }

    private static boolean messagesExists(){ return Files.exists(Paths.get(messages)); }

    public static String getTagMessage() { return tag; }

    public static String getTagOutMessage() { return tagOut; }

    public static String getLogMessage() { return log; }

    public static String getJoinMessageAfterPunishment() { return joinAfterPunishment; }

    public static String getCantUseCommandsInCombat() { return cantUseCommandsInCombat; }

    public static String getCantTeleportInCombat() { return cantTeleportInCombat; }

    public static String getTagCommandInCombatMessage() { return tagCommandInCombat; }

    public static String getTagCommandOutCombatMessage() { return tagCommandOutCombat; }

    public static List<String> getHelpMessage() { return help; }

    public static String getReloadedConfigMessage() { return reloadedConfig; }

    public static String getReloadedMessagesMessage() { return reloadedMessages; }

    public static String getMissingPermissionsMessage() { return missingPermissions; }

    public static String getOnlyPlayerMessage() { return onlyPlayer; }

}
