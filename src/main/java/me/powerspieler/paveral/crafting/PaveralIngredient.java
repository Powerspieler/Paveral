package me.powerspieler.paveral.crafting;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import java.util.Objects;

public class PaveralIngredient extends StandardIngredient {
    private final NamespacedKey namespacedKey;
    private final String itemType;

    public PaveralIngredient(Material material, int amount, NamespacedKey namespacedKey, String itemType) {
        super(material, amount);
        this.namespacedKey = namespacedKey;
        this.itemType = itemType;
    }

    public String getItemType() {
        return itemType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaveralIngredient that)) return false;
        return super.equals(that) && Objects.equals(namespacedKey, that.namespacedKey) && Objects.equals(getItemType(), that.getItemType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), namespacedKey, getItemType());
    }

    @Override
    public String toString() {
        return itemType + " of " + namespacedKey;
    }
}
