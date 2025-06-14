package me.powerspieler.paveral.util;

import me.powerspieler.paveral.Paveral;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.logging.Level;

public class StructureLoader {
    public static void loadAncientCityCenter() {
        String path = Bukkit.getServer().getWorlds().get(0).getWorldFolder().getAbsolutePath() + "/datapacks/bukkit/data/minecraft/structures/ancient_city/city_center";
        try {
            Files.walk(Path.of(path)).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
        } catch (IOException ignored) {
            Paveral.getPlugin().getLogger().log(Level.WARNING, "Could not delete / readd Ancient City Center. Is this the first enable?");
        }
        new File(path).mkdirs();

        try (InputStream inputStream = Paveral.getPlugin().getResource("city_center_2.nbt")) {
            if (inputStream == null) {
                Paveral.getPlugin().getLogger().log(Level.SEVERE, "Unable to find city_center_2.nbt in resources!");
                return;
            }

            File targetFile = new File(path, "city_center_2.nbt");
            Files.copy(inputStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            Paveral.getPlugin().getLogger().log(Level.INFO, "Successfully loaded Ancient City Center into: " + targetFile.getAbsolutePath());
        } catch (IOException e) {
            Paveral.getPlugin().getLogger().log(Level.SEVERE, "Error while copying ancient city center:", e);
        }
    }
}
