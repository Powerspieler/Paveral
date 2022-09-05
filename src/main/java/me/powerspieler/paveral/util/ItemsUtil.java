package me.powerspieler.paveral.util;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class ItemsUtil {
    private ItemsUtil(){}

    public static void applyDamage(ItemStack item, int damagevalue) {
        int unbreakinglvl = item.getEnchantmentLevel(Enchantment.DURABILITY);
        boolean shoulddamage = ((Math.random()) < (1 / (unbreakinglvl + 1.0)));
        if (shoulddamage) {
            Damageable itemvalue = (Damageable) item.getItemMeta();
            int damage = (itemvalue.getDamage() + damagevalue);
            if (damage > 100) {
                damage = 100;
            }
            itemvalue.setDamage(damage);
            item.setItemMeta(itemvalue);
        }
    }

    public static void repair(ItemStack item, int damage){
        Damageable itemvalue = (Damageable) item.getItemMeta();
        itemvalue.setDamage(itemvalue.getDamage() - damage);
        item.setItemMeta(itemvalue);
    }
}
