package me.axiefeat.axolotlstudio.Utils;

import me.axiefeat.axolotlstudio.Main;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Config {
    public static String getString(String key) {
        if (key == null) return "&cСтрока не указана!";
        String string = Main.INSTANCE.getConfig().getString(key).replace("%prefix%", Main.INSTANCE.getConfig().getString("Plugin-prefix"));
        if (string == null) return "&cСтрока не найдена!";
        ChatColor.translateAlternateColorCodes('&', string);
        return string;
    }
    public static int getInt(String key) {
        if (key == null) return 0;
        int Int = Main.INSTANCE.getConfig().getInt(key);
        return Int;
    }
    public static String getList(String key) {
       // ArrayList<String> list = (ArrayList<String>) new ArrayList<>(Main.INSTANCE.getConfig().getList(key));
        return Main.INSTANCE.getConfig().getList(key).toString().replace("%prefix%", Main.INSTANCE.getConfig().getString("Plugin-prefix"));
    }
}
