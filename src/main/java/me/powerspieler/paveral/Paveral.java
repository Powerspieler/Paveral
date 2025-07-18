package me.powerspieler.paveral;

import me.powerspieler.paveral.advancements.AwardAdvancements;
import me.powerspieler.paveral.commands.ItemsCommand;
import me.powerspieler.paveral.commands.TestCommand;
import me.powerspieler.paveral.disassemble.AwakeTable;
import me.powerspieler.paveral.disassemble.DisassembleListeners;
import me.powerspieler.paveral.discovery.CatMorningGiftLootTable;
import me.powerspieler.paveral.discovery.ChestLootTable;
import me.powerspieler.paveral.discovery.FishingLootTable;
import me.powerspieler.paveral.discovery.guide.BaseGuide;
import me.powerspieler.paveral.discovery.guide.CombiningLiterature;
import me.powerspieler.paveral.discovery.guide.GuideCommand;
import me.powerspieler.paveral.discovery.papers.AchievementReward;
import me.powerspieler.paveral.forge.AwakeForge;
import me.powerspieler.paveral.forge.ForgeListener;
import me.powerspieler.paveral.forming_altar.AwakeAltar;
import me.powerspieler.paveral.forming_altar.FormingListeners;
import me.powerspieler.paveral.items.*;
import me.powerspieler.paveral.items.enhanced.Channeling;
import me.powerspieler.paveral.items.enhanced.Efficiency;
import me.powerspieler.paveral.items.enhanced.Knockback;
import me.powerspieler.paveral.items.helper.ItemHoldingController;
import me.powerspieler.paveral.items.helper.TotemDisabler;
import me.powerspieler.paveral.items.musicpack.*;
import me.powerspieler.paveral.items.parts.worldalterer.SonicEssence;
import me.powerspieler.paveral.misc.HandlePlayerJoin;
import me.powerspieler.paveral.util.DatapackLoader;
import me.powerspieler.paveral.util.MarkerDataStorage;
import me.powerspieler.paveral.util.RecipeLoader;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;


public final class Paveral extends JavaPlugin {
    private static Paveral plugin;

    // TODO Wrench: Bring back behavior
    // TODO Raphilius Waffe für Arena. On Kill: player stribt nicht wenn von dieser waffe getötet.
    // TODO Lightning rod: Bring back hold behavior
    // TODO Angle Ring; Pylone // Phanton Membran aufladen? Bei station aufladen?

    // TODO Rythms Awaking 2 two block high damage. // ?????

    @Override
    public void onEnable() {
        plugin = this;

        DatapackLoader.load();
        RecipeLoader.registerRecipes();

        Objects.requireNonNull(getCommand("test")).setExecutor(new TestCommand());
        //Objects.requireNonNull(getCommand("cooldown")).setExecutor(new CooldownCommand());
        Objects.requireNonNull(getCommand("items")).setExecutor(new ItemsCommand());

        PluginManager pm = Bukkit.getPluginManager();
        // Discovery
        pm.registerEvents(new BaseGuide(), this);
        pm.registerEvents(new CombiningLiterature(), this);
        pm.registerEvents(new AchievementReward(), this);
        Objects.requireNonNull(getCommand("guide")).setExecutor(new GuideCommand());


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
        pm.registerEvents(new StringBlade(Material.BLACK_DYE, "stringblade/black"), this);
        pm.registerEvents(new ResonatingPickaxe(), this);
        pm.registerEvents(new LumberjacksBass(), this);
        pm.registerEvents(new BardicInspiration(), this);
        pm.registerEvents(new ScytheOfHarmony(), this);

        // Enhanced Enchantments
        pm.registerEvents(new Knockback(), this);
        pm.registerEvents(new Channeling(), this);
        pm.registerEvents(new Efficiency(), this);

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
        pm.registerEvents(new TotemDisabler(), this);
    }
    public static Paveral getPlugin(){
        return plugin;
    }
}