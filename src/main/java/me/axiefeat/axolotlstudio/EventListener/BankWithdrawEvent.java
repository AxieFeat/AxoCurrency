package me.axiefeat.axolotlstudio.EventListener;

import me.axiefeat.axolotlstudio.Itemstack;
import me.axiefeat.axolotlstudio.Main;
import me.axiefeat.axolotlstudio.Utils.Config;
import me.axiefeat.axolotlstudio.Utils.Utils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.File;
import java.io.IOException;

public class BankWithdrawEvent implements Listener {

    public static int BankWithdrawInt;
    @EventHandler
    public static void ChatWait(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        File file = new File(Main.INSTANCE.getDataFolder() + File.separator + "playerdata", player.getUniqueId() + ".yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        if (Utils.BankPlayerWithdrawing.contains(player.getUniqueId())) {
            if (event.getMessage().equalsIgnoreCase(Main.INSTANCE.getConfig().getString("General.withdraw.exit"))) {
                event.setCancelled(true);
                Utils.removePlayerFromBankWithdrawing(player);
                player.sendMessage(Config.getString("Messages.main.exit"));
                return;
            }
            try {
                event.setCancelled(true);
                BankWithdrawInt = Integer.parseInt(event.getMessage());
                if (cfg.getInt("Money") < BankWithdrawInt) {
                    player.sendMessage(Config.getString("Messages.errors.min-limit-error"));
                    Utils.removePlayerFromBankWithdrawing(player);
                    return;
                }
                if (BankWithdrawInt <= Itemstack.getEmptySlots(player.getInventory())) {
                    Itemstack.giveVault(player, BankWithdrawInt);
                    cfg.set("Money", cfg.getInt("Money") - BankWithdrawInt);
                    cfg.save(file);
                    player.sendMessage(Config.getString("Messages.main.money-withdraw").replace("%amount%", String.valueOf(BankWithdrawInt)));
                    Utils.playsound(player, "withdraw");
                    Utils.removePlayerFromBankWithdrawing(player);
                    return;
                } else {
                    player.sendMessage(Config.getString("Messages.errors.full"));
                    Utils.removePlayerFromBankWithdrawing(player);
                    return;
                }
            } catch (NumberFormatException e) {
                event.setCancelled(true);
                player.sendMessage(Config.getString("Messages.errors.amount-error-number").replace("%amount%", event.getMessage()));
                Utils.removePlayerFromBankWithdrawing(player);
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
