package me.powerspieler.paveral.util;

import me.powerspieler.paveral.Paveral;
import org.bukkit.Bukkit;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DatapackLoader {
    public static void copyAncientCityCenter() {
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

    public static void copyAdvancements() {

        String path = Bukkit.getServer().getWorlds().getFirst().getWorldFolder().getAbsolutePath() + "/datapacks/bukkit/data/paveral/advancement";

        try {
            Files.walk(Path.of(path)).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
        } catch (IOException ignored) {
            Bukkit.getLogger().log(Level.WARNING, "Could not delete / readd Paveral's advancements. Is this the first enable?");
        }

        new File(path).mkdirs();

        ClassLoader classloader = DatapackLoader.class.getClassLoader() == null ? Thread.currentThread().getContextClassLoader() : DatapackLoader.class.getClassLoader();

        try(InputStream is = classloader.getResourceAsStream("advancement.zip")){
            byte[] buffer = new byte[1024];
            ZipInputStream zis = new ZipInputStream(is);
            ZipEntry entry;
            while((entry = zis.getNextEntry()) != null){
                File newFile = new File(path, String.valueOf(entry));
                if (entry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + newFile);
                    }
                } else {
                    // fix for Windows-created archives
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }
                    // write file content
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                }
            }
            zis.closeEntry();
            zis.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        Bukkit.reloadData();
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
