package me.axiefeat.axolotlstudio.EventListener;

import me.axiefeat.axolotlstudio.Utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    @EventHandler
    public void PlayerQuit(PlayerQuitEvent event ) {
        Player player = event.getPlayer();
        if (Utils.BankPlayerDepositing.contains(player.getUniqueId())) {
            Utils.removePlayerFromBankDepositing(player);
        }
        if (Utils.BankPlayerWithdrawing.contains(player.getUniqueId())) {
            Utils.removePlayerFromBankWithdrawing(player);
        }
        if (Utils.MoneyPlayerDepositing.contains(player.getUniqueId())) {
            Utils.removePlayerFromMoneyDepositing(player);
        }
        if (Utils.MoneyPlayerWithdrawing.contains(player.getUniqueId())) {
            Utils.removePlayerFromMoneyWithdrawing(player);
        }
    }
}
