package me.powerspieler.paveral.items;

import me.powerspieler.paveral.crafting.PaveralRecipe;
import me.powerspieler.paveral.crafting.StandardIngredient;
import me.powerspieler.paveral.items.helper.ActionbarStatus;
import me.powerspieler.paveral.items.helper.Dismantable;
import me.powerspieler.paveral.items.helper.ItemHoldingControllerEvent;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class AntiCreeperGrief extends PaveralItem implements Listener, Dismantable {
    private static Component itemName(){
        return Component.text("Creeper Defuser", NamedTextColor.GOLD)
                .decoration(TextDecoration.ITALIC, false);
    }

    private static List<Component> lore(){
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Keep in inventory to prevent creepers from exploding nearby", NamedTextColor.DARK_AQUA)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("Can be placed in an itemframe to protect a whole area", NamedTextColor.DARK_AQUA)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("When hold in hand it displays if you're in a safe area", NamedTextColor.DARK_AQUA)
                .decoration(TextDecoration.ITALIC, false));
        return lore;
    }

    public AntiCreeperGrief() {
        super(Material.JIGSAW, 1, Constant.ITEMTYPE, "anti_creeper_grief", itemName(), lore());
    }

    @Override
    public PaveralRecipe recipe() {
        Set<StandardIngredient> ingredients = new HashSet<>();
        ingredients.add(new StandardIngredient(Material.CREEPER_HEAD, 1));
        ingredients.add(new StandardIngredient(Material.FIREWORK_STAR, 1));
        ingredients.add(new StandardIngredient(Material.SCULK_SENSOR, 1));
        return new PaveralRecipe(ingredients, this.build());
    }

    @EventHandler
    protected void actionbarDisplay(ItemHoldingControllerEvent event) {
        if(event.getItemType().equals(keyString)){
            Player player = event.getPlayer();
            new ActionbarStatus(player, keyString, 5L) {
                @Override
                public void message() {
                    if (checkForItemframe(player)) {
                        player.sendActionBar(Component.text("[ ", NamedTextColor.GOLD)
                                .append(Component.text("Save",NamedTextColor.GREEN))
                                .append(Component.text(" ]",NamedTextColor.GOLD)));
                    } else
                        player.sendActionBar(Component.text("[ ", NamedTextColor.GOLD)
                                .append(Component.text("Unsave",NamedTextColor.RED))
                                .append(Component.text(" ]",NamedTextColor.GOLD)));

                }
            }.displayMessage();
        }
    }

    @Override
    public List<ItemStack> parts() {
        List<ItemStack> parts = new ArrayList<>();
        parts.add(new ItemStack(Material.CREEPER_HEAD));
        parts.add(new ItemStack(Material.FIREWORK_STAR));
        parts.add(new ItemStack(Material.SCULK_SENSOR));
        return parts;
    }

    // --- Item Logic ---

    private Entity creeper;

    private Color getColor(int i) {
        Color c = null;
        if(i==1){
            c=Color.AQUA;
        }
        if(i==2){
            c=Color.BLACK;
        }
        if(i==3){
            c=Color.BLUE;
        }
        if(i==4){
            c=Color.FUCHSIA;
        }
        if(i==5){
            c=Color.GRAY;
        }
        if(i==6){
            c=Color.GREEN;
        }
        if(i==7){
            c=Color.LIME;
        }
        if(i==8){
            c=Color.MAROON;
        }
        if(i==9){
            c=Color.NAVY;
        }
        if(i==10){
            c=Color.OLIVE;
        }
        if(i==11){
            c=Color.ORANGE;
        }
        if(i==12){
            c=Color.PURPLE;
        }
        if(i==13){
            c=Color.RED;
        }
        if(i==14){
            c=Color.SILVER;
        }
        if(i==15){
            c=Color.TEAL;
        }
        if(i==16){
            c=Color.WHITE;
        }
        if(i==17){
            c=Color.YELLOW;
        }

        return c;
    }

    @EventHandler // handle CreeperExplosions
    private void onCreeperExplosion(EntityExplodeEvent event) {
        if (!(event.getEntityType() == EntityType.CREEPER)) return;
        creeper = event.getEntity();
        if(checkForPlayer(creeper) || checkForItemframe(creeper)){
            event.setCancelled(true);
            defineFirework();
        }
    }

    @EventHandler //handle EntityDamage Inflicted By Creeper
    private void onCreeperDamageEntity(EntityDamageByEntityEvent event){
        if(!(event.getDamager().getType() == EntityType.CREEPER)) return;
        Entity damager = event.getDamager();
        if(checkForPlayer(damager) || checkForItemframe(damager)){
            event.setCancelled(true);
        }
    }

    @EventHandler //handle HangingDamage Inflicted by Creeper
    private void onCreeperDamageHanging(HangingBreakByEntityEvent event){
        if (!(event.getRemover().getType() == EntityType.CREEPER)) return;
        Entity remover = event.getRemover();
        if(checkForPlayer(remover) || checkForItemframe(remover)){
            event.setCancelled(true);
        }
    }

    // Check for Player with CreeperItem in 8x8x8 Box
    private boolean checkForPlayer(Entity creeper){
        List<Entity> playernearby = creeper.getNearbyEntities(8D,8D,8D);
        for(Entity entity : playernearby){
            if(entity instanceof Player player) {
                if(hasCreeperItem(player)){
                    return true;
                }
            }
        }
        return false;
    }

    // Check of Itemframe in 50x50x50 Box
    private boolean checkForItemframe(Entity creeper){
        List<Entity> itemframenearby = creeper.getNearbyEntities(50D,50D,50D);
        for(Entity entity : itemframenearby){
            if(entity instanceof ItemFrame itemframe){
                if(itemframe.getItem().hasItemMeta()){
                    if(Objects.equals(itemframe.getItem().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), keyString)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Check if CreeperItem is in Inventory
    private boolean hasCreeperItem(final Player player) {
        final PlayerInventory inv = player.getInventory();
        final ItemStack[] contents = inv.getContents();
        for (final ItemStack stack : contents) {
            if (stack != null && stack.getItemMeta().getPersistentDataContainer().has(Constant.ITEMTYPE) && Objects.equals(stack.getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), keyString)) {
                return true;
            }
        }
        return false;
    }

    //Define FireworkVisual
    private void defineFirework(){
        Firework fw = (Firework) creeper.getWorld().spawnEntity(creeper.getLocation(), EntityType.FIREWORK_ROCKET);
        FireworkMeta fwm = fw.getFireworkMeta();
        Random r = new Random();
        int r1i = r.nextInt(17) + 1;
        int r2i = r.nextInt(17) + 1;
        Color c1 = getColor(r1i);
        Color c2 = getColor(r2i);
        FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(FireworkEffect.Type.BURST).trail(r.nextBoolean()).build();
        fwm.addEffect(effect);
        fw.setFireworkMeta(fwm);
        fw.detonate();
    }
}
