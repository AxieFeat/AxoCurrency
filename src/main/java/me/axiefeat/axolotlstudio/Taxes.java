package me.axiefeat.axolotlstudio;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import me.axiefeat.axolotlstudio.Utils.Config;
import me.axiefeat.axolotlstudio.Utils.Logger;
import net.ess3.api.MaxMoneyException;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Taxes {
    public static String getGroup (Player player) {
        if (player == null) Logger.error("При попытке получить группу игрока " + player + " произошла ошибка!");
        ArrayList<String> list = new ArrayList<>(Main.INSTANCE.getConfig().getConfigurationSection("General.withdraw.taxes").getKeys(false));
        for (int i = 0; i < list.size(); i++) {
            if (player.hasPermission(list.get(i))) return list.get(i);
        }
        return "&cERROR";
    }
    public static void byTaxesWithdraw (Player player, int amount) {
        File file = new File(Main.INSTANCE.getDataFolder() + File.separator + "playerdata", player.getUniqueId() + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        String group = getGroup(player);
        int percent = Main.INSTANCE.getConfig().getInt("General.withdraw.taxes." + group);
        int result = (amount*percent/100);
        int max = Main.INSTANCE.getConfig().getInt("General.withdraw.max-taxe");
        if (amount < result) {
            if (Main.INSTANCE.getConfig().getString("General.withdraw.sound").equalsIgnoreCase("none")) {
                player.sendMessage(Config.getString("Messages.main.withdraw").replace("%amount%", String.valueOf(amount)).replace("%taxe%", String.valueOf(amount)));
            } else {
                player.playSound(player.getLocation(), Sound.valueOf(Main.INSTANCE.getConfig().getString("General.withdraw.sound")), 500.0f, 1.0f);
                player.sendMessage(Config.getString("Messages.main.withdraw").replace("%amount%", String.valueOf(amount)).replace("%taxe%", String.valueOf(amount)));
            }
        }
        if (result > max) {
            try {
                Economy.setMoney(player.getName(), (Economy.getMoneyExact(player.getName()).doubleValue() + amount - max));
                cfg.set("Money", cfg.getInt("Money") - (amount));
                try {
                    cfg.save(file);
                } catch (IOException e) {
                    Logger.error("Не удалось сохранить баланс игрока " + player.getName() + "!");
                }
            } catch (UserDoesNotExistException | MaxMoneyException | NoLoanPermittedException e) {
                player.sendMessage(Config.getString("Messages.errors.what"));
            }
            if (Main.INSTANCE.getConfig().getString("General.withdraw.sound").equalsIgnoreCase("none")) {
                player.sendMessage(Config.getString("Messages.main.withdraw").replace("%amount%", String.valueOf(amount)).replace("%taxe%", String.valueOf(max)));
            } else {
                player.playSound(player.getLocation(), Sound.valueOf(Main.INSTANCE.getConfig().getString("General.withdraw.sound")), 500.0f, 1.0f);
                player.sendMessage(Config.getString("Messages.main.withdraw").replace("%amount%", String.valueOf(amount)).replace("%taxe%", String.valueOf(max)));
            }
        } else {
            try {
                Economy.setMoney(player.getName(), (Economy.getMoneyExact(player.getName()).doubleValue() + (amount - result)));
                cfg.set("Money", cfg.getInt("Money") - (amount));
                try {
                    cfg.save(file);
                } catch (IOException e) {
                    Logger.error("Не удалось сохранить баланс игрока " + player.getName() + "!");
                }
            } catch (UserDoesNotExistException | MaxMoneyException | NoLoanPermittedException e) {
                player.sendMessage(Config.getString("Messages.errors.what"));
            }
            if (Main.INSTANCE.getConfig().getString("General.withdraw.sound").equalsIgnoreCase("none")) {
                player.sendMessage(Config.getString("Messages.main.withdraw").replace("%amount%", String.valueOf(amount)).replace("%taxe%", String.valueOf(result)));
            } else {
                player.playSound(player.getLocation(), Sound.valueOf(Main.INSTANCE.getConfig().getString("General.withdraw.sound")), 500.0f, 1.0f);
                player.sendMessage(Config.getString("Messages.main.withdraw").replace("%amount%", String.valueOf(amount)).replace("%taxe%", String.valueOf(result)));
            }
        }
    }

    public static void byTaxesDeposit (Player player, int amount) {
        File file = new File(Main.INSTANCE.getDataFolder() + File.separator + "playerdata", player.getUniqueId() + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        String group = getGroup(player);
        int percent = Main.INSTANCE.getConfig().getInt("General.deposit.taxes." + group);
        int result = ((amount * percent) / 100);
        int max = Main.INSTANCE.getConfig().getInt("General.deposit.max-taxe");
        if (amount < result) {
            if (Main.INSTANCE.getConfig().getString("General.deposit.sound").equalsIgnoreCase("none")) {
                player.sendMessage(Config.getString("Messages.main.deposit").replace("%amount%", String.valueOf(amount)).replace("%taxe%", String.valueOf(amount)));
            } else {
                player.playSound(player.getLocation(), Sound.valueOf(Main.INSTANCE.getConfig().getString("General.deposit.sound")), 500.0f, 1.0f);
                player.sendMessage(Config.getString("Messages.main.deposit").replace("%amount%", String.valueOf(amount)).replace("%taxe%", String.valueOf(amount)));
            }
        }
        if (result > max) {
            try {
                Economy.setMoney(player.getName(), (Economy.getMoneyExact(player.getName()).doubleValue() - amount));
                cfg.set("Money", cfg.getInt("Money") + (amount - max));
                try {
                    cfg.save(file);
                } catch (IOException e) {
                    Logger.error("Не удалось сохранить баланс игрока " + player.getName() + "!");
                }
            } catch (UserDoesNotExistException | MaxMoneyException | NoLoanPermittedException e) {
                player.sendMessage(Config.getString("Messages.errors.what"));
            }
            if (Main.INSTANCE.getConfig().getString("General.deposit.sound").equalsIgnoreCase("none")) {
                player.sendMessage(Config.getString("Messages.main.deposit").replace("%amount%", String.valueOf(amount)).replace("%taxe%", String.valueOf(max)));
            } else {
                player.playSound(player.getLocation(), Sound.valueOf(Main.INSTANCE.getConfig().getString("General.withdraw.sound")), 500.0f, 1.0f);
                player.sendMessage(Config.getString("Messages.main.deposit").replace("%amount%", String.valueOf(amount)).replace("%taxe%", String.valueOf(max)));
            }
        } else {
            try {
                Economy.setMoney(player.getName(), (Economy.getMoneyExact(player.getName()).doubleValue() - amount));
                cfg.set("Money", cfg.getInt("Money") + (amount - result));
                try {
                    cfg.save(file);
                } catch (IOException e) {
                    Logger.error("Не удалось сохранить баланс игрока " + player.getName() + "!");
                }
            } catch (UserDoesNotExistException | MaxMoneyException | NoLoanPermittedException e) {
                player.sendMessage(Config.getString("Messages.errors.what"));
            }
            if (Main.INSTANCE.getConfig().getString("General.deposit.sound").equalsIgnoreCase("none")) {
                player.sendMessage(Config.getString("Messages.main.deposit").replace("%amount%", String.valueOf(amount)).replace("%taxe%", String.valueOf(result)));
            } else {
                player.playSound(player.getLocation(), Sound.valueOf(Main.INSTANCE.getConfig().getString("General.withdraw.sound")), 500.0f, 1.0f);;
                player.sendMessage(Config.getString("Messages.main.deposit").replace("%amount%", String.valueOf(amount)).replace("%taxe%", String.valueOf(result)));
            }
        }
    }
}
