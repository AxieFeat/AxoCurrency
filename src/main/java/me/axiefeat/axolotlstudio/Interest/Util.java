package me.axiefeat.axolotlstudio.Interest;

import com.earth2me.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Util {

    static Essentials ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
    public static boolean isAFK (Player player)  {
        if (player == null) return false;
        if (ess.getUser(player).isAfk()) {
            return true;
        } else return false;
    }
}
