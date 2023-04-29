package me.axiefeat.axolotlstudio.EventListener;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import me.axiefeat.axolotlstudio.Itemstack;
import me.axiefeat.axolotlstudio.Main;
import me.axiefeat.axolotlstudio.Utils.Config;
import me.axiefeat.axolotlstudio.Utils.Utils;
import net.ess3.api.MaxMoneyException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.File;

public class MoneyWithdrawEvent implements Listener {
    public static int MoneyWithdrawInt;
    @EventHandler
    public static void ChatWait(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (Utils.MoneyPlayerWithdrawing.contains(player.getUniqueId())) {
            if (event.getMessage().equalsIgnoreCase(Main.INSTANCE.getConfig().getString("General.deposit.exit"))) {
                event.setCancelled(true);
                Utils.removePlayerFromMoneyWithdrawing(player);
                player.sendMessage(Config.getString("Messages.main.exit"));
                return;
            }
            try {
                event.setCancelled(true);
                MoneyWithdrawInt = Integer.parseInt(event.getMessage());
                double money = Economy.getMoneyExact(player.getName()).doubleValue();

                if (money < MoneyWithdrawInt) {
                    player.sendMessage(Config.getString("Messages.errors.unenough-money"));
                    Utils.removePlayerFromMoneyWithdrawing(player);
                    return;
                }

                if (MoneyWithdrawInt <= Itemstack.getEmptySlots(player.getInventory())) {
                    Itemstack.giveVault(player, MoneyWithdrawInt);
                    Economy.setMoney(player.getName(), Economy.getMoneyExact(player.getName()).doubleValue() - MoneyWithdrawInt);
                    player.sendMessage(Config.getString("Messages.main.money-withdraw").replace("%amount%", String.valueOf(MoneyWithdrawInt)));
                    Utils.playsound(player, "withdraw");
                    Utils.removePlayerFromMoneyWithdrawing(player);
                    return;
                } else {
                    player.sendMessage(Config.getString("Messages.errors.full"));
                    Utils.removePlayerFromMoneyWithdrawing(player);
                    return;
                }
            } catch (NumberFormatException e) {
                event.setCancelled(true);
                player.sendMessage(Config.getString("Messages.errors.amount-error-number").replace("%amount%", event.getMessage()));
                Utils.removePlayerFromMoneyWithdrawing(player);
                return;
            } catch (UserDoesNotExistException e) {
                event.setCancelled(true);
                player.sendMessage(Config.getString("Messages.errors.error-player"));
                Utils.removePlayerFromMoneyWithdrawing(player);
                return;
            } catch (MaxMoneyException e) {
                event.setCancelled(true);
                player.sendMessage(Config.getString("Messages.errors.what"));
                Utils.removePlayerFromMoneyWithdrawing(player);
                return;
            } catch (NoLoanPermittedException e) {
                event.setCancelled(true);
                player.sendMessage(Config.getString("Messages.errors.unenough-money"));
                Utils.removePlayerFromMoneyWithdrawing(player);
                return;
            }
        }
    }
}
