package me.axiefeat.axolotlstudio.Commands;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import me.axiefeat.axolotlstudio.Itemstack;
import me.axiefeat.axolotlstudio.Main;
import me.axiefeat.axolotlstudio.Utils.Config;
import me.axiefeat.axolotlstudio.Utils.Utils;
import net.ess3.api.MaxMoneyException;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.TimerTask;

public class MoneyCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Config.getString("Messages.errors.not-player"));
            return true;
        }
        if (args.length == 0) {
            try {
                int money = Economy.getMoneyExact(sender.getName()).intValue();
                if (!sender.hasPermission("axocurrency.money")) {
                    sender.sendMessage(Config.getString("Messages.errors.perm").replace("%permission%", "axocurrency.money"));
                    return true;
                }
                sender.sendMessage(Config.getString("Messages.main.money").replace("%amount%", String.valueOf(money)));
                return true;
            } catch (UserDoesNotExistException e) {
                return true;
            }
        }
        if (args[0].equalsIgnoreCase("withdraw")) {
            if (!sender.hasPermission("axocurrency.money.withdraw")) {
                sender.sendMessage(Config.getString("Messages.errors.perm").replace("%permission%", "axocurrency.money.withdraw"));
                return true;
            }
            Player p = (Player) sender;
            if (Utils.BankPlayerWithdrawing.contains(p.getUniqueId()) | Utils.BankPlayerDepositing.contains(p.getUniqueId()) | Utils.MoneyPlayerWithdrawing.contains(p.getUniqueId()) | Utils.MoneyPlayerDepositing.contains(p.getUniqueId())) {
                sender.sendMessage(Config.getString("Messages.errors.already"));
                return true;
            }
            sender.sendMessage(Config.getString("Messages.main.chat"));
            Utils.addPlayerToMoneyWithdraw((Player) sender);

            TimerTask task3 = new TimerTask() {
                public void run() {
                    if (Utils.MoneyPlayerWithdrawing.contains(p.getUniqueId())) {
                        Utils.removePlayerFromMoneyWithdrawing((Player) sender);
                        sender.sendMessage(Config.getString("Messages.main.exit"));
                    }
                }
            };
            Timer timer = new Timer("Timer3");
            long delay = Main.INSTANCE.getConfig().getLong("General.withdraw.time") * 1000;
            timer.schedule(task3, delay);
            return true;
        }
        if (args[0].equalsIgnoreCase("deposit")) {
            if (!sender.hasPermission("axocurrency.money.deposit")) {
                sender.sendMessage(Config.getString("Messages.errors.perm").replace("%permission%", "axocurrency.money.deposit"));
                return true;
            }
            Player p2 = (Player) sender;
            if (Utils.BankPlayerWithdrawing.contains(p2.getUniqueId()) | Utils.BankPlayerDepositing.contains(p2.getUniqueId()) | Utils.MoneyPlayerDepositing.contains(p2.getUniqueId()) | Utils.MoneyPlayerWithdrawing.contains(p2.getUniqueId())) {
                sender.sendMessage(Config.getString("Messages.errors.already"));
                return true;
            }
            sender.sendMessage(Config.getString("Messages.main.chat"));
            Utils.addPlayerToMoneyDeposit((Player) sender);

            TimerTask task22 = new TimerTask() {
                public void run() {
                    if (Utils.MoneyPlayerDepositing.contains(p2.getUniqueId())) {
                        Utils.removePlayerFromMoneyDepositing((Player) sender);
                        sender.sendMessage(Config.getString("Messages.main.exit"));
                    }
                }
            };
            Timer timer2 = new Timer("Timerr2");
            long delay2 = Main.INSTANCE.getConfig().getLong("General.deposit.time") * 1000;
            timer2.schedule(task22, delay2);
            return true;
        }
        if (args[0].equalsIgnoreCase("admin")) {
            if (args[1].equalsIgnoreCase("view")) {
                if (!sender.hasPermission("axocurrency.admin.money.view")) {
                    sender.sendMessage(Config.getString("Messages.errors.perm").replace("%permission%", "axocurrency.admin.money.view"));
                    return true;
                }
                if (args.length != 3) {
                    sender.sendMessage(Config.getString("Messages.admin.usages.money-usage").replace("%command%", command.getName()));
                    return true;
                }
                Player player = Bukkit.getPlayer(args[2]);
                if (player == null) {
                    sender.sendMessage(Config.getString("Messages.errors.error-player"));
                    return true;
                }
                try {
                    sender.sendMessage(Config.getString("Messages.admin.money").replace("%player%", player.getName())
                            .replace("%amount%", String.valueOf(Economy.getMoneyExact(player.getName()).intValue())));
                    return true;
                } catch (UserDoesNotExistException e) {
                    return true;
                }
            } else if (args[1].equalsIgnoreCase("give")) {
                if (!sender.hasPermission("axocurrency.admin.money.give")) {
                    sender.sendMessage(Config.getString("Messages.errors.perm").replace("%permission%", "axocurrency.admin.money.give"));
                    return true;
                }
                if (args.length != 4) {
                    sender.sendMessage(Config.getString("Messages.admin.usages.money-give").replace("%command%", command.getName()));
                    return true;
                }
                Player player = Bukkit.getPlayer(args[2]);
                if (player == null) {
                    sender.sendMessage(Config.getString("Messages.errors.error-player"));
                    return true;
                }
                try {
                    int i = Integer.parseInt(args[3]);
                    if (args[3].startsWith("-")) {
                        sender.sendMessage(Config.getString("Messages.errors.minus-amount"));
                        return true;
                    }
                } catch (NumberFormatException e) {
                    sender.sendMessage(Config.getString("Messages.errors.amount-error-number").replace("%amount%", args[3]));
                    return true;
                }
                try {
                    int i = Integer.parseInt(args[3]);
                    Economy.setMoney(player.getName(), Economy.getMoneyExact(player.getName()).intValue() + i);
                    sender.sendMessage(Config.getString("Messages.admin.money-give").replace("%player%", player.getName())
                            .replace("%amount%", args[3]));
                    return true;
                } catch (UserDoesNotExistException e) {
                    return true;
                } catch (MaxMoneyException e) {
                    return true;
                } catch (NoLoanPermittedException e) {
                    return true;
                }
            } else if (args[1].equalsIgnoreCase("take")) {
                if (!sender.hasPermission("axocurrency.admin.money.take")) {
                    sender.sendMessage(Config.getString("Messages.errors.perm").replace("%permission%", "axocurrency.admin.money.take"));
                    return true;
                }
                if (args.length != 4) {
                    sender.sendMessage(Config.getString("Messages.admin.usages.money-take").replace("%command%", command.getName()));
                    return true;
                }
                Player player = Bukkit.getPlayer(args[2]);
                if (player == null) {
                    sender.sendMessage(Config.getString("Messages.errors.error-player"));
                    return true;
                }
                try {
                    int i = Integer.parseInt(args[3]);
                    if (args[3].startsWith("-")) {
                        sender.sendMessage(Config.getString("Messages.errors.minus-amount"));
                        return true;
                    }
                } catch (NumberFormatException e) {
                    sender.sendMessage(Config.getString("Messages.errors.amount-error-number").replace("%amount%", args[3]));
                    return true;
                }
                try {
                    int i = Integer.parseInt(args[3]);
                    Economy.setMoney(player.getName(), Economy.getMoneyExact(player.getName()).intValue() - i);
                    sender.sendMessage(Config.getString("Messages.admin.money-take").replace("%player%", player.getName())
                            .replace("%amount%", args[3]));
                    return true;
                } catch (UserDoesNotExistException e) {
                    return true;
                } catch (MaxMoneyException | NoLoanPermittedException e) {
                    sender.sendMessage(Config.getString("Messages.admin.money-take-error").replace("%player%", player.getName()));
                    return true;
                }
            } else if (args[1].equalsIgnoreCase("set")) {
                if (!sender.hasPermission("axocurrency.admin.money.set")) {
                    sender.sendMessage(Config.getString("Messages.errors.perm").replace("%permission%", "axocurrency.admin.money.set"));
                    return true;
                }
                if (args.length != 4) {
                    sender.sendMessage(Config.getString("Messages.admin.usages.money-take").replace("%command%", command.getName()));
                    return true;
                }
                Player player = Bukkit.getPlayer(args[2]);
                if (player == null) {
                    sender.sendMessage(Config.getString("Messages.errors.error-player"));
                    return true;
                }
                try {
                    int i = Integer.parseInt(args[3]);
                    if (args[3].startsWith("-")) {
                        sender.sendMessage(Config.getString("Messages.errors.minus-amount"));
                        return true;
                    }
                } catch (NumberFormatException e) {
                    sender.sendMessage(Config.getString("Messages.errors.amount-error-number").replace("%amount%", args[3]));
                    return true;
                }
                try {
                    int i = Integer.parseInt(args[3]);
                    Economy.setMoney(player.getName(), i);
                    sender.sendMessage(Config.getString("Messages.admin.money-set").replace("%player%", player.getName())
                            .replace("%amount%", args[3]));
                    return true;
                } catch (UserDoesNotExistException e) {
                    return true;
                } catch (MaxMoneyException | NoLoanPermittedException e) {
                    return true;
                }
            } else {
                if (!sender.hasPermission("axocurrency.admin.help")) {
                    sender.sendMessage(Config.getString("Messages.errors.perm").replace("%permission%", "axocurrency.admin.help"));
                    return true;
                }
                sender.sendMessage(Config.getString("Messages.admin.usages.unknown-command").replace("%command%", command.getName()));
                return true;
            }
        }
        return true;
    }
}
