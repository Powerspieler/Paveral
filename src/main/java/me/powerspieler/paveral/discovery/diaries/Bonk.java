package me.powerspieler.paveral.discovery.diaries;

import me.powerspieler.paveral.discovery.Discovery;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataType;

public class Bonk implements Discovery {
    @Override
    public ItemStack build() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookmeta = (BookMeta) book.getItemMeta();
        bookmeta.getPersistentDataContainer().set(Constant.DISCOVERY, PersistentDataType.STRING, "diary_34");
        bookmeta.setAuthor("");
        bookmeta.setTitle("Diary [#34]");
        bookmeta.setGeneration(BookMeta.Generation.TATTERED);
        bookmeta.addPages(Component.text("Day 1: \nIt´s my first day at this facility, the coworkers look horrified but I can´t explain why. They told me I was not allowed to know it yet."));
        bookmeta.addPages(Component.text("Day 2: \nToday my boss told me it would be a fun day because we only would look around in the facility, he yelled at me when I wanted to go in a restricted area, it was labeld with a HJ."));
        bookmeta.addPages(Component.text("Day 13: \nMy colleague Jim is missing for a week now, I was so worried that I asked my boss if he heard anything from him but he just waved it away - said he is ill but Jim and I have become good friends and we played videogames after work everyday and.. Come on! No videogames"));
        bookmeta.addPages(Component.text("when you are ill, seriously?"));
        bookmeta.addPages(Component.text("Day 30: \nI heard strange noises from the restricted area today maybe I should tell my boss about it but first I will go investigate and hope to get a pay-raise for my good work."));
        bookmeta.addPages(Component.text("Day 33: \nThe noises didn´t stop since day 30 - I was going to investigate - I sneaked around the cameras and everything but when I saw it there was some random machinery and on the wall a big blueprint from what seemed like a weapon of some kind. Now, standing in front of it,"));
        bookmeta.addPages(Component.text("there is something written on it. A glowing book - with ancient language, I guess. \nꖌリ\uD835\uDE79ᓵꖌʖᔑᓵꖌ II\n...it says and combined with a bunch of brown scrap should form another book? Next to it there was another blueprint with the book you got before and a stick?!?!"));
        bookmeta.addPages(Component.text("Day 34?: \nI woke up in a strange place and I couldn´t get out. I looked around and found the dead body of my friend Jim. I was frozen in shock and I started to think about my boss just waving Jim´s absence away like it was nothing. He lyed - He probably knew it all along at"));
        bookmeta.addPages(Component.text("first. Jim, now me, I should have listend to my mother, when she told me I shouldn´t work here. Now I´m trapped but why? Is this a security measure? I don´t have time to think about it. I have to get out of this place but there is no way."));
        bookmeta.addPages(Component.text("Day 35?: \nLooking outside the room I saw something written on the wall:\n\n\"Welcome to the HornyJail\"\n\nNO!! That can´t be true! I heard rumors about it but I never thought about it as a real place. The HornyJail is a place"));
        bookmeta.addPages(Component.text("where you can never leave and once you´re Bonked on the head with a special stick you magicaly enter it."));
        bookmeta.addPages(Component.text("Day 36?: \nI think I´m going crazy the corpse of Jim started to smell badly and I don´t think I will have much time left..."));
        bookmeta.addPages(Component.text(""));
        bookmeta.addPages(Component.text(""));
        bookmeta.addPages(Component.text(""));
        bookmeta.addPages(Component.text(""));
        bookmeta.addPages(Component.text(""));
        bookmeta.addPages(Component.text("Day 69!: \nLOL GET RECKED JIM n00b xD eZ"));
        book.setItemMeta(bookmeta);
        return book;
    }
}
