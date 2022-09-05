package me.powerspieler.paveral;

import me.powerspieler.paveral.commands.CooldownCommand;
import me.powerspieler.paveral.commands.TestCommand;
import me.powerspieler.paveral.forming_altar.Awake;
import me.powerspieler.paveral.forming_altar.FormingListeners;
import me.powerspieler.paveral.items.AntiCreeperGrief;
import me.powerspieler.paveral.items.BedrockBreaker;
import me.powerspieler.paveral.items.Chunkloader;
import me.powerspieler.paveral.items.enchanced.Knockback;
import me.powerspieler.paveral.items.LightStaff;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Paveral extends JavaPlugin {
    private static Paveral plugin;

    @Override
    public void onEnable() {
        plugin = this;


        Objects.requireNonNull(getCommand("test")).setExecutor(new TestCommand());
        Objects.requireNonNull(getCommand("cooldown")).setExecutor(new CooldownCommand());

        PluginManager pm = Bukkit.getPluginManager();
        // Items
        pm.registerEvents(new AntiCreeperGrief(), this);
        pm.registerEvents(new BedrockBreaker(), this);
        pm.registerEvents(new Chunkloader(), this);
        pm.registerEvents(new LightStaff(), this);

        // Enhanced Enchantments
        pm.registerEvents(new Knockback(), this);

        // Forming Altar
        pm.registerEvents(new Awake(), this);
        pm.registerEvents(new FormingListeners(), this);
    }

    public static Paveral getPlugin(){
        return plugin;
    }
}