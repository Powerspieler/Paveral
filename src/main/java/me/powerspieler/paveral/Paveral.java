package me.powerspieler.paveral;

import me.powerspieler.paveral.advancements.AwardAdvancements;
import me.powerspieler.paveral.commands.ItemsCommand;
import me.powerspieler.paveral.disassemble.AwakeTable;
import me.powerspieler.paveral.disassemble.DisassembleListeners;
import me.powerspieler.paveral.discovery.CatMorningGiftLootTable;
import me.powerspieler.paveral.discovery.ChestLootTable;
import me.powerspieler.paveral.discovery.FishingLootTable;
import me.powerspieler.paveral.forge.AwakeForge;
import me.powerspieler.paveral.forge.ForgeListener;
import me.powerspieler.paveral.forming_altar.AwakeAltar;
import me.powerspieler.paveral.forming_altar.FormingListeners;
import me.powerspieler.paveral.items.*;
import me.powerspieler.paveral.items.enchanced.Channeling;
import me.powerspieler.paveral.items.enchanced.Knockback;
import me.powerspieler.paveral.misc.HandlePlayerJoin;
import me.powerspieler.paveral.util.AdvancementLoader;
import me.powerspieler.paveral.util.RecipeLoader;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;


public final class Paveral extends JavaPlugin {
    private static Paveral plugin;

    @Override
    public void onEnable() {
        plugin = this;

        AdvancementLoader.copyAdvancements();
        new RecipeLoader().registerRecipes();

        //Objects.requireNonNull(getCommand("test")).setExecutor(new TestCommand());
        //Objects.requireNonNull(getCommand("cooldown")).setExecutor(new CooldownCommand());
        Objects.requireNonNull(getCommand("items")).setExecutor(new ItemsCommand());

        PluginManager pm = Bukkit.getPluginManager();
        // Items
        pm.registerEvents(new AntiCreeperGrief(), this);
        pm.registerEvents(new BedrockBreaker(), this);
        pm.registerEvents(new Chunkloader(), this);
        pm.registerEvents(new LightStaff(), this);
        pm.registerEvents(new LightningRod(), this);
        pm.registerEvents(new Wrench(), this);

        // Enhanced Enchantments
        pm.registerEvents(new Knockback(), this);
        pm.registerEvents(new Channeling(), this);

        // Forming Altar
        pm.registerEvents(new AwakeAltar(), this);
        pm.registerEvents(new FormingListeners(), this);

        // Disassembling Table
        pm.registerEvents(new AwakeTable(), this);
        pm.registerEvents(new DisassembleListeners(), this);

        // Forge
        pm.registerEvents(new AwakeForge(), this);
        pm.registerEvents(new ForgeListener(), this);

        // Discovery
        pm.registerEvents(new ChestLootTable(), this);
        pm.registerEvents(new CatMorningGiftLootTable(), this);
        pm.registerEvents(new FishingLootTable(), this);

        //Advancements
        pm.registerEvents(new AwardAdvancements(), this);

        // Misc
        pm.registerEvents(new HandlePlayerJoin(), this);
    }
    public static Paveral getPlugin(){
        return plugin;
    }
}