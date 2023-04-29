package me.axiefeat.axolotlstudio.Tab;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Bank implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            ArrayList<String> list = new ArrayList<>();
            if (sender.hasPermission("axocurrency.bank.withdraw")) {
                list.add("withdraw");
            }
            if (sender.hasPermission("axocurrency.bank.deposit")) {
                list.add("deposit");
            }
            if (sender.hasPermission("axocurrency.bank.pay")) {
                list.add("pay");
            }
            if (sender.hasPermission("axocurrency.bank.money.withdraw") | sender.hasPermission("axocurrency.bank.money.deposit")
                    | sender.hasPermission("axocurrency.bank.money")) {
                list.add("money");
            }
            if (sender.hasPermission("axocurrency.admin.reload") | sender.hasPermission("axocurrency.admin.help") |
                    sender.hasPermission("axocurrency.admin.bank.give") | sender.hasPermission("axocurrency.admin.bank.take") |
                    sender.hasPermission("axocurrency.admin.bank.set") | sender.hasPermission("axocurrency.admin.bank.money")) {
                list.add("admin");
            }
            return list;
        }
        if (args.length == 2) {
            ArrayList<String> list = new ArrayList<>();
            if (args[0].equalsIgnoreCase("withdraw") | args[0].equalsIgnoreCase("deposit")) {
                if (sender.hasPermission("axocurrency.bank.withdraw") | sender.hasPermission("axocurrency.bank.deposit")) {
                    return List.of(
                            "100", "1000", "10000"
                    );
                }
            }
            if (args[0].equalsIgnoreCase("money")) {
                return new ArrayList<>();
            }
            if (args[0].equalsIgnoreCase("pay")) {
                if (sender.hasPermission("axocurrency.bank.pay")) {
                    ArrayList<String> list2 = new ArrayList<>();
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        list2.add(p.getName());
                    }
                    return list2;
                }
            }
            if (sender.hasPermission("axocurrency.admin.reload")) {
                list.add("reload");
            }
            if (sender.hasPermission("axocurrency.admin.help")) {
                list.add("help");
            }
            if (sender.hasPermission("axocurrency.admin.bank.give")) {
                list.add("give");
            }
            if (sender.hasPermission("axocurrency.admin.bank.take")) {
                list.add("take");
            }
            if (sender.hasPermission("axocurrency.admin.bank.set")) {
                list.add("set");
            }
            if (sender.hasPermission("axocurrency.admin.bank.money")) {
                list.add("money");
            }
            return list;
        }
        if (args.length == 3) {
            ArrayList<String> list = new ArrayList<>();
            if (args[0].equalsIgnoreCase("pay")) {
                if (sender.hasPermission("axocurrency.bank.pay")) {
                    ArrayList<String> list2 = new ArrayList<>();
                    list2.add("100"); list2.add("1000"); list2.add("10000");
                    return list2;
                }
            }
            if (args[0].equalsIgnoreCase("withdraw") | args[0].equalsIgnoreCase("deposit") | args[0].equalsIgnoreCase("money")) {
                return new ArrayList<>();
            }
            if (args[1].equalsIgnoreCase("reload") | args[1].equalsIgnoreCase("help")) {
                return new ArrayList<>();
            }
            if (sender.hasPermission("axocurrency.admin.bank.give") | sender.hasPermission("axocurrency.admin.bank.take") |
                    sender.hasPermission("axocurrency.admin.bank.set") | sender.hasPermission("axocurrency.admin.bank.money")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    list.add(p.getName());
                }
            }
            return list;
        }
        if (args.length == 4) {
            if (args[0].equalsIgnoreCase("withdraw") | args[0].equalsIgnoreCase("deposit") |
                    args[0].equalsIgnoreCase("pay") | args[1].equalsIgnoreCase("money") |
                    args[1].equalsIgnoreCase("reload") | args[1].equalsIgnoreCase("help") | args[0].equalsIgnoreCase("money")) {
                return new ArrayList<>();
            }
            ArrayList<String> list = new ArrayList<>();
            if (sender.hasPermission("axocurrency.admin.bank.give") | sender.hasPermission("axocurrency.admin.bank.take") |
                    sender.hasPermission("axocurrency.admin.bank.set")) {
                list.add("100"); list.add("1000"); list.add("10000");
            }
            return list;
        }
        if (args.length > 4) {
            return new ArrayList<>();
        }
        return null;
    }
}
