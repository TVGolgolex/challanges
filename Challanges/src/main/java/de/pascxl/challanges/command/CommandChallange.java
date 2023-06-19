package de.pascxl.challanges.command;

import de.pascxl.challanges.Main;
import de.pascxl.challanges.modules.IModule;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Created by Pascal K. on 19.06.2023.
 */
public class CommandChallange extends Command {
    public CommandChallange() {
        super("challange");
        setAliases(Arrays.asList("chall", "ch"));
        setPermission("de.pascxl.challange.command.challange");
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if (!testPermission(commandSender)) return false;

        switch (strings.length) {
            default -> {
                commandSender.sendMessage(Component.text("§7Folgende Challanges existieren"));
                for (IModule module : Main.getInstance().getModuleHandler().getModules())
                    commandSender.sendMessage(Component.text(" §8- §b" + module.getClass().getSimpleName()));
            }
            case 1 -> {
                if (strings[0].equalsIgnoreCase("start")) {

                    if (Main.getInstance().getModuleHandler().getActiveModule() == null) {
                        commandSender.sendMessage(Component.text(
                                "§cEs kann nicht gestartet werden, da kein Module ausgewählt ist."));
                        return false;
                    }

                    try {
                        Main.getInstance().getModuleHandler().getActiveModule().initial();
                        commandSender.sendMessage(Component.text("§aDas Modul wurde aktiviert."));
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        commandSender.sendMessage(Component.text("§cEs ist ein Fehler aufgetreten!"));
                    }

                    return true;
                }

                IModule iModule = Main.getInstance().getModuleHandler().getModules().stream().filter(iModule1 -> iModule1.getClass().getSimpleName().equalsIgnoreCase(
                        strings[0])).findFirst().orElse(null);

                if (iModule == null) {
                    commandSender.sendMessage(Component.text("§cKein Module unter dem Namen gefunden!"));
                    return false;
                }

                Main.getInstance().getModuleHandler().setActiveModule(iModule);
                commandSender.sendMessage(Component.text("§aNeues Module ausgewählt!").append(Component.text(" §7[§e/challange start§7]")));

            }
        }

        return false;
    }
}
