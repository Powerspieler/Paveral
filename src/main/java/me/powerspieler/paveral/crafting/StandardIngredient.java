package me.powerspieler.paveral.crafting;

import org.bukkit.Material;

import java.util.Objects;

public class StandardIngredient {
    private final Material material;
    private final int amount;

    public StandardIngredient(Material material, int amount) {
        this.material = material;
        this.amount = amount;
    }

    public Material getMaterial() {
        return material;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StandardIngredient that)) return false;
        return getMaterial() == that.getMaterial() && getAmount() == that.getAmount();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMaterial(), getAmount());
    }

    @Override
    public String toString() {
        return amount + "x " + material;
    }
}
