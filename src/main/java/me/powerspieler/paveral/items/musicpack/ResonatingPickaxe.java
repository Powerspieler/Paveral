package me.powerspieler.paveral.items.musicpack;

import com.destroystokyo.paper.MaterialSetTag;
import me.powerspieler.paveral.crafting.ItemHelper;
import me.powerspieler.paveral.crafting.PaveralIngredient;
import me.powerspieler.paveral.crafting.PaveralRecipe;
import me.powerspieler.paveral.crafting.StandardIngredient;
import me.powerspieler.paveral.items.Enchantable;
import me.powerspieler.paveral.items.ItemHoldingController;
import me.powerspieler.paveral.items.PaveralItem;
import me.powerspieler.paveral.util.Constant;
import me.powerspieler.paveral.util.ItemsUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResonatingPickaxe extends AdvancedMiningTool implements Listener {
    private static Component itemName(){
        return Component.text("Resonating Pickaxe", NamedTextColor.DARK_PURPLE);
    }

    private static List<Component> lore(){
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Due to its double pickaxe head, this tool is able to mine a 3x3 area", NamedTextColor.DARK_AQUA)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        lore.add(Component.text("Cannot be enchanted with ", NamedTextColor.BLUE)
                        .decoration(TextDecoration.ITALIC, false)
                        .append(Component.text("Unbreaking", NamedTextColor.GRAY)
                                .decoration(TextDecoration.ITALIC, false)));
        return lore;
    }

    public ResonatingPickaxe() {
        super(Material.NETHERITE_PICKAXE, "resonating_pickaxe", itemName(), lore());
    }

    @Override
    public PaveralRecipe recipe() {
        Set<StandardIngredient> ingredients = new HashSet<>();
        ingredients.add(new StandardIngredient(Material.NETHERITE_PICKAXE, 1));
        ingredients.add(new PaveralIngredient(Material.JIGSAW, 1, Constant.ITEMTYPE, "music_core"));
        return new PaveralRecipe(ingredients, this.build());
    }

    @EventHandler
    protected void onBlockBreak(BlockBreakEvent event){
        super.onBlockBreak(event);
    }


    @Override
    @EventHandler
    public void onEnchantingAttempt(PrepareAnvilEvent event) {
        super.onEnchantingAttempt(event);
    }
}
