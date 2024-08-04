package me.powerspieler.paveral;

import me.powerspieler.paveral.advancements.AwardAdvancements;
import me.powerspieler.paveral.commands.ItemsCommand;
import me.powerspieler.paveral.commands.TestCommand;
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
import me.powerspieler.paveral.items.musicpack.PianoSword;
import me.powerspieler.paveral.items.musicpack.ScytheOfHarmony;
import me.powerspieler.paveral.items.musicpack.StringBlade;
import me.powerspieler.paveral.items.musicpack.VampiresBass;
import me.powerspieler.paveral.items.parts.worldalterer.SonicEssence;
import me.powerspieler.paveral.misc.HandlePlayerJoin;
import me.powerspieler.paveral.util.AdvancementLoader;
import me.powerspieler.paveral.util.MarkerDataStorage;
import me.powerspieler.paveral.util.RecipeLoader;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;


public final class Paveral extends JavaPlugin {
    private static Paveral plugin;

    //TODO Forming Listner modularisieren
    // TODO Forge Listner modularisieren
    // TODO Vampires Bass / Achievement / Crafting
    // TODO Music Pickaxe / Achievement / Crafting
    // TODO Music Shovel / Achievemnet / Crafting
    // TODO mit Forming Listner dann String Blades Colors einfacher machen
    // TODO Advancement ohne zip; nicht immer kopieren nur bei Changes
    // TODO Interface Items -> abstract PaveralItem und maybe Interface bzgl. enchantments und maybe auch disasseble optional interface machen
    // TODO Worldalterer spam fix
    // TODO String Blade / Piano Sword FireAspect?


    @Override
    public void onEnable() {
        plugin = this;

        AdvancementLoader.copyAdvancements();
        RecipeLoader.registerRecipes();

        Objects.requireNonNull(getCommand("test")).setExecutor(new TestCommand());
        //Objects.requireNonNull(getCommand("cooldown")).setExecutor(new CooldownCommand());
        Objects.requireNonNull(getCommand("items")).setExecutor(new ItemsCommand());

        PluginManager pm = Bukkit.getPluginManager();
        // Items
        pm.registerEvents(new ItemHoldingController(), this);

        pm.registerEvents(new AntiCreeperGrief(), this);
        pm.registerEvents(new BedrockBreaker(), this);
        pm.registerEvents(new Chunkloader(), this);
        pm.registerEvents(new LightStaff(), this);
        pm.registerEvents(new LightningRod(), this);
        pm.registerEvents(new Worldalterer(), this);
        pm.registerEvents(new SonicEssence(), this);
        pm.registerEvents(new Wrench(), this);

        pm.registerEvents(new PianoSword(), this);
        pm.registerEvents(new StringBlade(), this);
        // pickaxe
        pm.registerEvents(new VampiresBass(), this);
        //shovel
        pm.registerEvents(new ScytheOfHarmony(), this);

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
        pm.registerEvents(new MarkerDataStorage(), this);
    }
    public static Paveral getPlugin(){
        return plugin;
    }
}