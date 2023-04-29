package me.axiefeat.axolotlstudio.Commands;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import me.axiefeat.axolotlstudio.Utils.Config;
import net.ess3.api.MaxMoneyException;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PayCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Config.getString("Messages.errors.not-player"));
            return true;
        }
        if (!sender.hasPermission("axocurrency.pay")) {
            sender.sendMessage(Config.getString("Messages.errors.perm").replace("%permission%", "axocurrency.pay"));
            return true;
        }
        if (args.length < 2) {
            sender.sendMessage(Config.getString("Messages.usages.pay-usage").replace("%command%", cmd.getName()));
            return true;
        }
        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(Config.getString("Messages.errors.error-player"));
            return true;
        }
        if (player == sender) {
            sender.sendMessage(Config.getString("Messages.errors.myself"));
            return true;
        }
        try {
            int i = Integer.parseInt(args[1]);
            if (String.valueOf(i).startsWith("-")) {
                sender.sendMessage(Config.getString("Messages.errors.minus-amount"));
                return true;
            }
            Economy.setMoney(sender.getName(), Economy.getMoneyExact(sender.getName()).doubleValue() - i);
            Economy.setMoney(player.getName(), Economy.getMoneyExact(player.getName()).doubleValue() + i);
            player.sendMessage(Config.getString("Messages.main.pay-receive").replace("%player%", sender.getName()).replace("%amount%", String.valueOf(i)));
            sender.sendMessage(Config.getString("Messages.main.pay").replace("%player%", player.getName()).replace("%amount%", String.valueOf(i)));
            return true;
        } catch (NumberFormatException e) {
            sender.sendMessage(Config.getString("Messages.errors.amount-error-number").replace("%amount%", args[1]));
            return true;
        } catch (MaxMoneyException e) {
            sender.sendMessage(Config.getString("Messages.errors.what"));
            return true;
        } catch (UserDoesNotExistException e) {
            sender.sendMessage(Config.getString("Messages.errors.error-player"));
            return true;
        } catch (NoLoanPermittedException e) {
            sender.sendMessage(Config.getString("Messages.errors.unenough-money"));
            return true;
        }
    }
}
