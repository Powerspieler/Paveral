package de.powerspieler.paveral;

import de.powerspieler.paveral.commands.TestCommand;
import de.powerspieler.paveral.items.AntiCreeperGrief;
import de.powerspieler.paveral.items.LightStaff;
import de.powerspieler.paveral.listeners.ItemDropListener;
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

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new AntiCreeperGrief(), this);
        pm.registerEvents(new LightStaff(), this);
        pm.registerEvents(new ItemDropListener(), this);
    }

    public static Paveral getPlugin(){
        return plugin;
    }
}