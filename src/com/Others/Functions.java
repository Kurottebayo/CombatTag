package com.Others;

public class Functions {
    public static String replaceColors(String value){
        if(value.contains("&0")) value = value.replace("&0","§0");
        if(value.contains("&1")) value = value.replace("&1","§1");
        if(value.contains("&2")) value = value.replace("&2","§2");
        if(value.contains("&3")) value = value.replace("&3","§3");
        if(value.contains("&4")) value = value.replace("&4","§4");
        if(value.contains("&5")) value = value.replace("&5","§5");
        if(value.contains("&6")) value = value.replace("&6","§6");
        if(value.contains("&7")) value = value.replace("&7","§7");
        if(value.contains("&8")) value = value.replace("&8","§8");
        if(value.contains("&9")) value = value.replace("&9","§9");
        if(value.contains("&a")) value = value.replace("&a","§a");
        if(value.contains("&b")) value = value.replace("&b","§b");
        if(value.contains("&c")) value = value.replace("&c","§c");
        if(value.contains("&d")) value = value.replace("&d","§d");
        if(value.contains("&e")) value = value.replace("&e","§e");
        if(value.contains("&f")) value = value.replace("&f","§f");
        if(value.contains("&o")) value = value.replace("&o","§o");
        if(value.contains("&l")) value = value.replace("&l","§l");
        if(value.contains("&i")) value = value.replace("&i","§i");
        if(value.contains("&m")) value = value.replace("&m","§m");
        if(value.contains("&r")) value = value.replace("&r","§r");
        return value;
    }
}
