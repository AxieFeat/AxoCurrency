package me.axiefeat.axolotlstudio;

import me.axiefeat.axolotlstudio.Utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLOutput;

public final class Main extends JavaPlugin {

   // private ProtocolManager protocolManager;

    public static Main INSTANCE;

    private DataManager dataManager;

    private String serverVersion;


    @Override
    public void onEnable() {
        //protocolManager = ProtocolLibrary.getProtocolManager();
        PluginManager plManager = Bukkit.getPluginManager();
        if (plManager.getPlugin("Vault") == null) {
            Logger.error("");
            Logger.error("&cОшибка загрузки AxoCurrency");
            Logger.error("&cУстановите Vault!");
            Logger.error("");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        INSTANCE = this;
        this.serverVersion = getServer().getVersion();
        this.dataManager = new DataManager(this);
        dataManager.setupPlugin();
        if (plManager.getPlugin("PlaceholderAPI") != null) {
            Logger.info("Плейсхолдеры загружены! &a(PlaceholderAPI)");
            new Placeholders().register();
        }

    }


    @Override
    public void onDisable() {
        dataManager.shutdownPlugin();
    }

    public String getServerVersion() {
        return serverVersion;
    }
}
