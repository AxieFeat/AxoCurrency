package me.axiefeat.axolotlstudio.Utils;

import me.axiefeat.axolotlstudio.Main;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.bukkit.Bukkit.*;

public class Utils {

    static Main plugin = Main.INSTANCE;

    public static void copyFile(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }
    public static File getJarPath() {
        return new java.io.File(Main.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath())
                ;
    }
    public static void runCmd (String cmd) {
        getServer().getScheduler().callSyncMethod(plugin, () -> {
            dispatchCommand(getConsoleSender(), cmd);
            return null;
        });
    }

    public static Set<UUID> BankPlayerDepositing = new HashSet<>();

    public static Set<UUID> BankPlayerWithdrawing = new HashSet<>();

    public static void addPlayerToBankDeposit(Player p) {
        BankPlayerDepositing.add(p.getUniqueId());
    }

    public static void addPlayerToBankWithdraw(Player p) {
        BankPlayerWithdrawing.add(p.getUniqueId());
    }

    public static void removePlayerFromBankDepositing(Player p) {
        BankPlayerDepositing.remove(p.getUniqueId());
    }

    public static void removePlayerFromBankWithdrawing(Player p) {
        BankPlayerWithdrawing.remove(p.getUniqueId());
    }


    public static Set<UUID> MoneyPlayerDepositing = new HashSet<>();

    public static Set<UUID> MoneyPlayerWithdrawing = new HashSet<>();

    public static void addPlayerToMoneyDeposit(Player p) {
        MoneyPlayerDepositing.add(p.getUniqueId());
    }

    public static void addPlayerToMoneyWithdraw(Player p) {
        MoneyPlayerWithdrawing.add(p.getUniqueId());
    }

    public static void removePlayerFromMoneyDepositing(Player p) {
        MoneyPlayerDepositing.remove(p.getUniqueId());
    }

    public static void removePlayerFromMoneyWithdrawing(Player p) {
        MoneyPlayerWithdrawing.remove(p.getUniqueId());
    }


    public static String formatTime(long milliseconds) {
        if (!Main.INSTANCE.getConfig().getBoolean("Interest.Enabled")) return Chat.color("&cПроценты отключены.");

        long seconds = milliseconds / 1000;
        if (seconds <= 0) return placeSeconds(Main.INSTANCE.getConfig().getString("Interest.Time.Interest-Time.Only-Seconds"), 0);
        if (seconds < 60) return placeSeconds(Main.INSTANCE.getConfig().getString("Interest.Time.Interest-Time.Only-Seconds"), seconds);

        long minutes = seconds / 60;
        long newSeconds = seconds - (60 * minutes);
        if (seconds < 3600) {
            if (seconds % 60 == 0) return placeMinutes(Main.INSTANCE.getConfig().getString("Interest.Time.Interest-Time.Only-Minutes"), minutes);

            String secondsPlaced = placeSeconds(Main.INSTANCE.getConfig().getString("Interest.Time.Interest-Time.Seconds-Minutes"), newSeconds);
            return placeMinutes(secondsPlaced, minutes);
        }

        long hours = seconds / 3600;
        long newMinutes = minutes - (60 * hours);
        if (seconds < 86400) {
            if (newSeconds == 0 && newMinutes == 0)
                return placeHours(Main.INSTANCE.getConfig().getString("Interest.Time.Interest-Time.Only-Hours"), hours);
            if (newSeconds == 0) {
                String minutesPlaced = placeMinutes(Main.INSTANCE.getConfig().getString("Interest.Time.Interest-Time.Minutes-Hours"), newMinutes);
                return placeHours(minutesPlaced, hours);
            }
            if (newMinutes == 0) {
                String secondsPlaced = placeSeconds(Main.INSTANCE.getConfig().getString("Interest.Time.Interest-Time.Seconds-Hours"), newSeconds);
                return placeHours(secondsPlaced, hours);
            }

            String secondsPlaced = placeSeconds(Main.INSTANCE.getConfig().getString("Interest.Time.Interest-Time.Seconds-Minutes-Hours"), newSeconds);
            String minutesPlaced = placeMinutes(secondsPlaced, newMinutes);
            return placeHours(minutesPlaced, hours);
        }

        long days = seconds / 86400;
        long newHours = hours - (24 * days);
        if (newSeconds == 0 && newMinutes == 0 && newHours == 0)
            return placeDays(Main.INSTANCE.getConfig().getString("Interest.Time.Interest-Time.Only-Days"), days);
        if (newSeconds == 0 && newMinutes == 0) {
            String hoursPlaced = placeHours(Main.INSTANCE.getConfig().getString("Interest.Time.Interest-Time.Hours-Days"), newHours);
            return placeDays(hoursPlaced, days);
        }
        if (newSeconds == 0 && newHours == 0) {
            String minutesPlaced = placeMinutes(Main.INSTANCE.getConfig().getString("Interest.Time.Interest-Time.Minutes-Days"), newMinutes);
            return placeDays(minutesPlaced, days);
        }
        if (newMinutes == 0 && newHours == 0) {
            String secondsPlaced = placeMinutes(Main.INSTANCE.getConfig().getString("Interest.Time.Interest-Time.Seconds-Days"), newSeconds);
            return placeDays(secondsPlaced, days);
        }

        if (newSeconds == 0) {
            String minutesPlaced = placeMinutes(Main.INSTANCE.getConfig().getString("Interest.Time.Interest-Time.Minutes-Hours-Days"), newMinutes);
            String hoursPlaced = placeHours(minutesPlaced, newHours);
            return placeDays(hoursPlaced, days);
        }
        if (newMinutes == 0) {
            String secondsPlaced = placeSeconds(Main.INSTANCE.getConfig().getString("Interest.Time.Interest-Time.Seconds-Hours-Days"), newSeconds);
            String hoursPlaced = placeHours(secondsPlaced, newHours);
            return placeDays(hoursPlaced, days);
        }
        if (newHours == 0) {
            String secondsPlaced = placeSeconds(Main.INSTANCE.getConfig().getString("Interest.Time.Interest-Time.Seconds-Minutes-Days"), newSeconds);
            String minutesPlaced = placeMinutes(secondsPlaced, newMinutes);
            return placeDays(minutesPlaced, days);
        }

        String secondsPlaced = placeSeconds(Main.INSTANCE.getConfig().getString("Interest.Time.Interest-Time.Seconds-Minutes-Hours-Days"), newSeconds);
        String minutesPlaced = placeMinutes(secondsPlaced, newMinutes);
        String hoursPlaced = placeHours(minutesPlaced, newHours);
        return placeDays(hoursPlaced, days);
    }

    private static String placeSeconds(String message, long seconds) {
        String time = message.replace("%seconds%", String.valueOf(seconds));
        if (seconds == 1) return time.replace("%seconds_placeholder%", Main.INSTANCE.getConfig().getString("Interest.Time.Second"));
        else return time.replace("%seconds_placeholder%", Main.INSTANCE.getConfig().getString("Interest.Time.Seconds"));
    }

    private static String placeMinutes(String message, long minutes) {
        String time = message.replace("%minutes%", String.valueOf(minutes));
        if (minutes == 1) return time.replace("%minutes_placeholder%", Main.INSTANCE.getConfig().getString("Interest.Time.Minute"));
        else return time.replace("%minutes_placeholder%", Main.INSTANCE.getConfig().getString("Interest.Time.Minutes"));
    }

    private static String placeHours(String message, long hours) {
        String time = message.replace("%hours%", String.valueOf(hours));
        if (hours == 1) return time.replace("%hours_placeholder%", Main.INSTANCE.getConfig().getString("Interest.Time.Hour"));
        else return time.replace("%hours_placeholder%", Main.INSTANCE.getConfig().getString("Interest.Time.Hours"));
    }

    private static String placeDays(String message, long days) {
        String time = message.replace("%days%", String.valueOf(days));
        if (days == 1) return time.replace("%days_placeholder%", Main.INSTANCE.getConfig().getString("Interest.Time.Day"));
        else return time.replace("%days_placeholder%", Main.INSTANCE.getConfig().getString("Interest.Time.Days"));
    }

    public static void playsound(Player player, String type) {
        if (type == null) {
            Logger.error("Ошибка в классе Utils! Неверный тип задачи. Доступные: withdraw, deposit (Вызвано null)");
            return;
        }
        if (!Main.INSTANCE.getConfig().getString("General." + type + ".sound").equalsIgnoreCase("none")) {
            player.playSound(player.getLocation(), Sound.valueOf(Main.INSTANCE.getConfig().getString("General." + type + ".sound")), 500.0f, 1.0f);
        }
    }
}
