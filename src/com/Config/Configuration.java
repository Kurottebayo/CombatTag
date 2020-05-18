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

    /**
     *
     * Config.yml configuration
     *
     */

    private static String prefix;
    private static int cooldown;
    private static boolean allowMobTagPlayers;
    private static String preventGamemodeTag;
    private static boolean preventFly;
    private static boolean disableFly;
    private static String cancelTeleport;
    private static String onlyTag;
    private static String punishment;
    private static String punishmentCommand;
    private static boolean broadcast;
    private static boolean enableBypass;
    private static String broadcastMessage;
    private static boolean worldGuard;
    private static List<String> whitelistedCommands;
    private static List<String> disabledWorlds;

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
        addNode("Allow Mob Tag Players", false, config);
        setComment("Allow Mob Tag Players", " ", config);
        addComment("Allow Mob Tag Players", " Allows mobs to activate the player's tag when one of the two is damaged by the other", config, AUTOINDENT);
        addComment("Allow Mob Tag Players", " ", config, AUTOINDENT);
        addNode("Prevent Gamemode Tag", "CREATIVE, SPECTATOR", config);
        setComment("Prevent Gamemode Tag", " ", config);
        addComment("Prevent Gamemode Tag", " Prevent a user in a given gamemode from triggering the tag for himself", config, AUTOINDENT);
        addComment("Prevent Gamemode Tag", "    - Nodes: CREATIVE, SURVIVAL, ADVENTURE, SPECTATOR, NULL(to disable this function)", config, AUTOINDENT);
        addComment("Prevent Gamemode Tag", " ", config, AUTOINDENT);
        addNode("Prevent Fly", true, config);
        setComment("Prevent Fly", " ", config);
        addComment("Prevent Fly", " Do not allow the player to fly during combat", config, AUTOINDENT);
        addComment("Prevent Fly", " ", config, AUTOINDENT);
        addNode("Disable Fly", false, config);
        setComment("Disable Fly", " ", config);
        addComment("Disable Fly", " Disable the fly during combat", config, AUTOINDENT);
        addComment("Disable Fly", " The difference between this one and the top one is that, in the top one, the fly will be automatically ", config, AUTOINDENT);
        addComment("Disable Fly", " activated at the end of the tag, whereas in this one, the player would have, for example, to use a command", config, AUTOINDENT);
        addComment("Disable Fly", " that would enable him to fly", config, AUTOINDENT);
        addComment("Disable Fly", " ", config, AUTOINDENT);
        addNode("Enable Bypass", true, config);
        setComment("Enable Bypass", " ", config);
        addComment("Enable Bypass", " Allow users with " + Permissions.BYPASS + " or " + Permissions.ALL + " permission to not trigger tag blocking", config, AUTOINDENT);
        addComment("Enable Bypass", " ", config, AUTOINDENT);
        addNode("Cancel Teleport", "PLUGIN, COMMAND", config);
        setComment("Cancel Teleport", " ", config);
        addComment("Cancel Teleport", " Cancel a specific teleport while in combat", config, AUTOINDENT);
        addComment("Cancel Teleport", "   - Nodes: ENDER_PEARL, PLUGIN, COMMAND, UNKNOWN", config, AUTOINDENT);
        addComment("Cancel Teleport", " ", config, AUTOINDENT);
        addNode("Only Tag", "BOTH", config);
        setComment("Only Tag", " ", config);
        addComment("Only Tag", " Just trigger the tag for ATTACKER, VICTIM or BOTH.", config, AUTOINDENT);
        addComment("Only Tag", " ", config, AUTOINDENT);
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
        addNode("World Guard", true, config);
        setComment("World Guard", " ", config);
        addComment("World Guard", " Enable World Guard support in the plugin (prevents players from trigger the tag, in a region without PVP)", config, AUTOINDENT);
        addComment("World Guard", " ", config, AUTOINDENT);
        List<String> commands = new Vector<>();
        commands.add("tell");
        commands.add("m");
        commands.add("r");
        commands.add("msg");
        commands.add("tag");
        commands.add("ct");
        addNode("Whitelisted Commands", commands, config);
        setComment("Whitelisted Commands", " ", config);
        addComment("Whitelisted Commands", " Commands enabled during combat", config, AUTOINDENT);
        addComment("Whitelisted Commands", " ", config, AUTOINDENT);
        List<String> disabledWorlds = new Vector<>();
        disabledWorlds.add("some world here");
        disabledWorlds.add("and another world here");
        addNode("Disabled Worlds", disabledWorlds, config);
        setComment("Disabled Worlds", " ", config);
        addComment("Disabled Worlds", " In which worlds to disable this plugin?", config, AUTOINDENT);
        addComment("Disabled Worlds", " ", config, AUTOINDENT);
        Bukkit.getConsoleSender().sendMessage("§9CombatTag> §aConfig.yml created!");
    }

    @SuppressWarnings("all")
    public static void loadConfiguration() {
        //Runnable run = () -> {
            Bukkit.getConsoleSender().sendMessage("§9CombatTag> §6Loading configuration...");
            if (!configurationExists()) createConfiguration();
            prefix = (String) getNode("Prefix", config);
            cooldown = Integer.parseInt((String) getNode("Cooldown", config));
            allowMobTagPlayers = Boolean.parseBoolean((String) getNode("Allow Mob Tag Players", config));
            preventGamemodeTag = ((String) getNode("Prevent Gamemode Tag", config)).toLowerCase();
            preventFly = Boolean.parseBoolean((String) getNode("Prevent Fly", config));
            disableFly = Boolean.parseBoolean((String) getNode("Disable Fly", config));
            cancelTeleport = ((String) getNode("Cancel Teleport", config)).toLowerCase();
            onlyTag = ((String) getNode("Only Tag", config)).toLowerCase();
            enableBypass = Boolean.parseBoolean((String) getNode("Enable Bypass", config));
            punishment = (String) getNode("Punishment Type", config);
            punishmentCommand = (String) getNode("Punishment Command", config);
            broadcast = Boolean.parseBoolean((String) getNode("Broadcast", config));
            broadcastMessage = (String) getNode("Broadcast Message", config);
            worldGuard = Boolean.parseBoolean((String) getNode("World Guard", config));
            whitelistedCommands = (List<String>) getNode("Whitelisted Commands", config);
            for (int i = 0; i < whitelistedCommands.size(); i++)
                whitelistedCommands.set(i, whitelistedCommands.get(i).toLowerCase());
            disabledWorlds = (List<String>) getNode("Disabled Worlds", config);
            for (int i = 0; i < disabledWorlds.size(); i++)
                disabledWorlds.set(i, disabledWorlds.get(i).toLowerCase());
            Bukkit.getConsoleSender().sendMessage("§9CombatTag> §aConfiguration loaded!");
        /*};
        new Thread(run).start();*/
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

    public static List<String> getDisabledWorlds() { return disabledWorlds; }

    public static boolean isAllowMobTagPlayers() { return allowMobTagPlayers; }

    public static String getPreventGamemodeTag() { return preventGamemodeTag; }

    public static boolean isPreventFly() { return preventFly; }

    public static boolean isDisableFly() { return disableFly; }

    public static String getOnlyTag() { return onlyTag; }

    public static boolean isWorldGuardEnabled() { return worldGuard; }

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
    private static String disabledWorld;
    private static String argumentNotFound;
    private static String flyingInCombat;
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
        addNode("Disabled World", "&cThe plugin is disabled in the current world.", messages);
        addNode("Argument Not Found", "&cThe specified argument could not be found!", messages);
        addNode("Flying in Combat", "&cYou cannot fly in combat!", messages);
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
        //Runnable run = () -> {
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
            disabledWorld = (String) getNode("Disabled World", messages);
            argumentNotFound = (String) getNode("Argument Not Found", messages);
            flyingInCombat = (String) getNode("Flying in Combat", messages);
            help = (List<String>) getNode("Help", messages);
            Bukkit.getConsoleSender().sendMessage("§9CombatTag> §aMessages loaded!");
        //};
        //new Thread(run).start();
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

    public static String getDisabledWorldMessage() { return disabledWorld; }

    public static String getArgumentNotFound() { return argumentNotFound; }

    public static String getFlyingInCombat() { return flyingInCombat; }
}
