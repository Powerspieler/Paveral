package me.powerspieler.paveral.commands;


import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.CustomModelData;
import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.discovery.diaries.AntiCreeperGrief;
import me.powerspieler.paveral.discovery.guide.BaseGuide;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentIteratorFlag;
import net.kyori.adventure.text.ComponentIteratorType;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.LecternInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.logging.Level;


public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof ConsoleCommandSender) {

            TextComponent text = Component.text("Cone")
                    .append(Component.text("fort"))
                    .append(Component.text("fort"))
                    .append(Component.text("fort"))
                    .append(Component.text("fort"));
            List<Component> list = text.children();
            System.out.println(list);


//            Spliterator<Component> test = text.spliterator(ComponentIteratorType.DEPTH_FIRST, ComponentIteratorFlag.INCLUDE_TRANSLATABLE_COMPONENT_ARGUMENTS);
//            System.out.println();
//            test.tryAdvance(c -> c.c);
//            System.out.println();
//            test.tryAdvance(System.out::println);
//            System.out.println();
//            test.tryAdvance(System.out::println);
//            System.out.println();

            Paveral.getPlugin().getLogger().log(Level.INFO, "Test run");
        }
        if(sender instanceof Player player){
            if(player.isOp()){






//                ItemStack guide = new BaseGuide().build();
//
//                List<String> entries = new ArrayList<>();
//                entries.add("Altar");
//                entries.add("Dis");
//                entries.add("Forge");
//                ItemMeta meta = guide.getItemMeta();
//                meta.getPersistentDataContainer().set(key, Constant.STRING_LIST_DATA_TYPE, entries);
//                guide.setItemMeta(meta);
//                player.getInventory().addItem(guide);
//
//                List<String> result = guide.getItemMeta().getPersistentDataContainer().get(key, Constant.STRING_LIST_DATA_TYPE);
//                if(result != null){
//                    player.sendMessage(Component.text("Entries: " + result.toString()));
//                } else {
//                    player.sendMessage(Component.text("No Entries found!", NamedTextColor.RED));
//                }







                //player.getInventory().addItem(new BaseGuide().build());

//                player.openBook(new AntiCreeperGrief().build());
//                player.sendMessage(Paveral.getPlugin().getPluginMeta().getVersion());
                // openBook. guide only index when all explored. append in the end if update. but sort index still maybe with comparator string -> int. x






//                ItemStack item = new AntiCreeperGrief().build();
//                player.getInventory().addItem(item);

//                ItemStack itemStack = new ItemStack(Material.WARPED_FUNGUS_ON_A_STICK);
//                itemStack.setData(DataComponentTypes.CUSTOM_MODEL_DATA, CustomModelData.customModelDataString().addString("lightstaff").build());
//                player.getInventory().addItem(itemStack);


//                ItemStack item = new ItemStack(Material.NETHERITE_AXE);
//                ItemMeta itemMeta = item.getItemMeta();
//                itemMeta.setCustomModelData(1);
//                item.setItemMeta(itemMeta);
//                player.getInventory().addItem(item);

//                  player.getInventory().addItem(new BardicInspiration().build());
//
//
//                  String colorString = Material.PURPLE_DYE.translationKey().split(" ", 2)[0];
//                  Bukkit.broadcast(Component.text(colorString));

                  //player.getInventory().addItem(new LumberjacksBass().recipe().result());

//                Bukkit.getScheduler().getPendingTasks().forEach(task -> Bukkit.broadcast(Component.text(task.toString())));
//
//                Bukkit.broadcast(Component.text("Tasks KeySet: \n"));
//                ActionbarStatus.tasks.keySet().forEach(uuid -> Bukkit.broadcast(Component.text(uuid + "\n")));
//                Bukkit.broadcast(Component.text(""));
//                Bukkit.broadcast(Component.text("Map Entries: "));
//                ActionbarStatus.tasks.keySet().stream().map(ActionbarStatus.tasks::get)
//                        .forEach(s -> Bukkit.broadcast(Component.text("Key: " + s.getKey() + ", Task: " + s.getTask() + " ,isCancelled" + s.getTask().isCancelled() + "\n")));
//
//
//                Bukkit.broadcast(Component.text(""));
//                Bukkit.broadcast(Component.text("Vault KeySet: "));
//                ActionbarStatus.statusMessageVault.keySet().forEach(uuid -> Bukkit.broadcast(Component.text(uuid + "\n")));
//                Bukkit.broadcast(Component.text(""));
//                Bukkit.broadcast(Component.text("Personal KeySet: "));
//                UUID uuid = player.getUniqueId();
//                ActionbarStatus.statusMessageVault.get(uuid).keySet().forEach(item -> Bukkit.broadcast(Component.text(item + "\n")));
//                Bukkit.broadcast(Component.text(""));
//                Bukkit.broadcast(Component.text("Personal EntrySet: "));
//                ActionbarStatus.statusMessageVault.get(uuid).entrySet().forEach(item -> Bukkit.broadcast(Component.text(item + "\n")));







//                if(player.getPersistentDataContainer().has(Constant.IS_HOLDING)){
//                    String string = player.getPersistentDataContainer().get(Constant.IS_HOLDING, PersistentDataType.STRING);
//
//                    Bukkit.broadcast(Component.text("Holding: " + string));
//                } else
//                    Bukkit.broadcast(Component.text("Holding nothing!"));




//                ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
//                BookMeta bookmeta = (BookMeta) book.getItemMeta();
//                bookmeta.setAuthor("");
//                bookmeta.setTitle("Diary [#17]");
//                for (int i = 0; i < 210; i++) {
//                    bookmeta.addPages(Component.text("Soos"));
//                }
//                book.setItemMeta(bookmeta);
//                player.getInventory().addItem(book);



                /*
                BlockDisplay errglow = (BlockDisplay) player.getWorld().spawnEntity(player.getLocation(), EntityType.BLOCK_DISPLAY);
                errglow.setBlock(Material.NETHERITE_BLOCK.createBlockData());
                errglow.setGlowing(true);
                errglow.setBrightness(new Display.Brightness(15,15));

                Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
                Team error = scoreboard.registerNewTeam("WA_ERRORTEST");
                error.prefix(Component.text("AMOGUS", NamedTextColor.RED));
                Bukkit.broadcast(Component.text(scoreboard.getTeams().stream().findFirst().get().getName()));
                error.color(NamedTextColor.RED);
                error.addEntity(errglow);
                error.addPlayer(player);

                Bukkit.broadcast(Component.text("Teams: " + scoreboard.getTeams().toString()));
                //scoreboard.getTeams().forEach(t -> Bukkit.broadcast(Component.text(t.toString())));




                //player.getInventory().addItem(new Worldalterer().build());
                //player.getPersistentDataContainer().remove(Constant.WA_POS1);
                //player.getPersistentDataContainer().remove(Constant.WA_POS2);
                //player.getPersistentDataContainer().remove(Constant.WA_FACING);

                /*NamespacedKey soos2 = new NamespacedKey("paveral", "find_diary");
                Advancement adv = Bukkit.getAdvancement(soos2);
                *//*if(player.getAdvancementProgress(adv).isDone()){
                    Bukkit.broadcast(Component.text("isDone!"));
                } else {
                    Bukkit.broadcast(Component.text("notDone!"));
                }*//*

                AdvancementProgress advcom = player.getAdvancementProgress(adv);
                Collection<String> remainingcrit = advcom.getRemainingCriteria();
                for(String crit : remainingcrit){
                    advcom.awardCriteria(crit);
                }*/


                /*NamespacedKey key = ro;
                AdvancementProgress progress = player.getAdvancementProgress(Bukkit.getAdvancement(key));
                for(String criteria : progress.getRemainingCriteria())
                    progress.awardCriteria(criteria);*/



                /*NamespacedKey soos = Bukkit.advancementIterator().next().getKey();
                Bukkit.broadcast(Component.text("soos: " + soos));

                Advancement foof = new Test();
                Bukkit.broadcast(Component.text("Adv: " + foof));
                Bukkit.broadcast(Component.text("Executed!"));
                player.getAdvancementProgress(foof).getRemainingCriteria();
                for(String criteria : player.getAdvancementProgress(foof).getRemainingCriteria())
                    player.getAdvancementProgress(foof).awardCriteria(criteria);*/


/*            if(args.length == 1){
                if(args[0].equalsIgnoreCase("get")){
                    Items test = new Chunkloader();
                    player.getInventory().addItem(test.build());
                }
                if(args[0].equalsIgnoreCase("remove")){
                    player.getChunk().getPersistentDataContainer().remove(CHUNKLOADS);
                }

            } else {
                int value = player.getChunk().getPersistentDataContainer().get(CHUNKLOADS, PersistentDataType.INTEGER);
                Bukkit.broadcast(Component.text("Is Chunkforce loaded?: " + player.getChunk().isForceLoaded()));
                Bukkit.broadcast(Component.text("Chunkloads Value: " + value));
            }*/

            /*
            // Creeperitem Give
            Items anticreeperitem = new AntiCreeperGrief();
            player.getInventory().addItem(anticreeperitem.build());

            // Lightstaff Give
            Items lightStaff = new LightStaff();
            player.getInventory().addItem(lightStaff.build());



            ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
            ItemMeta itemMetap = item.getItemMeta();
            TextComponent test = Component.text("This is one too");
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text("This is a test"));
            lore.add(test);
            itemMetap.lore(lore);
            item.setItemMeta(itemMetap);
            Inventory inventory = Bukkit.createInventory(null, 9*3);
            inventory.addItem(item);
            player.openInventory(inventory);
            */
                player.sendMessage(Component.text("Test run."));
            } else player.sendMessage(Component.text("ERROR: No Op", NamedTextColor.RED));
        }
        return false;
    }
}
