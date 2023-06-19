package de.pascxl.challanges.backpack;

import de.pascxl.challanges.Main;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * JavaDoc this file!
 * Created: 09.11.2022
 *
 * @author GolgolexTV (golgolex@golgolex.de)
 */
@Getter
public class BackpackManager {
    private final Map<UUID, Backpack> map;

    private final UUID systemUuid = UUID.fromString("00000000-0000-0000-0000-000000000000");

    private final File file;

    private final YamlConfiguration config;

    public BackpackManager() {
        this.map = new HashMap<>();
        this.file = new File("./plugins/" + Main.getInstance().getDescription().getName(), "backpacks.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
        this.load();
    }

    public Backpack getBackpack(UUID uniqueId) {
        if (this.map.containsKey(uniqueId)) {
            return map.get(uniqueId);
        }
        Backpack backpack = new Backpack(uniqueId);
        this.map.put(backpack.getUuid(), backpack);
        this.save();
        return backpack;
    }

    public void setBackpack(Backpack backpack) {
        map.put(backpack.getUuid(), backpack);
    }

    public void load() {
        List<String> uuids = this.config.getStringList("backpack-ids");
        uuids.forEach(s -> {
            UUID uuid = UUID.fromString(s);
            String base64 = this.config.getString(uuid.toString());
            try {
                this.map.put(uuid, new Backpack(uuid, base64));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void save() {

        List<String> uuids = new ArrayList<>();

        for (UUID uuid : this.map.keySet()) {
            uuids.add(uuid.toString());
        }

        this.config.set("backpack-ids", uuids);
        this.map.forEach((uuid, backpack) -> this.config.set(uuid.toString(), backpack.toBase64()));
        try {
            this.config.save(this.file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
