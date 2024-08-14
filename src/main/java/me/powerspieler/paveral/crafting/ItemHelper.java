package me.powerspieler.paveral.crafting;

import me.powerspieler.paveral.util.Constant;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ItemHelper {

    public static Set<StandardIngredient> convertToIngredientSet(Set<Item> itemSet){
        Set<StandardIngredient> out = new HashSet<>();
        for(Item item : itemSet){
            ItemStack itemStack = item.getItemStack();
            if(itemStack.hasItemMeta() && itemStack.getItemMeta() instanceof EnchantmentStorageMeta meta){
                out.add(new EnchantmentIngredient(meta.getStoredEnchants()));
            } else if (ItemHelper.hasPaveralNamespacedKey(itemStack, Constant.ITEMTYPE)) {
                out.add(new PaveralIngredient(itemStack.getType(), itemStack.getAmount(), Constant.ITEMTYPE, ItemHelper.getPaveralNamespacedKey(itemStack, Constant.ITEMTYPE)));
            } else if (ItemHelper.hasPaveralNamespacedKey(itemStack, Constant.DISCOVERY)) {
                out.add(new PaveralIngredient(itemStack.getType(), itemStack.getAmount(), Constant.DISCOVERY, ItemHelper.getPaveralNamespacedKey(itemStack, Constant.DISCOVERY)));
            } else {
                out.add(new StandardIngredient(itemStack.getType(), itemStack.getAmount()));
            }
        }
        return out;
    }

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
