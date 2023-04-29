package me.axiefeat.axolotlstudio;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Itemstack {

    public static int getEmptySlots(Inventory inventory) {
        int i = 0;
        for (ItemStack is : inventory.getContents()) {
            if (is == null)
                continue;
            i++;
        }
        return (36-i)*64;
    }

    public static void giveVault(Player player, int Count) {
//        ItemStack vault = new ItemStack(Material.valueOf(Main.INSTANCE.getConfig().getString("Item.Material").toUpperCase()));
//        ItemMeta meta = vault.getItemMeta();
//        if (!Main.INSTANCE.getConfig().getString("Item.ItemName").equalsIgnoreCase("none")) {
//            meta.setDisplayName(Main.INSTANCE.getConfig().getString("Item.ItemName"));
//        }
//        if (!Main.INSTANCE.getConfig().get("Item.Lore").toString().equalsIgnoreCase("none")) {
//            meta.setLore((List<String>) Main.INSTANCE.getConfig().getList("Item.Lore"));
//        }
//        if (!Main.INSTANCE.getConfig().getString("Item.CustomModelData").equalsIgnoreCase("none")) {
//            meta.setCustomModelData(Main.INSTANCE.getConfig().getInt("Item.CustomModelData"));
//        }
//        vault.setItemMeta(meta);
//        vault.setAmount(Count);
        player.getInventory().addItem(Vault.vault(Count));
    }

    public static boolean hasVault (Player player, int Count) {
//        ItemStack vault = new ItemStack(Material.valueOf(Main.INSTANCE.getConfig().getString("Item.Material").toUpperCase()));
//        ItemMeta meta = vault.getItemMeta();
//        if (!Main.INSTANCE.getConfig().getString("Item.ItemName").equalsIgnoreCase("none")) {
//            meta.setDisplayName(Main.INSTANCE.getConfig().getString("Item.ItemName"));
//        }
//        if (!Main.INSTANCE.getConfig().get("Item.Lore").toString().equalsIgnoreCase("none")) {
//            meta.setLore((List<String>) Main.INSTANCE.getConfig().getList("Item.Lore"));
//        }
//        if (!Main.INSTANCE.getConfig().getString("Item.CustomModelData").equalsIgnoreCase("none")) {
//            meta.setCustomModelData(Main.INSTANCE.getConfig().getInt("Item.CustomModelData"));
//        }
//        vault.setItemMeta(meta);
//        for (ItemStack item : player.getInventory().getContents()){
//            if (item.getType() == Material.valueOf(Main.INSTANCE.getConfig().getString("Item.Material").toUpperCase()) && item.getAmount() == Count) return true;
//        }
        return player.getInventory().contains(Material.valueOf(Main.INSTANCE.getConfig().getString("Item.Material").toUpperCase()), Count);
    }
    public static void takeVault(Player player, int Count) {
//        ItemStack vault = new ItemStack(Material.valueOf(Main.INSTANCE.getConfig().getString("Item.Material").toUpperCase()));
//        ItemMeta meta = vault.getItemMeta();
//        if (!Main.INSTANCE.getConfig().getString("Item.ItemName").equalsIgnoreCase("none")) {
//            meta.setDisplayName(Main.INSTANCE.getConfig().getString("Item.ItemName"));
//        }
//        if (!Main.INSTANCE.getConfig().get("Item.Lore").toString().equalsIgnoreCase("none")) {
//            meta.setLore((List<String>) Main.INSTANCE.getConfig().getList("Item.Lore"));
//        }
//        if (!Main.INSTANCE.getConfig().getString("Item.CustomModelData").equalsIgnoreCase("none")) {
//            meta.setCustomModelData(Main.INSTANCE.getConfig().getInt("Item.CustomModelData"));
//        }
//        vault.setItemMeta(meta);
//        vault.setAmount(Count);
        player.getInventory().removeItem(Vault.vault(Count));
    }
    public static void ItemStack (int Count) {
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
        vault.setAmount(Count);
    }
}
