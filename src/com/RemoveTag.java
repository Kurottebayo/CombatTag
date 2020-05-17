package com;

import com.Config.Configuration;
import com.Others.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

import static com.Others.Data.getPlayerTag;
import static com.Others.Functions.replaceColors;

public class RemoveTag extends Thread {

    private Player player;
    public RemoveTag(String player){
        if(Bukkit.getPlayer(player)!=null){
            this.player = Bukkit.getPlayer(player);
            this.start();
        }
    }

    private static HashMap<String, Thread> threads = new HashMap<>();

    public static HashMap<String, Thread> getThreads(){ return threads; }

    public static Thread getPlayerThread(String player){ return threads.get(player); }

    public static void deletePlayerThread(String player){ threads.get(player).interrupt(); threads.remove(player); }

    public static void createPlayerThread(String player){
        if(threads.containsKey(player)) deletePlayerThread(player);
        threads.put(player,new RemoveTag(player));
    }

    @Override
    public void run() {
        while (!this.isInterrupted() && !Data.isServerGoingDown() && Bukkit.getPlayer(player.getName()) != null && Data.getPlayerTag(player) > -1) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                break;
            }
            if (Bukkit.getPlayer(player.getName()) != null) {
                Data.setPlayerTag(player, Data.getPlayerTag(player) - 1);
                if (Data.getPlayerTag(player) == 0) {
                    Data.setPlayerTag(player, -1);
                    String message = replaceColors(Configuration.getPrefix() + Configuration.getTagOutMessage());
                    message = message.replace("<player>", player.getName());
                    message = message.replace("<tag-cooldown>", String.valueOf(getPlayerTag(player)));
                    player.getPlayer().sendMessage(message);
                }
            }
            /*for (Player p : Bukkit.getOnlinePlayers()) {
                if (Data.getPlayerTag(p) > -1) {
                    Data.setPlayerTag(p, Data.getPlayerTag(p) - 1);
                    if (Data.getPlayerTag(p) == 0) {
                        Data.setPlayerTag(p,-1);
                        String message = replaceColors(Configuration.getPrefix() + Configuration.getTagOutMessage());
                        message = message.replace("<player>", p.getName());
                        message = message.replace("<tag-cooldown>", String.valueOf(getPlayerTag(p)));
                        p.getPlayer().sendMessage(message);
                    }
                }
            }
             */
        }
    }
}
