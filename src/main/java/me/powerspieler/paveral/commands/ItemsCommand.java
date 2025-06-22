package me.powerspieler.paveral.commands;

import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.discovery.diaries.Enhancing;
import me.powerspieler.paveral.discovery.guide.BaseGuide;
import me.powerspieler.paveral.discovery.papers.Paper;
import me.powerspieler.paveral.discovery.tutorial.AltarBook;
import me.powerspieler.paveral.discovery.tutorial.DisBook;
import me.powerspieler.paveral.discovery.tutorial.TechBook;
import me.powerspieler.paveral.items.*;
import me.powerspieler.paveral.items.enhanced.Channeling;
import me.powerspieler.paveral.items.enhanced.Efficiency;
import me.powerspieler.paveral.items.enhanced.Knockback;
import me.powerspieler.paveral.items.musicpack.*;
import me.powerspieler.paveral.items.parts.MusicCore;
import me.powerspieler.paveral.items.parts.worldalterer.*;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ItemsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player){
            if(player.isOp()){
                Inventory inventory = Bukkit.createInventory(player, 54, Component.text("Paveral Items", NamedTextColor.GOLD));
                inventory.setItem(0, new AltarBook().build());
                inventory.setItem(1, new DisBook().build());
                inventory.setItem(2, new TechBook().build());

                inventory.setItem(6, new Knockback().recipe().result());
                inventory.setItem(7, new Channeling().recipe().result());
                inventory.setItem(8, new Efficiency().recipe().result());

                inventory.setItem(9, new AntiCreeperGrief().recipe().result());
                inventory.setItem(10, new BedrockBreaker().recipe().result());
                inventory.setItem(11, new Chunkloader().recipe().result());
                inventory.setItem(12, new LightningRod().build());
                inventory.setItem(13, new LightStaff().recipe().result());
                inventory.setItem(14, new Worldalterer().recipe().result());
                inventory.setItem(15, new Wrench().recipe().result());
                inventory.setItem(16, Knockback.bonk());

                inventory.setItem(18, MusicCore.registerRecipe().getResult());
                inventory.setItem(19, new PianoSword().recipe().result());
                inventory.setItem(20, new StringBlade(Material.BLACK_DYE, "stringblade/black").recipe().result());
                inventory.setItem(21, new ResonatingPickaxe().recipe().result());
                inventory.setItem(22, new LumberjacksBass().recipe().result());
                inventory.setItem(23, new BardicInspiration().recipe().result());
                inventory.setItem(24, new ScytheOfHarmony().recipe().result());

                inventory.setItem(27, new SonicEssence().build());
                inventory.setItem(28, SculkCircuit.registerRecipe().getResult());
                inventory.setItem(29, AlterationCore.registerRecipe().getResult());
                inventory.setItem(30, AmethystLaser.registerRecipe().getResult());
                inventory.setItem(31, EchoContainer.registerRecipe().getResult());

                inventory.setItem(36, new BaseGuide().build());
                inventory.setItem(37, fullGuide());
                inventory.setItem(40, Paper.disassemblePaper());
                inventory.setItem(41, Paper.forgePaper());
                inventory.setItem(42, Paper.musicCorePaper());
                inventory.setItem(43, Paper.musicCoreItemsPaper());
                inventory.setItem(44, Paper.lightstaffPaper());

                inventory.setItem(45, new me.powerspieler.paveral.discovery.diaries.BedrockBreaker().build());
                inventory.setItem(46, new me.powerspieler.paveral.discovery.diaries.Bonk().build());
                inventory.setItem(47, new Enhancing().build());
                inventory.setItem(48, new me.powerspieler.paveral.discovery.diaries.LightningRod().build());
                inventory.setItem(49, waBook());

                player.openInventory(inventory);
            } else player.sendMessage(Component.text("ERROR: No Op", NamedTextColor.RED));
        }
        return false;
    }

    private static ItemStack fullGuide() {
        ItemStack item = new BaseGuide().build();
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(Paveral.getPlugin(), "guide_entries"), Constant.STRING_LIST_DATA_TYPE, List.of(
                "Forming", "Dis", "Forge",
                "Enhanced", "Bonk","LightningRod",
                "Lightstaff","BedrockBreaker",
                "MusicCore","MusicPianoSword","MusicStringBlade","MusicPickaxe","MusicAxe","MusicShovel","MusicHoe",
                "CreeperDefuser","Chunkloader","Wrench","Worldalterer"));
        item.setItemMeta(itemMeta);
        return item;
    }

    private static ItemStack waBook() {
        ItemStack item = new me.powerspieler.paveral.discovery.diaries.Worldalterer().build();
        ItemMeta itemMeta = item.getItemMeta();
        List<Component> lore = itemMeta.lore() != null ? itemMeta.lore() : new ArrayList<>();
        lore.addFirst(Component.empty());
        lore.addFirst(Component.text("Looks like this book still isn't finished"));
        itemMeta.lore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }
}
