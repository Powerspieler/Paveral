package me.powerspieler.paveral.util;

import me.powerspieler.paveral.Paveral;
import org.bukkit.Bukkit;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DatapackLoader {
    public static void load(){
        copyAncientCityCenter();
        copyAdvancements();
        Bukkit.reloadData();
    }

    private static void copyAncientCityCenter() {
        String path = Bukkit.getServer().getWorlds().getFirst().getWorldFolder().getAbsolutePath() + "/datapacks/bukkit/data/minecraft/structures/ancient_city/city_center";
        File targetDirectory = new File(path);
        File targetFile = new File(path, "city_center_2.nbt");

        if(!targetDirectory.exists()) {
            targetDirectory.mkdirs();
        }

        try (InputStream inputStream = Paveral.getPlugin().getResource("city_center_2.nbt")) {
            if (inputStream == null) {
                Paveral.getPlugin().getLogger().log(Level.SEVERE, "Unable to find city_center_2.nbt in resources!");
                return;
            }

            if(targetFile.exists()) {
                if(filesAreEqual(inputStream, targetFile)){
                    return;
                }
                inputStream.close();
                InputStream newStream = Paveral.getPlugin().getResource("city_center_2.nbt");
                if(newStream == null) {
                    return;
                }
                Files.copy(newStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                newStream.close();

                Paveral.getPlugin().getLogger().log(Level.INFO, "Saved changes in Ancient City Center into: " + targetFile.getAbsolutePath());
            } else {
                Files.copy(inputStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Paveral.getPlugin().getLogger().log(Level.INFO, "Copied Ancient City Center for the first time! " + targetFile.getAbsolutePath());
            }
        } catch (IOException e) {
            Paveral.getPlugin().getLogger().log(Level.SEVERE, "Error while copying ancient city center:", e);
        }
    }

    private static void copyAdvancements() {
        String path = Bukkit.getServer().getWorlds().getFirst().getWorldFolder().getAbsolutePath() + "/datapacks/bukkit/data/paveral/advancement";
        File targetDirectory = new File(path);

        if (!targetDirectory.exists()) {
            targetDirectory.mkdirs();
        }

        // Liste der bestehenden Dateien im Zielverzeichnis erstellen
        Map<String, File> existingFiles = new HashMap<>();
        if (targetDirectory.exists()) {
            try {
                Files.walk(targetDirectory.toPath())
                        .filter(Files::isRegularFile)
                        .forEach(p -> existingFiles.put(targetDirectory.toPath().relativize(p).toString(), p.toFile()));
            } catch (IOException e) {
                Paveral.getPlugin().getLogger().log(Level.WARNING, "Failed to list existing advancement files", e);
            }
        }

        Set<String> processedFiles = new HashSet<>();

        try {
            // Ressourcenverzeichnis laden
            ClassLoader classloader = DatapackLoader.class.getClassLoader();

            // Advancements aus dem Ressourcenverzeichnis extrahieren
            try (InputStream is = classloader.getResourceAsStream("advancement.zip")) {
                if (is == null) {
                    Paveral.getPlugin().getLogger().log(Level.SEVERE, "advancement.zip not found in resources!");
                    return;
                }

                byte[] buffer = new byte[1024];
                ZipInputStream zis = new ZipInputStream(is);
                ZipEntry entry;

                while ((entry = zis.getNextEntry()) != null) {
                    if (entry.isDirectory()) {
                        continue;
                    }

                    String relativePath = entry.getName();
                    processedFiles.add(relativePath);

                    File targetFile = new File(targetDirectory, relativePath);
                    File parent = targetFile.getParentFile();
                    if (!parent.exists() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }

                    // Überprüfen, ob die Datei bereits existiert und unverändert ist
                    if (targetFile.exists()) {
                        // ZIP-Eintrag in Byte-Array lesen
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            baos.write(buffer, 0, len);
                        }
                        byte[] zipEntryBytes = baos.toByteArray();

                        // Zieldatei in Byte-Array lesen
                        byte[] targetFileBytes = Files.readAllBytes(targetFile.toPath());

                        // Vergleich mit Arrays.equals
                        if (Arrays.equals(zipEntryBytes, targetFileBytes)) {
                            // Dateien sind identisch, nichts zu tun
                            continue;
                        }

                        // Dateien sind unterschiedlich, aus dem bereits gelesenen Byte-Array schreiben
                        try (FileOutputStream fos = new FileOutputStream(targetFile)) {
                            fos.write(zipEntryBytes);
                        }

                        Paveral.getPlugin().getLogger().log(Level.INFO, "Updated advancement: " + relativePath);
                        continue;
                    }

                    // Falls die Datei noch nicht existiert, normal fortfahren
                    try (FileOutputStream fos = new FileOutputStream(targetFile)) {
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }

                    Paveral.getPlugin().getLogger().log(Level.INFO, "Added new advancement: " + relativePath);
                }

                zis.closeEntry();
                zis.close();
            }

            // Dateien entfernen, die nicht mehr in der ZIP vorhanden sind
            for (Map.Entry<String, File> entry : existingFiles.entrySet()) {
                if (!processedFiles.contains(entry.getKey())) {
                    entry.getValue().delete();
                    Paveral.getPlugin().getLogger().log(Level.INFO, "Removed outdated advancement: " + entry.getKey());
                }
            }
        } catch (IOException e) {
            Paveral.getPlugin().getLogger().log(Level.SEVERE, "Error while processing advancements:", e);
        }
    }

    private static boolean filesAreEqual(InputStream source, File target) {
        try {
            byte[] sourceBytes = source.readAllBytes();
            byte[] targetBytes = Files.readAllBytes(target.toPath());

            if (sourceBytes.length != targetBytes.length) {
                return false;
            }

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] sourceHash = md.digest(sourceBytes);
            md.reset();
            byte[] targetHash = md.digest(targetBytes);

            if (sourceHash.length != targetHash.length) {
                return false;
            }

            for (int i = 0; i < sourceHash.length; i++) {
                if (sourceHash[i] != targetHash[i]) {
                    return false;
                }
            }

            return true;
        } catch (IOException | NoSuchAlgorithmException e) {
            Paveral.getPlugin().getLogger().log(Level.WARNING, "Error comparing files:", e);
            return false;
        }
    }
}