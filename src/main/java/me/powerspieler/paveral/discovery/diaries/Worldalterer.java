package me.powerspieler.paveral.discovery.diaries;

import me.powerspieler.paveral.discovery.DiscoveryBook;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;

public class Worldalterer extends DiscoveryBook {
    private static List<Component> pages() {
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("PLACEHOLDER"));
        return pages;
    }

    public Worldalterer() {
        super("worldalterer", "", "EMI Research", BookMeta.Generation.TATTERED, pages(), true);
    }
}
