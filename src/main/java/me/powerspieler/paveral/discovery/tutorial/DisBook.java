package me.powerspieler.paveral.discovery.tutorial;

import me.powerspieler.paveral.crafting.PaveralIngredient;
import me.powerspieler.paveral.crafting.PaveralRecipe;
import me.powerspieler.paveral.crafting.StandardIngredient;
import me.powerspieler.paveral.discovery.DiscoveryBook;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DisBook extends DiscoveryBook implements Formable {
    private static List<Component> pages(){
        List<Component> pages = new ArrayList<>();
        pages.add(GsonComponentSerializer.gson().deserialize("{\"italic\":true,\"color\":\"dark_green\",\"extra\":[{\"italic\":true,\"color\":\"light_purple\",\"text\":\"icious\n               Additions\n\n\n\"},{\"italic\":false,\"color\":\"black\",\"text\":\"          [Vol. 2]\"}],\"text\":\"\n\n\n\n\n   Paveral\"}"));
        pages.add(GsonComponentSerializer.gson().deserialize("{\"color\":\"dark_purple\",\"extra\":[{\"color\":\"red\",\"text\":\"The Disassembling Table\"}],\"text\":\"I am glad you made it to the second volume of the documentation of Paveralicious Additions. Another structure is described on the following pages:\n\n\"}"));
        pages.add(GsonComponentSerializer.gson().deserialize("{\"color\":\"dark_purple\",\"text\":\"You may have already found some new interesting items and tools laying in chests all around your world..\nWait you haven't?\nThen go and search for them!\n\nIf you already think these items are uncraftable, don't worry this is where\"}"));
        pages.add(GsonComponentSerializer.gson().deserialize("{\"color\":\"dark_purple\",\"text\":\"the Disassembling Table comes into place. You can use it to take tools apart and learn which materials had been used for the original forming.\n\nThe Blueprint is shown on the following pages.\"}"));
        pages.add(GsonComponentSerializer.gson().deserialize("{\"color\":\"dark_purple\",\"extra\":[{\"color\":\"dark_green\",\"text\":\"6x Wall\n2x Stair\n1x End Rod\n1x Iron Bars\n1x Smithing Table\"},{\"color\":\"dark_purple\",\"text\":\"\n\n\nThe type of wall or stair doesn\\'t matter.\nBuild it the way you like it!\"}],\"text\":\"Materials:\n\n\"}"));
        pages.add(GsonComponentSerializer.gson().deserialize("{\"underlined\":true,\"color\":\"red\",\"extra\":[{\"underlined\":false,\"color\":\"dark_purple\",\"text\":\" Side View\n\n\n\"},{\"underlined\":false,\"color\":\"gold\",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Stair (Connects With Iron Bars;\nOn Top of Wall)\"}},\"text\":\"⬛\"},{\"underlined\":false,\"color\":\"gray\",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Iron Bars\"}},\"text\":\"⬛\"},{\"underlined\":false,\"color\":\"gold\",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Stair (Connects With Iron Bars;\nOn Top of Wall)\"}},\"text\":\"⬛\n\"},{\"underlined\":false,\"color\":\"blue\",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Wall\"}},\"text\":\"⬛\"},{\"underlined\":false,\"color\":\"light_purple\",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"End Rod (Facing Down)\"}},\"text\":\"⬛\"},{\"underlined\":false,\"color\":\"blue\",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Wall\"}},\"text\":\"⬛\n\"},{\"underlined\":false,\"color\":\"blue\",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Wall\"}},\"text\":\"⬛\"},{\"underlined\":false,\"color\":\"white\",\"text\":\"⬛\"},{\"underlined\":false,\"color\":\"blue\",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Wall\"}},\"text\":\"⬛\n\"},{\"underlined\":false,\"color\":\"blue\",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Wall\"}},\"text\":\"⬛\"},{\"underlined\":false,\"color\":\"dark_gray\",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Smithing Table\"}},\"text\":\"⬛\"},{\"underlined\":false,\"color\":\"blue\",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Wall\"}},\"text\":\"⬛\n\n\"},{\"underlined\":false,\"color\":\"dark_purple\",\"text\":\"Hover for Details\"}],\"text\":\"[!]\"}"));
        pages.add(GsonComponentSerializer.gson().deserialize("{\"color\":\"dark_purple\",\"text\":\"Throw your item to be disassembled onto the smiting table to start the process. This process cannot be undone or interrupted, so keep an eye out for the resulting materials to reform the item on the altar again.\"}"));
        pages.add(GsonComponentSerializer.gson().deserialize("{\"text\":\"Due to the machines roughness enchantments cannot be extracted!\",\"color\":\"dark_purple\"}"));
        return pages;
    }

    public DisBook() {
        super("dis_book", "Powerspieler", "Paveralicious Additions - Vol. 2", null, pages(), false, false);
    }

    @Override
    public PaveralRecipe recipe() {
        Set<StandardIngredient> ingredients = new HashSet<>();
        ingredients.add(new PaveralIngredient(Material.WRITTEN_BOOK, 1, Constant.DISCOVERY, "altar_book"));
        ingredients.add(new StandardIngredient(Material.NETHERITE_SCRAP, 1));
        return new PaveralRecipe(ingredients, this.build());
    }
}
