package com;

import com.Config.Configuration;
import com.Objects.Tag;
import com.Others.Data;
import com.Constants.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import static com.Config.Configuration.*;
import static com.Constants.Paths.BINARY_DATABASE;
import static com.Others.Functions.*;
import static com.Others.Data.*;
import static kuro.Serializable.reWriteObject;

public class Events implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!Data.isServerGoingDown()) {
            Data.addPlayer(event.getPlayer());
            if (getLogTag(event.getPlayer()) != null && getLogTag(event.getPlayer()).isLoggedOut()) {
                Tag t = new Tag(getLogTag(event.getPlayer()).getPlayer(),getLogTag(event.getPlayer()).getLoggedOutTimes(),false);
                reWriteObject(getLogTag(event.getPlayer()),t,BINARY_DATABASE);
                addLoggedOut(event.getPlayer(),t);
                String message = replaceColors(Configuration.getPrefix() + Configuration.getJoinMessageAfterPunishment());
                message = message.replace("<player>", event.getPlayer().getName());
                message = message.replace("<tag-cooldown>", String.valueOf(getPlayerTag(event.getPlayer())));
                event.getPlayer().sendMessage(message);
            }
        }
    }
    @EventHandler
    public void onPreprocessCommand(PlayerCommandPreprocessEvent event)
    {
        Player player = event.getPlayer();
        String label;
        if(event.getMessage().contains(" ")) {
            label = event.getMessage().substring(1, event.getMessage().indexOf(" "));
        } else {
            label = event.getMessage().substring(1);
        }
        if(!Configuration.getWhitelistedCommands().contains(label.toLowerCase()) && getPlayerTag(player)>-1){
            String message = replaceColors(Configuration.getPrefix() + Configuration.getCantUseCommandsInCombat());
            message = message.replace("<player>", player.getName());
            message = message.replace("<tag-cooldown>", String.valueOf(getPlayerTag(player)));
            player.sendMessage(message);
            event.setCancelled(true);
        }
    }

    public static void logTag(Player player){
        Tag tag = getLogTag(player);
        if(getLogTag(player)==null){
            tag = new Tag(player.getName(),0,true);
            kuro.Serializable.writeObject(tag, BINARY_DATABASE, true);
            Data.addLoggedOut(player,tag);
        } else {
            int index = kuro.Serializable.getObjectPosition(tag, BINARY_DATABASE);
            if (index == -1) {
                kuro.Serializable.writeObject(tag, BINARY_DATABASE, true);
            } else {
                reWriteObject(tag, index, BINARY_DATABASE);
            }
            tag.setLoggedOut(true);
            Data.addLoggedOut(player,tag);
        }
        if(!Configuration.getBypass() || !player.hasPermission(Permissions.BYPASS)) {
            if (Configuration.isBroadcast() && !Configuration.getBroadcastMessage().equals("")) {
                String message = replaceColors(Configuration.getPrefix() + Configuration.getBroadcastMessage());
                message = message.replace("<player>", player.getName());
                message = message.replace("<tag-cooldown>", String.valueOf(getPlayerTag(player)));
                for (Player p : Bukkit.getOnlinePlayers()) p.sendMessage(message);
            }
            if (!Configuration.getPunishment().equals("")) {
                if (Configuration.getPunishment().equalsIgnoreCase("kill")) {
                    player.setHealth(0);
                } else {
                    player.getInventory().clear();
                }
            }
            if (!Configuration.getPunishmentCommand().equalsIgnoreCase("null") && !Configuration.getPunishmentCommand().equalsIgnoreCase("")) {
                String command = Configuration.getPunishmentCommand().substring(1);
                command = command.replace("<player>", player.getName());
                command = command.replace("<tag-cooldown>", "null");
                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
            }
        }
        String message = replaceColors(Configuration.getPrefix() + Configuration.getLogMessage());
        message = message.replace("<player>", player.getName());
        message = message.replace("<tag-cooldown>", String.valueOf(getPlayerTag(player)));
        Bukkit.getConsoleSender().sendMessage(message);

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (!Data.isServerGoingDown() && Data.getPlayerTag(event.getPlayer())>-1) {
            RemoveTag.deletePlayerThread(event.getPlayer().getName());
            logTag(event.getPlayer());
        }
        Data.removePlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event){
        if (!Data.isServerGoingDown()) {
            if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
                Player victim = (Player) event.getEntity();
                Player attacker = (Player) event.getDamager();
                if (!Configuration.getBypass() || !victim.hasPermission(Permissions.BYPASS)) {
                    if (getPlayerTag(victim) == -1) {
                        setPlayerTag(victim, getCooldown());
                        String message = replaceColors(getPrefix() + getTagMessage());
                        message = message.replace("<player>", victim.getName());
                        message = message.replace("<tag-cooldown>", String.valueOf(getPlayerTag(victim)));
                        victim.sendMessage(message);
                        RemoveTag.createPlayerThread(victim.getName());
                    } else setPlayerTag(victim, getCooldown());
                }
                if (!Configuration.getBypass() || !attacker.hasPermission(Permissions.BYPASS)) {
                    if (getPlayerTag(attacker) == -1) {
                        setPlayerTag(attacker, getCooldown());
                        String message = replaceColors(getPrefix() + getTagMessage());
                        message = message.replace("<player>", attacker.getName());
                        message = message.replace("<tag-cooldown>", String.valueOf(getPlayerTag(attacker)));
                        attacker.sendMessage(message);
                        RemoveTag.createPlayerThread(attacker.getName());
                    } else setPlayerTag(attacker, getCooldown());
                }
            }
        }
    }
    @EventHandler
    public void onTeleport(PlayerTeleportEvent event){
        if(Data.getPlayerTag(event.getPlayer()) > -1 && Configuration.getCancelTeleport() != null && Configuration.getCancelTeleport().contains(event.getCause().toString().toLowerCase())) {
            event.setCancelled(true);
            String message = replaceColors(getPrefix() + getCantTeleportInCombat());
            message = message.replace("<player>", event.getPlayer().getName());
            message = message.replace("<tag-cooldown>", String.valueOf(getPlayerTag(event.getPlayer())));
            event.getPlayer().sendMessage(message);
        }
    }
}
