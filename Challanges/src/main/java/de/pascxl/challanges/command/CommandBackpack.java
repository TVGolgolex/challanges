package de.pascxl.challanges.command;

import de.pascxl.challanges.Main;
import de.pascxl.challanges.backpack.Backpack;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Pascal K. on 19.06.2023.
 */
public class CommandBackpack extends Command {
    public CommandBackpack() {
        super("backpack");
        setAliases(List.of("bp"));
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {

        Backpack backpack = Main.getInstance().getBackpackManager().getBackpack(Main.getInstance().getBackpackManager().getSystemUuid());
        ((Player) commandSender).openInventory(backpack.getInventory());

        return false;
    }
}
