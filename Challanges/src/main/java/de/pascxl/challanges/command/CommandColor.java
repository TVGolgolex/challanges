package de.pascxl.challanges.command;

import de.pascxl.challanges.Main;
import de.pascxl.challanges.coloring.Coloring;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.naming.Name;

/**
 * Created by Pascal K. on 20.06.2023.
 */
public class CommandColor extends Command {
    public CommandColor() {
        super("color");
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {

        if (strings.length == 1 && strings[0].equalsIgnoreCase("reset")) {
            Main.getInstance().getColoringConfig().getColoringMap().remove(((Player) commandSender).getUniqueId().toString());
            Main.getInstance().getColoringConfig().saveConfig();
            commandSender.sendMessage(Component.text("§aColoring zurückgesetzt+!"));
            return false;
        }

        if (strings.length != 3) {
            commandSender.sendMessage(Component.text("§cFehlerhafte Angabe: /color <firstcolor> <secondcolor> <NamedTextColor>"));
        }

        NamedTextColor namedTextColor = NamedTextColor.namedColor(Integer.parseInt(strings[2]));

        if (namedTextColor == null) {
            commandSender.sendMessage(Component.text("RED: " + NamedTextColor.RED.value()));
            commandSender.sendMessage(Component.text("AQUA: " + NamedTextColor.AQUA.value()));
            commandSender.sendMessage(Component.text("BLACK: " + NamedTextColor.BLACK.value()));
            commandSender.sendMessage(Component.text("BLUE: " + NamedTextColor.BLUE.value()));
            commandSender.sendMessage(Component.text("DARK_AQUA: " + NamedTextColor.DARK_AQUA.value()));
            commandSender.sendMessage(Component.text("DARK_BLUE: " + NamedTextColor.DARK_BLUE.value()));
            commandSender.sendMessage(Component.text("DARK_GRAY: " + NamedTextColor.DARK_GRAY.value()));
            commandSender.sendMessage(Component.text("DARK_GREEN: " + NamedTextColor.DARK_GREEN.value()));
            commandSender.sendMessage(Component.text("DARK_PURPLE: " + NamedTextColor.DARK_PURPLE.value()));
            commandSender.sendMessage(Component.text("DARK_RED: " + NamedTextColor.DARK_RED.value()));
            commandSender.sendMessage(Component.text("GOLD: " + NamedTextColor.GOLD.value()));
            commandSender.sendMessage(Component.text("GRAY: " + NamedTextColor.GRAY.value()));
            commandSender.sendMessage(Component.text("GREEN: " + NamedTextColor.GREEN.value()));
            commandSender.sendMessage(Component.text("LIGHT_PURPLE: " + NamedTextColor.LIGHT_PURPLE.value()));
            commandSender.sendMessage(Component.text("WHITE: " + NamedTextColor.WHITE.value()));
            commandSender.sendMessage(Component.text("YELLOW: " + NamedTextColor.YELLOW.value()));
            return false;
        }

        String first = strings[0].startsWith("#") ? "" : "#" + strings[0];
        String second = strings[1].startsWith("#") ? "" : "#" + strings[1];

        Coloring coloring = new Coloring(first, second, namedTextColor);

        Main.getInstance().getColoringConfig().getColoringMap().put(((Player) commandSender).getUniqueId().toString(), coloring);
        Main.getInstance().getColoringConfig().saveConfig();

        commandSender.sendMessage(Component.text("§aColoring wird angewendet!"));

        commandSender.sendMessage(MiniMessage.miniMessage().deserialize("<gradient:" + coloring.getFirstGradientColor() + ":" + coloring.getSecondGradientColor() + "><b>Gradient"));

        return false;
    }
}
