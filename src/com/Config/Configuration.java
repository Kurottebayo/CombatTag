package com.Config;

import com.Constants.Permissions;
import com.main;
import kuro.TextFiles;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Vector;

import static kuro.Parse.tryParseBoolean;
import static kuro.Parse.tryParseInt;
import static kuro.Yaml.*;

public class Configuration {

    private static final String config = "plugins\\CombatTag\\Config.yml";
    private static final String messages = "plugins\\CombatTag\\Messages.yml";

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
        header.add("## Variables: ");
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
        addNode("Broadcast Message", "&cO jogador &4<player> &cdisconectou-se em combate!", config);
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

    public static void loadConfiguration() {
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
        for(int i = 0; i < whitelistedCommands.size(); i++) whitelistedCommands.set(i,whitelistedCommands.get(i).toLowerCase());
        Bukkit.getConsoleSender().sendMessage("§9CombatTag> §aConfiguration loaded!");
    }
    private static boolean configurationExists(){ return Files.exists(Paths.get(config)); }

    public static boolean isValidConfiguration(){
        if(!nodeExists("Prefix",config) || !nodeHasValue("Prefix",config)) return false;
        if(!nodeExists("Cooldown",config) || !nodeHasValue("Cooldown",config) || !tryParseInt((String) getNode("Cooldown",config))) return false;
        if(!nodeExists("Punishment Type",config) || !nodeHasValue("Punishment Type",config)) return false;
        if(!nodeExists("Punishment Command",config) || !nodeHasValue("Punishment Command",config)) return false;
        if(!nodeExists("Broadcast",config) || !nodeHasValue("Broadcast",config) || !tryParseBoolean((String) getNode("Broadcast",config))) return false;
        if(!nodeExists("Broadcast Message",config) || !nodeHasValue("Broadcast Message",config)) return false;
        if(!nodeExists("Whitelist Commands",config)) return false;
        return true;
    }

    private static void setConfigValue(String node, Object newValue){ setNodeValue(node,newValue,config); }

    public static String getPrefix() { return prefix; }

    public static void setPrefix(String prefix) { Configuration.prefix = prefix; setConfigValue("Prefix",prefix); }

    public static int getCooldown() { return cooldown; }

    public static void setCooldown(int cooldown) { Configuration.cooldown = cooldown; setConfigValue("Cooldown",cooldown); }

    public static String getCancelTeleport() { return cancelTeleport; }

    public static void setCancelTeleport(String events) { Configuration.cancelTeleport = events; setConfigValue("Cancel Teleport",events); }

    public static void setBypass(boolean value) { enableBypass = value; setConfigValue("Enable Bypass",value); }

    public static boolean getBypass() { return enableBypass; }

    public static String getPunishment() { return punishment; }

    public static void setPunishment(String punishment) { if(punishment.equalsIgnoreCase("kill") || punishment.equalsIgnoreCase("clear")) { Configuration.punishment = punishment; setConfigValue("Punishment Type", punishment); } }

    public static String getPunishmentCommand() { if(punishmentCommand.startsWith("/")) { return punishmentCommand; } else return "/"+punishmentCommand; }

    public static void setPusnishmentCommand(String command) { punishmentCommand = command; setConfigValue("Punishment Commands",command); }

    public static boolean isBroadcast() { return broadcast; }

    public static void setBroadcast(boolean value) { broadcast = value; setConfigValue("Broadcast",value); }

    public static String getBroadcastMessage() { return broadcastMessage; }

    public static void setBroadcastMessage(String message) { broadcastMessage = message; setConfigValue("Broadcast Message",message); }

    public static List<String> getWhitelistedCommands() { return whitelistedCommands; }

    public static void setWhitelistedCommands(List<String> commands) { whitelistedCommands = commands; setConfigValue("Whitelist Commands",commands); }


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

    public static void createMessages(){
        Bukkit.getConsoleSender().sendMessage("§9CombatTag> §6Creating Messages.yml...");
        try {
            Files.deleteIfExists(Paths.get(messages));
            Files.createDirectories(Paths.get(messages.substring(0,messages.lastIndexOf('\\'))));
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
        setHeader(header,messages);

        addNode("Tag", "&7Estás agora em combate durante &c<tag-cooldown> segundos&7!",messages);
        addNode("Tag Out", "&aSaiste de combate, podes executar comandos ou sair do servidor em segurança!",messages);
        addNode("Tag Command in Combat", "&7Faltam-te &c<tag-cooldown> segundos &7para saires de combate!",messages);
        addNode("Tag Command out Combat", "&aNão estás em combate!",messages);
        addNode("Cant Teleport in Combat", "&cNão podes teleportar em combate!",messages);
        addNode("Cant Use Commands in Combat", "&cNão podes usar este comando enquanto estiveres em combate!",messages);
        addNode("Join After Punishment", "&cUma vez que saíste em combate anteriormente, as devidas punições foram aplicadas!",messages);
        addNode("Log", "&8[LOG] &7O jogador &8<player> &7deslogou em combate!",messages);
        addNode("Reloaded Config", "&aConfiguração recarregada!",messages);
        addNode("Reloaded Messages", "&aMensagens recarregadas!",messages);
        addNode("Missing Permissions", "&cNão tens permissões suficientes para executar este comando!",messages);
        addNode("Only Player", "&cApenas jogadores podem executar este comando!",messages);
        List<String> helpMessage = new Vector<>();
        helpMessage.add("&6&m---------------------------&r &eCombat Tag &6&m---------------------------&r");
        helpMessage.add("  &eAliases &a/ct");
        helpMessage.add("   &a/combattag reload [<config|messages>] &f- &7Recarrega a configuração, mensagens ou um dos dois");
        helpMessage.add("   &a/combattag &f- &7Mostra esta mensagem");
        helpMessage.add("&6&m----------------------------------------------------------------&r");
        addNode("Help", helpMessage,messages);
        Bukkit.getConsoleSender().sendMessage("§9CombatTag> §aMessages.yml created!");
    }

    public static void loadMessages() {
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
    }

    private static boolean messagesExists(){ return Files.exists(Paths.get(messages)); }

    @Deprecated
    public static boolean isValidMessages(){ return true; }

    private static void setMessageValue(String node, Object newValue){ setNodeValue(node,newValue,messages); }

    public static String getTagMessage() { return tag; }

    public static void setTagMessage(String message) { Configuration.tag = message; setConfigValue("Tag Message",messages); }

    public static String getTagOutMessage() { return tagOut; }

    public static void setTagOutMessage(String message) { Configuration.tagOut = message; setConfigValue("Tag Out Message",messages); }

    public static String getConsoleMessage() { return log; }

    public static void setConsoleMessage(String message) { Configuration.log = message; setConfigValue("Log",messages); }

    public static String getJoinMessageAfterPunishment() { return joinAfterPunishment; }

    public static void setJoinMessageAfterPunishment(String message) { Configuration.joinAfterPunishment = message; setConfigValue("Join Message After Punishment",messages); }

    public static String getCantUseCommandsInCombat() { return cantUseCommandsInCombat; }

    public static void setCantUseCommandsInCombat(String cantUseCommandsInCombat) { Configuration.cantUseCommandsInCombat = cantUseCommandsInCombat; setConfigValue("Cant Use Commands in Combat",messages);}

    public static String getCantTeleportInCombat() { return cantTeleportInCombat; }

    public static void setCantTeleportInCombat(String cantTeleportInCombat) { Configuration.cantTeleportInCombat = cantTeleportInCombat; setConfigValue("Cant Teleport in Combat",messages);}

    public static String getTagCommandInCombatMessage() { return tagCommandInCombat; }

    public static void setTagCommandInCombatMessage(String tagCommandInCombatMessage) { Configuration.tagCommandInCombat = tagCommandInCombatMessage; setConfigValue("Tag Command in Combat",messages); }

    public static String getTagCommandOutCombatMessage() { return tagCommandOutCombat; }

    public static void setTagCommandOutCombatMessage(String tagCommandOutCombatMessage) { Configuration.tagCommandOutCombat = tagCommandOutCombatMessage; setConfigValue("Tag Command out Combat",messages); }

    public static List<String> getHelpMessage() { return help; }

    public static void setHelpMessage(List<String> helpMessage) { Configuration.help = helpMessage; setConfigValue("Help",messages); }

    public static String getReloadedConfig() { return reloadedConfig; }

    public static void setReloadedConfigMessage(String reloadedConfig) { Configuration.reloadedConfig = reloadedConfig; setConfigValue("Reloaded Config",messages); }

    public static String getReloadedMessages() { return reloadedMessages; }

    public static void setReloadedMessages(String reloadedMessages) { Configuration.reloadedMessages = reloadedMessages; setConfigValue("Reloaded Messages",messages); }

    public static String getMissingPermissions() { return missingPermissions; }

    public static void setMissingPermissions(String missingPermissions) { Configuration.missingPermissions = missingPermissions; setConfigValue("Missing Permissions",messages); }

    public static String getOnlyPlayer() { return onlyPlayer; }

    public static void setOnlyPlayer(String message) { Configuration.onlyPlayer = message; setConfigValue("Only Player",messages);}
}
