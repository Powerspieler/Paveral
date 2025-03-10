package me.powerspieler.paveral.items;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.CustomModelData;
import me.powerspieler.paveral.crafting.PaveralRecipe;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public abstract class PaveralItem {
    protected Material baseMaterial;
    protected String customModelDataString;

    protected NamespacedKey key;
    protected String keyString;

    protected Component itemName;
    protected List<Component> lore;

    public PaveralItem(Material baseMaterial, String customModelDataString, NamespacedKey key, String keyString, Component itemName, List<Component> lore) {
        this.baseMaterial = baseMaterial;
        this.customModelDataString = customModelDataString;
        this.key = key;
        this.keyString = keyString;
        this.itemName = itemName;
        this.lore = lore;
    }

    public abstract PaveralRecipe recipe();

    protected ItemStack build(){
        ItemStack itemStack = new ItemStack(this.baseMaterial);
        itemStack.setData(DataComponentTypes.CUSTOM_MODEL_DATA, CustomModelData.customModelData().addString(this.customModelDataString).build());
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, keyString);

        itemMeta.itemName(itemName);
        itemMeta.lore(lore);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
