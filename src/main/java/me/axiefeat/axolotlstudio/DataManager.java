package me.axiefeat.axolotlstudio;

import me.axiefeat.axolotlstudio.Commands.*;
import me.axiefeat.axolotlstudio.EventListener.*;
import me.axiefeat.axolotlstudio.Interest.Interest;
import me.axiefeat.axolotlstudio.Tab.Bank;
import me.axiefeat.axolotlstudio.Tab.Money;
import me.axiefeat.axolotlstudio.Tab.Pay;
import me.axiefeat.axolotlstudio.Utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class DataManager {
    private final Main plugin;

    public DataManager(Main plugin) {
        this.plugin = plugin;
    }

    public void setupPlugin() {
        long startTime = System.currentTimeMillis();
        long time;

        Logger.log("");
        Logger.log("    &aAxoCurrency &fЗагрузка плагина...");
        Logger.log("    &fВерсия &a" + plugin.getDescription().getVersion() + "&f!");
        Logger.log("    &fВерсия ядра: &a" + plugin.getServerVersion());

        time = System.currentTimeMillis();
        plugin.saveDefaultConfig();
        if (!Main.INSTANCE.getConfig().getString("version").equalsIgnoreCase("1.3")) {
            Logger.error("Неподдерживаемая версия плагина!");
            Bukkit.getPluginManager().disablePlugin(Main.INSTANCE);
        }
        File data = new File(Main.INSTANCE.getDataFolder(),  "interest.yml");
        if (!data.exists()) {
            try {
                data.createNewFile();
                FileConfiguration cfg = YamlConfiguration.loadConfiguration(data);
                cfg.createSection("players");
                cfg.save(data);
            } catch (IOException e) {
                Logger.error("Не удалось создать interest.yml!");
            }
        }
        Interest.start();

        Logger.log("    &fКонфиг файлы загружены! &7(&a" + (System.currentTimeMillis() - time) + "ms&7)");

        time = System.currentTimeMillis();
        registerEvents();
        Logger.log("    &fРегистрация ивентов завершена! &7(&a" + (System.currentTimeMillis() - time) + "ms&7)");

        time = System.currentTimeMillis();
        setupCommands();
        Logger.log("    &fЗагрузка команд завершена! &7(&a" + (System.currentTimeMillis() - time) + "ms&7)");
        Logger.log("    &aГотово! &7(&fВсего &a" + (System.currentTimeMillis() - startTime) + "ms&7)");
        Logger.log("");
    }

    public void shutdownPlugin() {
        plugin.reloadConfig();
        Logger.log("");
        Logger.log("    &aAxoCurrency &fвыключен!");
        Logger.log("");
    }

    private void registerEvents() {
        PluginManager pluginManager = plugin.getServer().getPluginManager();
        pluginManager.registerEvents(new JoinEvent(), plugin);
        pluginManager.registerEvents(new QuitEvent(), plugin);
        pluginManager.registerEvents(new BankWithdrawEvent(), plugin);
        pluginManager.registerEvents(new BankDepositEvent(), plugin);
        pluginManager.registerEvents(new MoneyWithdrawEvent(), plugin);
        pluginManager.registerEvents(new MoneyDepositEvent(), plugin);
    }

    private void setupCommands() {
        //plugin.getCommand("axocurrency").setExecutor(new AdminCmd());
        Objects.requireNonNull(plugin.getCommand("axocurrency")).setExecutor(new BankCmd());
        Objects.requireNonNull(plugin.getCommand("axocurrency")).setTabCompleter(new Bank());
        //plugin.getCommand("axocurrency").setExecutor(new AdminCmd());
        Objects.requireNonNull(plugin.getCommand("pay")).setExecutor(new PayCmd());
        Objects.requireNonNull(plugin.getCommand("pay")).setTabCompleter(new Pay());
        Objects.requireNonNull(plugin.getCommand("money")).setExecutor(new MoneyCmd());
        Objects.requireNonNull(plugin.getCommand("money")).setTabCompleter(new Money());
        //plugin.getCommand("money").setExecutor(new AdminMoneyCmd());
    }
}
