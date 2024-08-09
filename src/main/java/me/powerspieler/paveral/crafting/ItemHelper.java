package me.powerspieler.paveral.crafting;

import me.powerspieler.paveral.util.Constant;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class ItemHelper {

    public static boolean hasPaveralNamespacedKey(ItemStack item, NamespacedKey namespacedKey){
        return item != null
                && item.hasItemMeta()
                && item.getItemMeta().getPersistentDataContainer().has(namespacedKey);
    }

    public static String getPaveralNamespacedKey(ItemStack item, NamespacedKey namespacedKey){
        if(hasPaveralNamespacedKey(item, namespacedKey)){
            return item.getItemMeta().getPersistentDataContainer().get(namespacedKey, PersistentDataType.STRING);
        }
        return null;
    }

    public static boolean paveralNamespacedKeyEquals(ItemStack item, NamespacedKey namespacedKey, String string){
        return hasPaveralNamespacedKey(item, namespacedKey)
                && Objects.equals(getPaveralNamespacedKey(item, namespacedKey), string);
    }
}
