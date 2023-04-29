package me.axiefeat.axolotlstudio.Commands;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import me.axiefeat.axolotlstudio.Itemstack;
import me.axiefeat.axolotlstudio.Main;
import me.axiefeat.axolotlstudio.Taxes;
import me.axiefeat.axolotlstudio.Utils.Config;
import me.axiefeat.axolotlstudio.Utils.Utils;
import net.ess3.api.MaxMoneyException;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class BankCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player playerone = Bukkit.getPlayer(sender.getName());
        File filed2 = new File(Main.INSTANCE.getDataFolder() + File.separator + "playerdata", playerone.getUniqueId() + ".yml");
        FileConfiguration cfgg = YamlConfiguration.loadConfiguration(filed2);
        if (!(sender instanceof Player)) {
            sender.sendMessage(Config.getString("Messages.errors.not-player"));
            return true;
        }
        if (args.length == 0) {
            if (!sender.hasPermission("axocurrency.help")) {
                sender.sendMessage(Config.getString("Messages.errors.perm").replace("%permission%", "axocurrency.help"));
                return true;
            }
            //ArrayList<String> list = (ArrayList<String>) new ArrayList<>(Main.INSTANCE.getConfig().getList("Messages.main.player-help"));
            sender.sendMessage(Config.getString("Messages.main.player-help"));
            return true;
        }
        if (args[0].equalsIgnoreCase("deposit")) {
            if (sender.hasPermission("axocurrency.bank.withdraw")) {
                if (!(args.length == 2)) {
                    sender.sendMessage(Config.getString("Messages.usages.bank-deposit-usage").replace("%command%", command.getName()));
                    return true;
                }
                try {
                    int amount = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    String amount = args[1];
                    sender.sendMessage(Config.getString("Messages.errors.amount-error-number").replace("%amount%", amount));
                    return true;
                }
                int amount = Integer.parseInt(args[1]);
                if (amount > Main.INSTANCE.getConfig().getInt("Max-Money")) {
                    sender.sendMessage(Config.getString("Messages.errors.max-limit-error"));
                    return true;
                }
                if (cfgg.getInt("Money") > Main.INSTANCE.getConfig().getInt("Max-Money")) {
                    sender.sendMessage(Config.getString("Messages.errors.max-limit-error"));
                    return true;
                }
                if ((amount + cfgg.getInt("Money")) > Main.INSTANCE.getConfig().getInt("Max-Money")) {
                    sender.sendMessage(Config.getString("Messages.errors.max-limit-error"));
                    return true;
                }
                try {
                    if (String.valueOf(amount).startsWith("-")) {
                        sender.sendMessage(Config.getString("Messages.errors.minus-amount"));
                        return true;
                    }
                    try {
                        double PlayerMoney = Economy.getMoneyExact(sender.getName()).doubleValue();
                        if (amount > PlayerMoney) {
                            sender.sendMessage(Config.getString("Messages.errors.unenough-money"));
                            return true;
                        }
                    } catch (UserDoesNotExistException e) {
                        sender.sendMessage(Config.getString("Messages.errors.what"));
                        return true;
                    }
                    Taxes.byTaxesDeposit(playerone, amount);
                    return true;
                } catch (NumberFormatException e) {
                    String amount2 = args[2];
                    sender.sendMessage(Config.getString("Messages.errors.amount-error-number").replace("%amount%", amount2));
                    return true;
                }
            } else {
                sender.sendMessage(Config.getString("Messages.errors.perm").replace("%permission%", "axocurrency.bank.withdraw"));
                return true;
            }
        } else if (args[0].equalsIgnoreCase("withdraw")) {
            if (sender.hasPermission("axocurrency.bank.withdraw")) {
                if (!(args.length == 2)) {
                    sender.sendMessage(Config.getString("Messages.usages.bank-withdraw-usage").replace("%command%", command.getName()));
                    return true;
                }
                try {
                    int amount = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    String amount = args[1];
                    sender.sendMessage(Config.getString("Messages.errors.amount-error-number").replace("%amount%", amount));
                    return true;
                }
                try {
                    int amount = Integer.parseInt(args[1]);
                    if (String.valueOf(amount).startsWith("-")) {
                        sender.sendMessage(Config.getString("Messages.errors.minus-amount"));
                        return true;
                    }
                    if (cfgg.getInt("Money") < amount) {
                        sender.sendMessage(Config.getString("Messages.errors.min-limit-error"));
                        return true;
                    }
                    double PlayerMoney = cfgg.getInt("Money");
                    if (amount > PlayerMoney) {
                        sender.sendMessage(Config.getString("Messages.errors.min-limit-error"));
                        return true;
                    }
                    Taxes.byTaxesWithdraw(playerone, amount);
                    return true;
                } catch (NumberFormatException e) {
                    String amount = args[2];
                    sender.sendMessage(Config.getString("Messages.errors.amount-error-number").replace("%amount%", amount));
                    return true;
                }
            } else {
                sender.sendMessage(Config.getString("Messages.errors.perm").replace("%permission%", "axocurrency.bank.withdraw"));
                return true;
            }
        } else if (args[0].equalsIgnoreCase("pay")) {
            File file1 = new File(Main.INSTANCE.getDataFolder() + File.separator + "playerdata", ((Player) sender).getPlayer().getUniqueId() + ".yml");
            FileConfiguration send = YamlConfiguration.loadConfiguration(file1);

            if (!(args.length == 3)) {
                sender.sendMessage(Config.getString("Messages.usages.bank-pay-usage").replace("%command%", command.getName()));
                return true;
            }

            if (!sender.hasPermission("axocurrency.bank.pay")) {
                sender.sendMessage(Config.getString("Messages.errors.perm").replace("%permission%", "axocurrency.bank.pay"));
                return true;
            }
            Player players = Bukkit.getPlayer(args[1]);
            if (args.length < 3) {
                sender.sendMessage(Config.getString("Messages.usages.bank-pay-usage").replace("%command%", command.getName()));
                return true;
            }
            if (players == null) {
                sender.sendMessage(Config.getString("Messages.errors.error-player"));
                return true;
            }
            if (players == sender) {
                sender.sendMessage(Config.getString("Messages.errors.myself"));
                return true;
            }
            File file2 = new File(Main.INSTANCE.getDataFolder() + File.separator + "playerdata", players.getUniqueId() + ".yml");
            FileConfiguration p = YamlConfiguration.loadConfiguration(file2);
            try {
                int i = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                sender.sendMessage(Config.getString("Messages.errors.amount-error-number").replace("%amount%", args[2]));
                return true;
            }
            int i = Integer.parseInt(args[2]);
            if (String.valueOf(i).startsWith("-")) {
                sender.sendMessage(Config.getString("Messages.errors.minus-amount"));
                return true;
            }
            if (p.getInt("Money") > Main.INSTANCE.getConfig().getInt("Max-Money")) {
                sender.sendMessage(Config.getString("Messages.errors.max-money-other").replace("%player%", players.getName()));
                return true;
            }
            if ((p.getInt("Money") + i) > Main.INSTANCE.getConfig().getInt("Max-Money")) {
                sender.sendMessage(Config.getString("Messages.errors.max-money-other").replace("%player%", players.getName()));
                return true;
            }
            if (send.getInt("Money") < i) {
                sender.sendMessage(Config.getString("Messages.errors.min-limit-error"));
                return true;
            }
            try {
                send.set("Money", send.getInt("Money") - i);
                send.save(file1);
            } catch (IOException e) {
                return true;
            }
            try {
                p.set("Money", p.getInt("Money") + i);
                p.save(file2);
            } catch (IOException e) {
                return true;
            }
            sender.sendMessage(Config.getString("Messages.main.bank-pay").replace("%player%", players.getName()).replace("%amount%", String.valueOf(i)));
            players.sendMessage(Config.getString("Messages.main.bank-pay-receive").replace("%player%", sender.getName()).replace("%amount%", String.valueOf(i)));
            return true;
        } else if (args[0].equalsIgnoreCase("money")) {
            if (args.length == 1) {
                if (!sender.hasPermission("axocurrency.bank.money")) {
                    sender.sendMessage(Config.getString("Messages.errors.perm").replace("%permission%", "axocurrency.bank.money"));
                    return true;
                }
                File filessdwaq = new File(Main.INSTANCE.getDataFolder() + File.separator + "playerdata", ((Player) sender).getPlayer().getUniqueId() + ".yml");
                FileConfiguration cfgssdwa = YamlConfiguration.loadConfiguration(filessdwaq);
                String sjdeq = String.valueOf(cfgssdwa.getInt("Money"));
                sender.sendMessage(Config.getString("Messages.main.bank-money").replace("%amount%", sjdeq));
                return true;
            }
            else if (args.length == 2) {
                if (args[1].equalsIgnoreCase("withdraw")) {
                    if (!sender.hasPermission("axocurrency.bank.money.withdraw")) {
                        sender.sendMessage(Config.getString("Messages.errors.perm").replace("%permission%", "axocurrency.bank.money.withdraw"));
                        return true;
                    }
                    Player p = (Player) sender;
                    if (Utils.BankPlayerWithdrawing.contains(p.getUniqueId()) | Utils.BankPlayerDepositing.contains(p.getUniqueId()) | Utils.MoneyPlayerDepositing.contains(p.getUniqueId()) | Utils.MoneyPlayerWithdrawing.contains(p.getUniqueId())) {
                        sender.sendMessage(Config.getString("Messages.errors.already"));
                        return true;
                    }
                    sender.sendMessage(Config.getString("Messages.main.chat"));
                    Utils.addPlayerToBankWithdraw((Player) sender);

                    TimerTask task = new TimerTask() {
                        public void run() {
                            if (Utils.BankPlayerWithdrawing.contains(playerone.getUniqueId())) {
                                Utils.removePlayerFromBankWithdrawing((Player) sender);
                                sender.sendMessage(Config.getString("Messages.main.exit"));
                            }
                        }
                    };
                    Timer timer = new Timer("Timer");
                    long delay = Main.INSTANCE.getConfig().getLong("General.withdraw.time") * 1000;
                    timer.schedule (task, delay);
                    return true;
                }
                else if (args[1].equalsIgnoreCase("deposit")) {
                    if (!sender.hasPermission("axocurrency.bank.money.deposit")) {
                        sender.sendMessage(Config.getString("Messages.errors.perm").replace("%permission%", "axocurrency.bank.money.deposit"));
                        return true;
                    }
                    Player p = (Player) sender;
                    if (Utils.BankPlayerWithdrawing.contains(p.getUniqueId()) | Utils.BankPlayerDepositing.contains(p.getUniqueId()) | Utils.MoneyPlayerDepositing.contains(p.getUniqueId()) | Utils.MoneyPlayerWithdrawing.contains(p.getUniqueId())) {
                        sender.sendMessage(Config.getString("Messages.errors.already"));
                        return true;
                    }
                    sender.sendMessage(Config.getString("Messages.main.chat"));
                    Utils.addPlayerToBankDeposit((Player) sender);

                    TimerTask task2 = new TimerTask() {
                        public void run() {
                            if (Utils.BankPlayerDepositing.contains(playerone.getUniqueId())) {
                                Utils.removePlayerFromBankDepositing((Player) sender);
                                sender.sendMessage(Config.getString("Messages.main.exit"));
                            }
                        }
                    };
                    Timer timer2 = new Timer("Timerr");
                    long delay2 = Main.INSTANCE.getConfig().getLong("General.deposit.time") * 1000;
                    timer2.schedule (task2, delay2);
                    return true;
                } else {
                    sender.sendMessage(Config.getString("Messages.usages.bank-money-usage").replace("%command%", command.getName()));
                    return true;
                }
            } else {
                sender.sendMessage(Config.getString("Messages.usages.bank-money-usage").replace("%command%", command.getName()));
                return true;
            }
        } else if (args[0].equalsIgnoreCase("admin")) {
            if (args.length == 1) {
                if (sender.hasPermission("axocurrency.admin.help")) {
                    sender.sendMessage(Config.getString("Messages.admin.admin-help"));
                    return true;
                } else {
                    sender.sendMessage(Config.getString("Messages.errors.perm").replace("%permission%", "axocurrency.admin.help"));
                    return true;
                }
            }
            if (args[1].equalsIgnoreCase("money")) {
                if (!sender.hasPermission("axocurrency.admin.bank.money")) {
                    sender.sendMessage(Config.getString("Messages.errors.perm").replace("%permission%", "axocurrency.admin.bank.money"));
                    return true;
                }
                if (args.length != 3) {
                    sender.sendMessage(Config.getString("Messages.admin.usages.bank-money-usage").replace("%command%", command.getName()));
                    return true;
                }
                Player player = Bukkit.getPlayer(args[2]);
                if (player == null) {
                    sender.sendMessage(Config.getString("Messages.errors.error-player"));
                    return true;
                }
                File file = new File(Main.INSTANCE.getDataFolder() + File.separator + "playerdata", player.getUniqueId() + ".yml");
                FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                sender.sendMessage(Config.getString("Messages.admin.bank-money").replace("%player%", player.getName())
                        .replace("%amount%", cfg.getString("Money")));
                return true;
            } else if (args[1].equalsIgnoreCase("give")) {
                if (!sender.hasPermission("axocurrency.admin.money.give")) {
                    sender.sendMessage(Config.getString("Messages.errors.perm").replace("%permission%", "axocurrency.admin.money.give"));
                    return true;
                }
                if (args.length != 4) {
                    sender.sendMessage(Config.getString("Messages.admin.usages.bank-money-give").replace("%command%", command.getName()));
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
                int i = Integer.parseInt(args[3]);
                File file = new File(Main.INSTANCE.getDataFolder() + File.separator + "playerdata", player.getUniqueId() + ".yml");
                FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

                cfg.set("Money", cfg.getInt("Money") + i);
                try {
                    cfg.save(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                sender.sendMessage(Config.getString("Messages.admin.bank-give").replace("%player%", player.getName()).replace("%amount%", String.valueOf(i)));
                return true;
            } else if (args[1].equalsIgnoreCase("take")) {
                if (!sender.hasPermission("axocurrency.admin.bank.take")) {
                    sender.sendMessage(Config.getString("Messages.errors.perm").replace("%permission%", "axocurrency.admin.bank.take"));
                    return true;
                }
                if (args.length != 4) {
                    sender.sendMessage(Config.getString("Messages.admin.usages.bank-money-take").replace("%command%", command.getName()));
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
                int i = Integer.parseInt(args[3]);
                File file = new File(Main.INSTANCE.getDataFolder() + File.separator + "playerdata", player.getUniqueId() + ".yml");
                FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                if (i > cfg.getInt("Money")) {
                    sender.sendMessage(Config.getString("Messages.admin.bank-take-error").replace("%player%", player.getName()));
                    return true;
                }
                cfg.set("Money", cfg.getInt("Money") - i);
                try {
                    cfg.save(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                sender.sendMessage(Config.getString("Messages.admin.bank-take").replace("%player%", player.getName()).replace("%amount%", String.valueOf(i)));
                return true;
            } else if (args[1].equalsIgnoreCase("set")) {
                if (!sender.hasPermission("axocurrency.admin.bank.set")) {
                    sender.sendMessage(Config.getString("Messages.errors.perm").replace("%permission%", "axocurrency.admin.bank.set"));
                    return true;
                }
                if (args.length != 4) {
                    sender.sendMessage(Config.getString("Messages.admin.usages.bank-money-set").replace("%command%", command.getName()));
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
                int i = Integer.parseInt(args[3]);
                File file = new File(Main.INSTANCE.getDataFolder() + File.separator + "playerdata", player.getUniqueId() + ".yml");
                FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                cfg.set("Money", i);
                try {
                    cfg.save(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                sender.sendMessage(Config.getString("Messages.admin.bank-set").replace("%player%", player.getName()).replace("%amount%", String.valueOf(i)));
                return true;
            } else if (args[1].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("axocurrency.admin.help")) {
                    sender.sendMessage(Config.getString("Messages.admin.reload"));
                    Main.INSTANCE.reloadConfig();
                    return true;
                } else {
                    sender.sendMessage(Config.getString("Messages.errors.perm").replace("%permission%", "axocurrency.admin.reload"));
                    return true;
                }
            } else if (args[1].equalsIgnoreCase("help")) {
                if (sender.hasPermission("axocurrency.admin.help")) {
                    sender.sendMessage(Config.getString("Messages.admin.admin-help"));
                    return true;
                } else {
                    sender.sendMessage(Config.getString("Messages.errors.perm").replace("%permission%", "axocurrency.admin.help"));
                    return true;
                }
            } else {
                sender.sendMessage(Config.getString("Messages.admin.usages.unknown-command").replace("%command%", command.getName()));
                return true;
            }
        } else if (args[0].equalsIgnoreCase("help")) {
            if (!sender.hasPermission("axocurrency.help")) {
                sender.sendMessage(Config.getString("Messages.errors.perm").replace("%permission%", "axocurrency.help"));
                return true;
            }
            //ArrayList<String> list = (ArrayList<String>) new ArrayList<>(Main.INSTANCE.getConfig().getList("Messages.main.player-help"));
            sender.sendMessage(Config.getString("Messages.main.player-help"));
            return true;
        } else {
            sender.sendMessage(Config.getString("Messages.errors.unknown-command").replace("%command%", command.getName()));
            return true;
        }
    }
}
