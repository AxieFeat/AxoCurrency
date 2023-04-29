package me.axiefeat.axolotlstudio.EventListener;

import me.axiefeat.axolotlstudio.Interest.Interest;
import me.axiefeat.axolotlstudio.Main;
import me.axiefeat.axolotlstudio.Utils.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;

public class JoinEvent implements Listener {
    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        File folder = new File(Main.INSTANCE.getDataFolder() + File.separator + "playerdata");
        if (!folder.exists()) {
            try {
                folder.mkdirs();
                folder.createNewFile();
            } catch (IOException e) {
                Logger.error("Не удалось создать папку \"playerdata\"!");
            }
        }
        File file = new File(Main.INSTANCE.getDataFolder() + File.separator + "playerdata", player.getUniqueId() + ".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                cfg.createSection("PlayerName");
                cfg.set("PlayerName", player.getName());
                cfg.createSection("UUID");
                cfg.set("UUID", player.getUniqueId().toString());
                cfg.createSection("FirstJoin");
                cfg.set("FirstJoin", System.currentTimeMillis());
                cfg.createSection("Money");
                cfg.set("Money", 0);
                cfg.save(file);
            } catch (IOException e) {
                Logger.error("Не удалось сохранить информацию о " + player.getName() + "(" + player.getUniqueId() + ")!");
            }
        }
        File data = new File(Main.INSTANCE.getDataFolder(),  "interest.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(data);
        if (!cfg.getConfigurationSection("players").contains(player.getName())) {
            cfg.createSection("players." + player.getName() + ".UUID");
            cfg.set("players." + player.getName() + ".UUID", player.getUniqueId().toString());
            //Interest.reload();
        }
        try {
            cfg.save(data);
        } catch (IOException e) {
            Logger.error("Не удалось сохранить информацию о " + player.getName() + "(" + player.getUniqueId() + ")!");
        }
    }
}
