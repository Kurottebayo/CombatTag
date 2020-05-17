package com;

import com.Others.Data;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static com.Config.Configuration.loadConfiguration;
import static com.Config.Configuration.loadMessages;
import static com.Others.Data.loadLoggedOutData;

public class main extends JavaPlugin {

    private static main plugin;
    private static PluginManager manager;
    private static final String version = "1.0-Beta";

    public void onEnable(){
        Data.setIsServerGoingDown(false);
        plugin = this;
        manager = getServer().getPluginManager();
        manager.registerEvents(new Events(),plugin);
        Bukkit.getConsoleSender().sendMessage("§9CombatTag> §6" + version + " version");
        Bukkit.getConsoleSender().sendMessage("§9CombatTag> §6Plugin developed by §eKurottebayo");
        this.getCommand("ct").setExecutor(new Commands());
        this.getCommand("combattag").setExecutor(new Commands());
        this.getCommand("tag").setExecutor(new Commands());
        this.setEnabled(true);
        loadConfiguration();
        loadMessages();
        loadLoggedOutData();
        Bukkit.getConsoleSender().sendMessage("§9CombatTag> §aPlugin enabled! ");
    }

    public void onDisable(){
        Data.setIsServerGoingDown(true);
        Bukkit.getConsoleSender().sendMessage("§9CombatTag> §aPlugin disabled! ");
    }

    public static main getPlugin(){
        return plugin;
    }

}
