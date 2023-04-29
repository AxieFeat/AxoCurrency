package me.axiefeat.axolotlstudio;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.List;

public class Vault extends ItemStack {
    public static ItemStack vault (int count) {
        ItemStack vault = new ItemStack(Material.valueOf(Main.INSTANCE.getConfig().getString("Item.Material").toUpperCase()));
        ItemMeta meta = vault.getItemMeta();
        if (!Main.INSTANCE.getConfig().getString("Item.ItemName").equalsIgnoreCase("none")) {
            meta.setDisplayName(Main.INSTANCE.getConfig().getString("Item.ItemName"));
        }
        if (!Main.INSTANCE.getConfig().get("Item.Lore").toString().equalsIgnoreCase("none")) {
            meta.setLore((List<String>) Main.INSTANCE.getConfig().getList("Item.Lore"));
        }
        if (!Main.INSTANCE.getConfig().getString("Item.CustomModelData").equalsIgnoreCase("none")) {
            meta.setCustomModelData(Main.INSTANCE.getConfig().getInt("Item.CustomModelData"));
        }
        vault.setItemMeta(meta);
        vault.setAmount(count);

        return vault;
    }
}
