package com.Others;

import com.Objects.Tag;
import kuro.Serializable;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

import static com.Constants.Paths.BINARY_DATABASE;

public class Data {
    private static HashMap<Player, Integer> tag = new HashMap<>();
    private static HashMap<String, Tag> loggedOut = new HashMap<>();
    private static boolean isServerGoingDown = false;

    @SuppressWarnings("unchecked")
    public static void loadLoggedOutData(){
        if(Serializable.sizeOf(BINARY_DATABASE) > 0){
            List<Tag> tags = (List<Tag>) (List<?>) Serializable.readAll(BINARY_DATABASE);
            for(Tag t :  tags) loggedOut.put(t.getPlayer(),t);
        }
    }

    public static HashMap<Player, Integer> getTag() {
        return tag;
    }

    public static void setPlayerTag(Player player, int cooldown) {
        tag.put(player,cooldown);
    }

    public static int getPlayerTag(Player player){
        return tag.get(player);
    }

    public static void removePlayer(Player player){
        tag.remove(player);
    }

    public static void addPlayer(Player player){
        tag.put(player,-1);
    }

    public static boolean isServerGoingDown() { return isServerGoingDown; }

    public static void setIsServerGoingDown(boolean isServerGoingDown) { Data.isServerGoingDown = isServerGoingDown; }



    public static HashMap<String, Tag> getLoggedOut() { return loggedOut; }

    public static void setLoggedOut(HashMap<String, Tag> loggedOut) { Data.loggedOut = loggedOut; }

    public static void addLoggedOut(Player player, Tag tag) { loggedOut.put(player.getName(), tag); }

    public static Tag getLogTag(Player player){ return loggedOut.get(player.getName()); }

}
