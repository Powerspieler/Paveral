package me.powerspieler.paveral.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public abstract class PaveralItem {
    protected Material baseMaterial;
    protected int customModelData;

    protected NamespacedKey key;
    protected String keyString;

    protected Component itemName;
    protected List<Component> lore;


    public PaveralItem(Material baseMaterial, int customModelData, NamespacedKey key, String keyString, Component itemName, List<Component> lore) {
        this.baseMaterial = baseMaterial;
        this.customModelData = customModelData;
        this.key = key;
        this.keyString = keyString;
        this.itemName = itemName;
        this.lore = lore;
    }

    protected ItemStack build(){
        ItemStack itemStack = new ItemStack(this.baseMaterial);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, keyString);
        itemMeta.setCustomModelData(customModelData);

        itemMeta.itemName(itemName);
        itemMeta.lore(lore);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
