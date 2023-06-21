package de.pascxl.challanges.command;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Pascal K. on 21.06.2023.
 */
public class CommandGive extends Command {
    public CommandGive() {
        super("give");
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        commandSender.sendMessage(Component.text("§cCommand is not active."));
        return false;
    }
}
