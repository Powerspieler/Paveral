package me.powerspieler.paveral.crafting;

import org.bukkit.inventory.ItemStack;

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
        return "PaveralRecipe{" +
                "ingredients=" + ingredients +
                ", result=" + result.getItemMeta().itemName().examinableName() +
                '}';
    }
}
