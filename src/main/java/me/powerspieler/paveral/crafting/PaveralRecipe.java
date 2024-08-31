package me.powerspieler.paveral.crafting;

import me.powerspieler.paveral.util.Constant;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;
import java.util.Set;

public record PaveralRecipe(Set<StandardIngredient> ingredients, ItemStack result) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaveralRecipe that)) return false;
        return Objects.equals(ingredients(), that.ingredients()) && Objects.equals(result(), that.result());
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredients(), result());
    }

    @Override
    public String toString() {
        return "\nPaveralRecipe{" +
                "ingredients=" + ingredients +
                ", result=" + result.getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING) +
                '}';
    }
}
