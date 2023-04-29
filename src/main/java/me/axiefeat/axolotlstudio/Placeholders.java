package me.axiefeat.axolotlstudio;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.UserDoesNotExistException;
import me.axiefeat.axolotlstudio.Interest.Interest;
import me.axiefeat.axolotlstudio.Utils.Utils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;

import static me.axiefeat.axolotlstudio.Taxes.getGroup;

public class Placeholders extends PlaceholderExpansion {
    @Override
    public String getAuthor() {
        return "AxieFeat";
    }

    @Override
    public String getIdentifier() {
        return "axocurrency";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }
    @Override
    public String onPlaceholderRequest(Player p, @NotNull String identifier) {
        if (p == null) return "Игрок не найден!";
        if (identifier.startsWith("taxes_deposit")) {
            String group = Taxes.getGroup(p);
            return Main.INSTANCE.getConfig().getString("General.deposit.taxes." + group);
        } else if (identifier.startsWith("taxes_withdraw")) {
            String group = Taxes.getGroup(p);
            return Main.INSTANCE.getConfig().getString("General.withdraw.taxes." + group);
        } else if (identifier.startsWith("interest_time")) {
            long will = Interest.unix;
            return Utils.formatTime(will - System.currentTimeMillis());
        } else if (identifier.startsWith("interest_amount")) {
            File file = new File(Main.INSTANCE.getDataFolder() + File.separator + "playerdata", p.getUniqueId() + ".yml");
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            int i = cfg.getInt("Money");
            int percent = Main.INSTANCE.getConfig().getInt("Interest.Money-Percent");
            int result = (i / 100) * (percent);
            if (result > Main.INSTANCE.getConfig().getInt("Interest.Max-Amount")) {
                return Main.INSTANCE.getConfig().getString("Interest.Max-Amount");
            }
            if (i + result > Main.INSTANCE.getConfig().getInt("Max-Money")) {
                return "0";
            }
            return String.valueOf(result);
        } else if (identifier.startsWith("money")) {
            try {
                return String.valueOf(Economy.getMoneyExact(p.getName()).intValue());
            } catch (UserDoesNotExistException e) {
                throw new RuntimeException(e);
            }
        } else if (identifier.startsWith("bank_money")) {
            File file = new File(Main.INSTANCE.getDataFolder() + File.separator + "playerdata", p.getUniqueId() + ".yml");
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            return cfg.getString("Money");
        } else {
            return "Неизвестный плейсхолдер!";
        }
    }
}
