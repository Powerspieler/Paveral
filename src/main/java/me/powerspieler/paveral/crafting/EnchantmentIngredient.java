package me.powerspieler.paveral.crafting;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.util.Map;
import java.util.Objects;

public class EnchantmentIngredient extends StandardIngredient {
    private final Map<Enchantment, Integer> storedEnchantments;

    public EnchantmentIngredient(Map<Enchantment, Integer> storedEnchantments) {
        super(Material.ENCHANTED_BOOK, 1);
        this.storedEnchantments = storedEnchantments;
    }

    public Map<Enchantment, Integer> getStoredEnchantments() {
        return storedEnchantments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnchantmentIngredient that)) return false;
        return super.equals(that) && Objects.equals(getStoredEnchantments(), that.getStoredEnchantments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getStoredEnchantments());
    }

    @Override
    public String toString() {
        return "Book with " + storedEnchantments;
    }
}
