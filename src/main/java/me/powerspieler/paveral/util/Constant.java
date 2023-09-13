package me.powerspieler.paveral.util;

import me.powerspieler.paveral.Paveral;
import org.bukkit.NamespacedKey;

public class Constant {
    Constant(){}
    public static final NamespacedKey ITEMTYPE = new NamespacedKey(Paveral.getPlugin(), "itemtype");
    public static final NamespacedKey DISCOVERY = new NamespacedKey(Paveral.getPlugin(), "discovery");
    public static final NamespacedKey IS_DIARY = new NamespacedKey(Paveral.getPlugin(), "is_diary");
    public static final NamespacedKey IS_HOLDING = new NamespacedKey(Paveral.getPlugin(), "isHolding");

    // Worldalterer
    public static final NamespacedKey WA_POS1 = new NamespacedKey(Paveral.getPlugin(), "WA_POS1");
    public static final NamespacedKey WA_POS2 = new NamespacedKey(Paveral.getPlugin(), "WA_POS2");
    public static final NamespacedKey WA_FACING = new NamespacedKey(Paveral.getPlugin(), "WA_FACING");
    public static final NamespacedKey WA_GLOWOWNER = new NamespacedKey(Paveral.getPlugin(), "WA_GLOWOWNER");
}
