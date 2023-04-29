package me.axiefeat.axolotlstudio.EventListener;

import me.axiefeat.axolotlstudio.Itemstack;
import me.axiefeat.axolotlstudio.Main;
import me.axiefeat.axolotlstudio.Utils.Config;
import me.axiefeat.axolotlstudio.Utils.Utils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.File;
import java.io.IOException;

public class BankDepositEvent implements Listener {
    public static int BankDepositInt;
    @EventHandler
    public static void ChatWait(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        File file = new File(Main.INSTANCE.getDataFolder() + File.separator + "playerdata", player.getUniqueId() + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        if (Utils.BankPlayerDepositing.contains(player.getUniqueId())) {
            if (event.getMessage().equalsIgnoreCase(Main.INSTANCE.getConfig().getString("General.deposit.exit"))) {
                event.setCancelled(true);
                Utils.removePlayerFromBankDepositing(player);
                player.sendMessage(Config.getString("Messages.main.exit"));
                return;
            }
            try {
                event.setCancelled(true);
                BankDepositInt = Integer.parseInt(event.getMessage());
                if (cfg.getInt("Money") + BankDepositInt > Main.INSTANCE.getConfig().getInt("Max-Money")) {
                    player.sendMessage(Config.getString("Messages.errors.max-limit-error"));
                    Utils.removePlayerFromBankDepositing(player);
                    return;
                }
                if (Itemstack.hasVault(player, BankDepositInt)) {
                    Itemstack.takeVault(player, BankDepositInt);
                    cfg.set("Money", cfg.getInt("Money") + BankDepositInt);
                    cfg.save(file);
                    Utils.removePlayerFromBankDepositing(player);
                    player.sendMessage(Config.getString("Messages.main.money-deposit").replace("%amount%", String.valueOf(BankDepositInt)));
                    Utils.playsound(player, "deposit");
                    return;
                } else {
                    Utils.removePlayerFromBankDepositing(player);
                    player.sendMessage(Config.getString("Messages.errors.unenough"));
                    return;
                }
            } catch (NumberFormatException e) {
                event.setCancelled(true);
                player.sendMessage(Config.getString("Messages.errors.amount-error-number").replace("%amount%", event.getMessage()));
                Utils.removePlayerFromBankDepositing(player);
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
