package me.axiefeat.axolotlstudio.Interest;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import me.axiefeat.axolotlstudio.Main;
import me.axiefeat.axolotlstudio.Utils.Config;
import me.axiefeat.axolotlstudio.Utils.Logger;
import net.ess3.api.MaxMoneyException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Interest {

    public static long unix;

    public static Timer timer = new Timer("Timer");

    public static void give (Player player, int result, int balance, String uuid) {
        File file = new File(Main.INSTANCE.getDataFolder() + File.separator + "playerdata", uuid + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        int max = Main.INSTANCE.getConfig().getInt("Interest.Max-Amount");
        int maxmoney = Main.INSTANCE.getConfig().getInt("Max-Money");
        if (Main.INSTANCE.getConfig().getBoolean("Interest.Enabled")) {
            if ((result >= max)) {
                if (!Main.INSTANCE.getConfig().getBoolean("Interest.Give-To-Offline-Players")) {
                    if (Main.INSTANCE.getConfig().getBoolean("Interest.Ignore-AFK-Players")) {
                        if (player != null) {
                            if (result == 0) {
                                if (player != null) {
                                    if (Main.INSTANCE.getConfig().getBoolean("Interest.Send-Null-Message") && (Main.INSTANCE.getConfig().getBoolean("Interest.Send-Message"))) {
                                        player.sendMessage(Config.getString("Messages.interest.get-null"));
                                    }
                                }
                            } else {
                                cfg.set("Money", cfg.getInt("Money") + max);
                                try {
                                    cfg.save(file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                if (Main.INSTANCE.getConfig().getBoolean("Interest.Send-Message")) {
                                    player.sendMessage(Config.getString("Messages.interest.get").replace("%amount%", String.valueOf(max)));
                                }
                            }
                        }
                    } else {
                        if (!Util.isAFK(player)) {
                            if (player != null) {
                                if (result == 0) {
                                    if (player != null) {
                                        if (Main.INSTANCE.getConfig().getBoolean("Interest.Send-Null-Message") && (Main.INSTANCE.getConfig().getBoolean("Interest.Send-Message"))) {
                                            player.sendMessage(Config.getString("Messages.interest.get-null"));
                                        }
                                    }
                                } else {
                                    cfg.set("Money", cfg.getInt("Money") + max);
                                    try {
                                        cfg.save(file);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    if (Main.INSTANCE.getConfig().getBoolean("Interest.Send-Message")) {
                                        player.sendMessage(Config.getString("Messages.interest.get").replace("%amount%", String.valueOf(max)));
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (player == null) {
                        cfg.set("Money", cfg.getInt("Money") + max);
                        try {
                            cfg.save(file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        if (Main.INSTANCE.getConfig().getBoolean("Interest.Ignore-AFK-Players")) {
                            if (player != null) {
                                if (result == 0) {
                                    if (player != null) {
                                        if (Main.INSTANCE.getConfig().getBoolean("Interest.Send-Null-Message") && (Main.INSTANCE.getConfig().getBoolean("Interest.Send-Message"))) {
                                            player.sendMessage(Config.getString("Messages.interest.get-null"));
                                        }
                                    }
                                } else {
                                    cfg.set("Money", cfg.getInt("Money") + max);
                                    try {
                                        cfg.save(file);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    if (Main.INSTANCE.getConfig().getBoolean("Interest.Send-Message")) {
                                        player.sendMessage(Config.getString("Messages.interest.get").replace("%amount%", String.valueOf(max)));
                                    }
                                }
                            }
                        } else {
                            if (!Util.isAFK(player)) {
                                if (player != null) {
                                    if (result == 0) {
                                        if (player != null) {
                                            if (Main.INSTANCE.getConfig().getBoolean("Interest.Send-Null-Message") && (Main.INSTANCE.getConfig().getBoolean("Interest.Send-Message"))) {
                                                player.sendMessage(Config.getString("Messages.interest.get-null"));
                                            }
                                        }
                                    } else {
                                        cfg.set("Money", cfg.getInt("Money") + max);
                                        try {
                                            cfg.save(file);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                        if (Main.INSTANCE.getConfig().getBoolean("Interest.Send-Message")) {
                                            player.sendMessage(Config.getString("Messages.interest.get").replace("%amount%", String.valueOf(max)));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                if (balance <= maxmoney && (balance + result) <= maxmoney) {
                    if (!Main.INSTANCE.getConfig().getBoolean("Interest.Give-To-Offline-Players")) {
                        if (Main.INSTANCE.getConfig().getBoolean("Interest.Ignore-AFK-Players")) {
                            if (player != null) {
                                if (result == 0) {
                                    if (player != null) {
                                        if (Main.INSTANCE.getConfig().getBoolean("Interest.Send-Null-Message") && (Main.INSTANCE.getConfig().getBoolean("Interest.Send-Message"))) {
                                            player.sendMessage(Config.getString("Messages.interest.get-null"));
                                        }
                                    }
                                } else {
                                    cfg.set("Money", cfg.getInt("Money") + result);
                                    try {
                                        cfg.save(file);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    if (Main.INSTANCE.getConfig().getBoolean("Interest.Send-Message")) {
                                        player.sendMessage(Config.getString("Messages.interest.get").replace("%amount%", String.valueOf(result)));
                                    }
                                }
                            }
                        } else {
                            if (!Util.isAFK(player)) {
                                if (player != null) {
                                    if (result == 0) {
                                        if (player != null) {
                                            if (Main.INSTANCE.getConfig().getBoolean("Interest.Send-Null-Message") && (Main.INSTANCE.getConfig().getBoolean("Interest.Send-Message"))) {
                                                player.sendMessage(Config.getString("Messages.interest.get-null"));
                                            }
                                        }
                                    } else {
                                        cfg.set("Money", cfg.getInt("Money") + result);
                                        try {
                                            cfg.save(file);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                        if (Main.INSTANCE.getConfig().getBoolean("Interest.Send-Message")) {
                                            player.sendMessage(Config.getString("Messages.interest.get").replace("%amount%", String.valueOf(result)));
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        if (player == null) {
                            cfg.set("Money", cfg.getInt("Money") + result);
                            try {
                                cfg.save(file);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            if (Main.INSTANCE.getConfig().getBoolean("Interest.Ignore-AFK-Players")) {
                                if (player != null) {
                                    if (result == 0) {
                                        if (player != null) {
                                            if (Main.INSTANCE.getConfig().getBoolean("Interest.Send-Null-Message") && (Main.INSTANCE.getConfig().getBoolean("Interest.Send-Message"))) {
                                                player.sendMessage(Config.getString("Messages.interest.get-null"));
                                            }
                                        }
                                    } else {
                                        cfg.set("Money", cfg.getInt("Money") + result);
                                        try {
                                            cfg.save(file);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                        if (Main.INSTANCE.getConfig().getBoolean("Interest.Send-Message")) {
                                            player.sendMessage(Config.getString("Messages.interest.get").replace("%amount%", String.valueOf(result)));
                                        }
                                    }
                                }
                            } else {
                                if (!Util.isAFK(player)) {
                                    if (player != null) {
                                        if (result == 0) {
                                            if (player != null) {
                                                if (Main.INSTANCE.getConfig().getBoolean("Interest.Send-Null-Message") && (Main.INSTANCE.getConfig().getBoolean("Interest.Send-Message"))) {
                                                    player.sendMessage(Config.getString("Messages.interest.get-null"));
                                                }
                                            }
                                        } else {
                                            cfg.set("Money", cfg.getInt("Money") + result);
                                            try {
                                                cfg.save(file);
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
                                            if (Main.INSTANCE.getConfig().getBoolean("Interest.Send-Message")) {
                                                player.sendMessage(Config.getString("Messages.interest.get").replace("%amount%", String.valueOf(result)));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (player != null) {
                        if (Main.INSTANCE.getConfig().getBoolean("Interest.Send-Null-Message") && (Main.INSTANCE.getConfig().getBoolean("Interest.Send-Message"))) {
                            player.sendMessage(Config.getString("Messages.interest.get-null"));
                        }
                    }
                }
            }
        }
    }

    public static long timer() {
        return System.currentTimeMillis() + Main.INSTANCE.getConfig().getInt("Interest.Delay");
    }
    public static void start() {

        unix = System.currentTimeMillis() + Main.INSTANCE.getConfig().getLong("Interest.Delay");

        TimerTask task = new TimerTask() {
            public void run() {
                try {
                    File data = new File(Main.INSTANCE.getDataFolder(),  "interest.yml");
                    FileConfiguration cfg = YamlConfiguration.loadConfiguration(data);
                    for (int i = 0; i < cfg.getConfigurationSection("players").getKeys(false).size(); i++) {
                        ArrayList<String> list = new ArrayList<>(cfg.getConfigurationSection("players").getKeys(false).stream().toList());
                        String uuid = cfg.getString("players." + list.get(i) + ".UUID");
                        File file = new File(Main.INSTANCE.getDataFolder() + File.separator + "playerdata", uuid + ".yml");
                        FileConfiguration cfg2 = YamlConfiguration.loadConfiguration(file);
                        int money = cfg2.getInt("Money");
                        int percent = Main.INSTANCE.getConfig().getInt("Interest.Money-Percent");
                        int result = (money / 100) * (percent);
                        Player player = Bukkit.getPlayer(list.get(i));
                        give(player, result, money, uuid);
                    }
                    unix = System.currentTimeMillis() + Main.INSTANCE.getConfig().getLong("Interest.Delay");
                } catch (NullPointerException ignored) {
                    Logger.error("Для работы процентов на сервер должен зайти хотя бы 1 игрок!");
                }
            }
        };
        long delay = 1L;
        long peroid = Main.INSTANCE.getConfig().getInt("Interest.Delay");
        timer.schedule (task, delay, peroid);
    }
    public static void reload() {
        timer.purge();
        start();
    }
}
