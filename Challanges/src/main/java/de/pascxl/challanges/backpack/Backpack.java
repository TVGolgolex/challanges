package de.pascxl.challanges.backpack;

import de.pascxl.challanges.utils.Base64;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.io.IOException;
import java.util.UUID;

/**
 * JavaDoc this file!
 * Created: 09.11.2022
 *
 * @author GolgolexTV (golgolex@golgolex.de)
 */
public class Backpack {
    private final UUID uuid;
    private final Inventory inventory;

    public Backpack(UUID uuid) {
        this.uuid = uuid;
        this.inventory = Bukkit.createInventory(null, 54, MiniMessage.miniMessage().deserialize("<gradient:#F931EC:#6f7bf2>Backpack"));
    }

    public Backpack(UUID uuid, String base64) throws IOException {
        this.uuid = uuid;
        this.inventory = Bukkit.createInventory(null, 54, MiniMessage.miniMessage().deserialize("<gradient:#F931EC:#6f7bf2>Backpack"));
        this.inventory.setContents(Base64.itemStackArrayFromBase64(base64));
    }

    public UUID getUuid() {
        return uuid;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String toBase64() {
        return Base64.itemStackArrayToBase64(this.inventory.getContents());
    }

}
