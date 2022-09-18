package me.powerspieler.paveral.util;

import me.powerspieler.paveral.Paveral;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class AdvancementLoader {

    public void load() throws IOException {
        String json;
        Path path;
        URL url;
        NamespacedKey root = new NamespacedKey(Paveral.getPlugin(), "root");
        NamespacedKey bedrock_breaker = new NamespacedKey(Paveral.getPlugin(), "bedrock_breaker");
        NamespacedKey bonk = new NamespacedKey(Paveral.getPlugin(), "bonk");
        NamespacedKey chunkloader = new NamespacedKey(Paveral.getPlugin(), "chunkloader");
        NamespacedKey craft_tutorial_book = new NamespacedKey(Paveral.getPlugin(), "craft_tutorial_book");
        NamespacedKey craft_tutorial_book_forge = new NamespacedKey(Paveral.getPlugin(), "craft_tutorial_book_forge");
        NamespacedKey dis_anti_creeper_grief = new NamespacedKey(Paveral.getPlugin(), "dis_anti_creeper_grief");
        NamespacedKey dis_lightstaff = new NamespacedKey(Paveral.getPlugin(), "dis_lightstaff");
        NamespacedKey dis_wrench = new NamespacedKey(Paveral.getPlugin(), "dis_wrench");
        NamespacedKey find_diary = new NamespacedKey(Paveral.getPlugin(), "find_diary");
        NamespacedKey first_dis = new NamespacedKey(Paveral.getPlugin(), "first_dis");
        NamespacedKey first_fairy_tail = new NamespacedKey(Paveral.getPlugin(), "first_fairy_tail");
        NamespacedKey first_forming = new NamespacedKey(Paveral.getPlugin(), "first_forming");
        NamespacedKey fishing = new NamespacedKey(Paveral.getPlugin(), "fishing");
        NamespacedKey flying_pig = new NamespacedKey(Paveral.getPlugin(), "flying_pig");
        NamespacedKey lightning_rod = new NamespacedKey(Paveral.getPlugin(), "lightning_rod");
        NamespacedKey sleep_with_cat = new NamespacedKey(Paveral.getPlugin(), "sleep_with_cat");


        url = Paveral.class.getClassLoader().getResource("advancements/root");
        if (url != null) {
            path = Path.of(url.getPath());
            json = Files.readString(path);
            Bukkit.getUnsafe().loadAdvancement(root, json);
        }

        url = Paveral.class.getClassLoader().getResource("advancements/bedrock_breaker");
        if (url != null) {
            path = Path.of(url.getPath());
            json = Files.readString(path);
            Bukkit.getUnsafe().loadAdvancement(bedrock_breaker, json);
        }

        url = Paveral.class.getClassLoader().getResource("advancements/bonk");
        if (url != null) {
            path = Path.of(url.getPath());
            json = Files.readString(path);
            Bukkit.getUnsafe().loadAdvancement(bonk, json);
        }

        url = Paveral.class.getClassLoader().getResource("advancements/chunkloader");
        if (url != null) {
            path = Path.of(url.getPath());
            json = Files.readString(path);
            Bukkit.getUnsafe().loadAdvancement(chunkloader, json);
        }

        url = Paveral.class.getClassLoader().getResource("advancements/craft_tutorial_book");
        if (url != null) {
            path = Path.of(url.getPath());
            json = Files.readString(path);
            Bukkit.getUnsafe().loadAdvancement(craft_tutorial_book, json);
        }

        url = Paveral.class.getClassLoader().getResource("advancements/craft_tutorial_book_forge");
        if (url != null) {
            path = Path.of(url.getPath());
            json = Files.readString(path);
            Bukkit.getUnsafe().loadAdvancement(craft_tutorial_book_forge, json);
        }

        url = Paveral.class.getClassLoader().getResource("advancements/dis_anti_creeper_grief");
        if (url != null) {
            path = Path.of(url.getPath());
            json = Files.readString(path);
            Bukkit.getUnsafe().loadAdvancement(dis_anti_creeper_grief, json);
        }

        url = Paveral.class.getClassLoader().getResource("advancements/dis_lightstaff");
        if (url != null) {
            path = Path.of(url.getPath());
            json = Files.readString(path);
            Bukkit.getUnsafe().loadAdvancement(dis_lightstaff, json);
        }

        url = Paveral.class.getClassLoader().getResource("advancements/dis_wrench");
        if (url != null) {
            path = Path.of(url.getPath());
            json = Files.readString(path);
            Bukkit.getUnsafe().loadAdvancement(dis_wrench, json);
        }

        url = Paveral.class.getClassLoader().getResource("advancements/find_diary");
        if (url != null) {
            path = Path.of(url.getPath());
            json = Files.readString(path);
            Bukkit.getUnsafe().loadAdvancement(find_diary, json);
        }

        url = Paveral.class.getClassLoader().getResource("advancements/first_dis");
        if (url != null) {
            path = Path.of(url.getPath());
            json = Files.readString(path);
            Bukkit.getUnsafe().loadAdvancement(first_dis, json);
        }

        url = Paveral.class.getClassLoader().getResource("advancements/first_fairy_tail");
        if (url != null) {
            path = Path.of(url.getPath());
            json = Files.readString(path);
            Bukkit.getUnsafe().loadAdvancement(first_fairy_tail, json);
        }

        url = Paveral.class.getClassLoader().getResource("advancements/first_forming");
        if (url != null) {
            path = Path.of(url.getPath());
            json = Files.readString(path);
            Bukkit.getUnsafe().loadAdvancement(first_forming, json);
        }

        url = Paveral.class.getClassLoader().getResource("advancements/fishing");
        if (url != null) {
            path = Path.of(url.getPath());
            json = Files.readString(path);
            Bukkit.getUnsafe().loadAdvancement(fishing, json);
        }

        url = Paveral.class.getClassLoader().getResource("advancements/flying_pig");
        if (url != null) {
            path = Path.of(url.getPath());
            json = Files.readString(path);
            Bukkit.getUnsafe().loadAdvancement(flying_pig, json);
        }

        url = Paveral.class.getClassLoader().getResource("advancements/lightning_rod");
        if (url != null) {
            path = Path.of(url.getPath());
            json = Files.readString(path);
            Bukkit.getUnsafe().loadAdvancement(lightning_rod, json);
        }

        url = Paveral.class.getClassLoader().getResource("advancements/sleep_with_cat");
        if (url != null) {
            path = Path.of(url.getPath());
            json = Files.readString(path);
            Bukkit.getUnsafe().loadAdvancement(sleep_with_cat, json);
        }

    }
}
