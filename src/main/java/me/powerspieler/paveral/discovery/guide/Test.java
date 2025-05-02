package me.powerspieler.paveral.discovery.guide;

import me.powerspieler.paveral.Paveral;
import org.bukkit.block.data.type.Lectern;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.LecternInventory;

public class Test {
    public static void test(Player player) {
        LecternInventory sheesh = (LecternInventory) Paveral.getPlugin().getServer().createInventory(player, InventoryType.LECTERN);


    }
}
