package me.powerspieler.paveral.util;

import org.bukkit.Bukkit;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class AdvancementLoader {
    public static void copyAdvancements() {

        String path = Bukkit.getServer().getWorlds().get(0).getWorldFolder().getAbsolutePath() + "/datapacks/bukkit/data/paveral/advancement";

        try {
            Files.walk(Path.of(path)).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
        } catch (IOException ignored) {
            Bukkit.getLogger().log(Level.WARNING, "Could not delete / readd Paveral's advancements. Is this the first enable?");
        }

        new File(path).mkdirs();

        ClassLoader classloader = AdvancementLoader.class.getClassLoader() == null ? Thread.currentThread().getContextClassLoader() : AdvancementLoader.class.getClassLoader();

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
}
