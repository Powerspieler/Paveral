package de.powerspieler.paveral;

import de.powerspieler.paveral.commands.CooldownCommand;
import de.powerspieler.paveral.commands.TestCommand;
import de.powerspieler.paveral.forming_altar.Awake;
import de.powerspieler.paveral.forming_altar.FormingListeners;
import de.powerspieler.paveral.items.AntiCreeperGrief;
import de.powerspieler.paveral.items.LightStaff;
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
        pm.registerEvents(new AntiCreeperGrief(), this);
        pm.registerEvents(new LightStaff(), this);
        pm.registerEvents(new Awake(), this);
        pm.registerEvents(new FormingListeners(), this);
    }

    public static Paveral getPlugin(){
        return plugin;
    }
}