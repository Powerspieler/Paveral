package me.powerspieler.paveral.items.musicpack;

import com.destroystokyo.paper.MaterialTags;
import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.crafting.PaveralIngredient;
import me.powerspieler.paveral.crafting.PaveralRecipe;
import me.powerspieler.paveral.crafting.StandardIngredient;
import me.powerspieler.paveral.items.PaveralItem;
import me.powerspieler.paveral.items.helper.ItemHoldingController;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.text.DecimalFormat;
import java.util.*;

public class PianoSword extends PaveralItem implements Listener {
    private static Component itemName(){
        return Component.text("Rhythms Awakening", NamedTextColor.DARK_PURPLE);
    }

    private static List<Component> lore(){
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Press ", NamedTextColor.DARK_AQUA)
                .decoration(TextDecoration.ITALIC, false)
                .append(Component.keybind("key.mouse.right",NamedTextColor.YELLOW)
                        .decoration(TextDecoration.ITALIC, false))
                .append(Component.text(" to cast a wave of music notes", NamedTextColor.DARK_AQUA)
                        .decoration(TextDecoration.ITALIC, false)));
        return lore;
    }

    public PianoSword(){
        super(Material.NETHERITE_SWORD, 1, Constant.ITEMTYPE, "piano_sword", itemName(), lore());
    }

    @Override
    protected ItemStack build() {
        ItemStack item = super.build();
        ItemMeta itemMeta = item.getItemMeta();
        AttributeModifier attackSpeedModifier = new AttributeModifier(new NamespacedKey(Paveral.getPlugin(), "rhythms_awakening_attack_speed"), -3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND);
        itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, attackSpeedModifier);
        AttributeModifier attackDamageModifier = new AttributeModifier(new NamespacedKey(Paveral.getPlugin(), "rhythms_awakening_attack_damage"), 10.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND);
        itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, attackDamageModifier);
        item.setItemMeta(itemMeta);
        return item;
    }

    @Override
    public PaveralRecipe recipe() {
        Set<StandardIngredient> ingredients = new HashSet<>();
        ingredients.add(new StandardIngredient(Material.NETHERITE_SWORD, 1));
        ingredients.add(new PaveralIngredient(Material.JIGSAW, 1, Constant.ITEMTYPE, "music_core"));
        ingredients.add(new StandardIngredient(Material.BLACKSTONE, 8));
        ingredients.add(new StandardIngredient(Material.QUARTZ, 16));
        return new PaveralRecipe(ingredients, this.build());
    }

    // --- Item Logic ---

    private final HashMap<UUID, Long> cooldown = new HashMap<>();

    @EventHandler
    private void onUse(PlayerInteractEvent event){
        if(ItemHoldingController.checkIsHoldingPaveralItem(event.getPlayer(), keyString)){
            if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
                Player player = event.getPlayer();

                if(!cooldown.containsKey(player.getUniqueId()) || (System.currentTimeMillis() - cooldown.get(player.getUniqueId()) >= 2000)){
                    cooldown.put(player.getUniqueId(), System.currentTimeMillis());

                    Location location = event.getPlayer().getLocation().add(0,0.5,0);
                    org.bukkit.util.Vector vector = location.getDirection().setY(0);

                    playSound(player);
                    vector.rotateAroundY(-0.9d);
                    for (int i = 0; i < 10; i++) {
                        particleBeam(location, vector, player);
                        vector.rotateAroundY(0.2d);
                    }


                    // Cooldown
                    new BukkitRunnable() {
                        final DecimalFormat df = new DecimalFormat("0.000");
                        @Override
                        public void run() {
                            if(ItemHoldingController.checkIsHoldingPaveralItem(player, "piano_sword")){
                                double cooldownsec = ((2000.0 - (System.currentTimeMillis() - cooldown.get(player.getUniqueId()))) / 1000.0);
                                player.sendActionBar(Component.text("[ ", NamedTextColor.GOLD)
                                        .append(Component.text(df.format(cooldownsec), NamedTextColor.RED))
                                        .append(Component.text(" ]",NamedTextColor.GOLD)));
                                if(cooldownsec <= 0){
                                    cancel();
                                    player.sendActionBar(Component.empty());
                                }
                            }
                        }
                    }.runTaskTimer(Paveral.getPlugin(), 0, 1);
                }
            }
        }
    }

    private void particleBeam(Location rawLocation, Vector vector, Player player){
        Location location = new Location(rawLocation.getWorld(), rawLocation.getX(), rawLocation.getY(), rawLocation.getZ());
        vector.normalize();
        vector.multiply(0.5);

        for (int i = 0; i < 14; i++) {
            if(location.getBlock().isSolid()){
                if(!MaterialTags.FENCES.getValues().contains(location.getBlock().getType()) && !MaterialTags.FENCE_GATES.getValues().contains(location.getBlock().getType())){
                    break;
                }
            }
            location.getWorld().spawnParticle(Particle.NOTE, location, 1, 0, 0, 0);

            List<Entity> raw = new ArrayList<>(location.getNearbyEntities(0.3, 0.3, 0.3));
            for (Entity entity : raw) {
                if (entity instanceof LivingEntity target && target.customName() == null && !target.equals(player)) {
                    if(target instanceof Tameable tameable){
                        if(tameable.isTamed()) continue;
                    }
                    if(target.getType() == EntityType.PLAYER){
                        target.damage(5.0, player);
                    } else {
                        target.damage(10.0, player);
                    }
                }
            }
            location.add(vector);
        }
    }

    private void playSound(Player player){
        Audience audience = player.getWorld().filterAudience(member -> member instanceof Player target && target.getLocation().distanceSquared(player.getLocation()) < 100);
        Random random = new Random();
        int number = random.nextInt(1,6);

        switch (number){
            // https://minecraft.wiki/w/Note_Block#Notes
            // NoteBlock: 5,6,8,12
            case 1 -> playTune(new float[]{0.667420f, 0.707107f, 0.793701f, 1.0f}, audience);
            // NoteBlock: 12,8,6,5
            case 2 -> playTune(new float[]{1.0f, 0.793701f, 0.707107f, 0.667420f}, audience);
            // NoteBlock: 7,10,13,16
            case 3 -> playTune(new float[]{0.749154f, 0.890899f, 1.059463f, 1.259921f}, audience);
            // NoteBlock: 16,13,10,7
            case 4 -> playTune(new float[]{1.259921f, 1.059463f, 0.890899f, 0.749154f}, audience);
            // NoteBlock: 0,0,0,10
            case 5 -> playTune(new float[]{0.5f, 0.5f, 0.5f, 0.890899f}, audience);
        }
    }

    private void playTune(float[] tunes, Audience audience){
        new BukkitRunnable() {
            int tuneID = 0;
            @Override
            public void run() {
                audience.playSound(Sound.sound(Key.key("block.note_block.harp"), Sound.Source.AMBIENT, 1f, tunes[tuneID]), Sound.Emitter.self());

                tuneID++;
                if(tuneID >= tunes.length){
                    cancel();
                }
            }
        }.runTaskTimer(Paveral.getPlugin(), 0, 2);
    }
}
