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

public class Money implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            ArrayList <String> list = new ArrayList<>();
            if (sender.hasPermission("axocurrency.money.withdraw")) {
                list.add("withdraw");
            }
            if (sender.hasPermission("axocurrency.money.deposit")) {
                list.add("deposit");
            }
            if (sender.hasPermission("axocurrency.admin.money.give") | sender.hasPermission("axocurrency.admin.money.take")
                    | sender.hasPermission("axocurrency.admin.money.set")) {
                list.add("admin");
            }
            return list;
        }
        if (args.length == 2) {
            ArrayList<String> list = new ArrayList<>();
            if (args[0].equalsIgnoreCase("withdraw") | args[0].equalsIgnoreCase("deposit")) {
                return new ArrayList<>();
            }
            if (sender.hasPermission("axocurrency.admin.money.give")) {
                list.add("give");
            }
            if (sender.hasPermission("axocurrency.admin.money.take")) {
                list.add("take");
            }
            if (sender.hasPermission("axocurrency.admin.money.set")) {
                list.add("set");
            }
            if (sender.hasPermission("axocurrency.admin.money.view")) {
                list.add("view");
            }
            return list;
        }
        if (args.length == 3) {
            ArrayList<String> list = new ArrayList<>();
            if (args[0].equalsIgnoreCase("withdraw") | args[0].equalsIgnoreCase("deposit")) {
                return new ArrayList<>();
            }
            if (sender.hasPermission("axocurrency.admin.money.give") | sender.hasPermission("axocurrency.admin.money.take")
                    | sender.hasPermission("axocurrency.admin.money.set") | sender.hasPermission("axocurrency.admin.money.view")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    list.add(p.getName());
                }
            }

            return list;
        }
        if (args.length == 4) {
            ArrayList<String> list = new ArrayList<>();
            if (args[0].equalsIgnoreCase("withdraw") | args[0].equalsIgnoreCase("deposit") | args[1].equalsIgnoreCase("view")) {
                return new ArrayList<>();
            }
            if (sender.hasPermission("axocurrency.admin.money.give") | sender.hasPermission("axocurrency.admin.money.take")
                    | sender.hasPermission("axocurrency.admin.money.set")) {
                list.add("100");
                list.add("1000");
                list.add("10000");
            }
            return list;
        }
        if (args.length > 4) {
            return new ArrayList<>();
        }
        return null;
    }
}
