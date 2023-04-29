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
import java.io.IOException;

public class MoneyDepositEvent implements Listener {
    public static int MoneyDepositInt;
    @EventHandler
    public static void ChatWait(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (Utils.MoneyPlayerDepositing.contains(player.getUniqueId())) {
            if (event.getMessage().equalsIgnoreCase(Main.INSTANCE.getConfig().getString("General.deposit.exit"))) {
                event.setCancelled(true);
                Utils.removePlayerFromMoneyDepositing(player);
                player.sendMessage(Config.getString("Messages.main.exit"));
                return;
            }
            try {
                event.setCancelled(true);
                MoneyDepositInt = Integer.parseInt(event.getMessage());
                if (Itemstack.hasVault(player, MoneyDepositInt)) {
                    Itemstack.takeVault(player, MoneyDepositInt);
                    Economy.setMoney(player.getName(), Economy.getMoneyExact(player.getName()).doubleValue() + MoneyDepositInt);
                    Utils.removePlayerFromMoneyDepositing(player);
                    player.sendMessage(Config.getString("Messages.main.money-deposit").replace("%amount%", String.valueOf(MoneyDepositInt)));
                    Utils.playsound(player, "deposit");
                    return;
                } else {
                    Utils.removePlayerFromMoneyDepositing(player);
                    player.sendMessage(Config.getString("Messages.errors.unenough"));
                    return;
                }
            } catch (NumberFormatException e) {
                event.setCancelled(true);
                player.sendMessage(Config.getString("Messages.errors.amount-error-number").replace("%amount%", event.getMessage()));
                Utils.removePlayerFromMoneyDepositing(player);
                return;
            } catch (UserDoesNotExistException e) {
                event.setCancelled(true);
                player.sendMessage(Config.getString("Messages.errors.error-player"));
                Utils.removePlayerFromMoneyDepositing(player);
                return;
            } catch (MaxMoneyException e) {
                event.setCancelled(true);
                player.sendMessage(Config.getString("Messages.errors.what"));
                Utils.removePlayerFromMoneyDepositing(player);
                return;
            } catch (NoLoanPermittedException e) {
                event.setCancelled(true);
                player.sendMessage(Config.getString("Messages.errors.unenough-money"));
                Utils.removePlayerFromMoneyDepositing(player);
                return;
            }
        }
    }
}
