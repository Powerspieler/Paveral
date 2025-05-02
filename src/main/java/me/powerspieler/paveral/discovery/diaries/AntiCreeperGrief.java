package me.powerspieler.paveral.discovery.diaries;

import me.powerspieler.paveral.discovery.DiscoveryBook;
import me.powerspieler.paveral.discovery.Literature;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;

public class AntiCreeperGrief extends DiscoveryBook implements Literature {
    private static List<Component> pages(){
        List<Component> pages = new ArrayList<>();
        pages.add(Component.text("Who likes these exploding green creatures? Seriously?!\nI hate wasting my time filling up creeper holes!\n\nBut I've got a solution!\nI just need to combine a creeper head, a firework star and sculk sensor. But how am I supposed to get two of these three"));
        pages.add(Component.text("items? And how can I combine them together?\n\n\nThis could be the best thing in the world! Imagine ignoring these sneaky little ..."));
        return pages;
    }

    public AntiCreeperGrief() {
        super("diary_35", "", "Diary [#35]", BookMeta.Generation.TATTERED, pages(), true);
    }

    @Override
    public List<Component> getGuidePages() {
        return List.of();
    }
}
