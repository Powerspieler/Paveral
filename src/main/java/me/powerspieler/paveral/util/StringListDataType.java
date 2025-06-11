package me.powerspieler.paveral.util;

import me.powerspieler.paveral.Paveral;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

class StringListDataType implements PersistentDataType<byte[], List<String>> {
    @Override
    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public @NotNull Class<List<String>> getComplexType() {
        return (Class<List<String>>) (Class<?>) List.class;
    }

    @Override
    public byte @NotNull [] toPrimitive(@NotNull List<String> strings, @NotNull PersistentDataAdapterContext context) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             DataOutputStream dos = new DataOutputStream(bos)) {

            dos.writeInt(strings.size());
            for (String str : strings) {
                dos.writeUTF(str);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            Paveral.getPlugin().getLogger().log(Level.SEVERE, "Error occured in StringListDataType! Could not write byte array", e);
            return new byte[0];
        }
    }

    @Override
    public @NotNull List<String> fromPrimitive(byte @NotNull [] bytes, @NotNull PersistentDataAdapterContext context) {
        List<String> strings = new ArrayList<>();
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             DataInputStream dis = new DataInputStream(bis)) {

            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                strings.add(dis.readUTF());
            }
        } catch (IOException e) {
            Paveral.getPlugin().getLogger().log(Level.SEVERE, "Error occured in StringListDataType! Could not read byte array", e);
        }
        return strings;
    }
}
