package de.pascxl.challanges.command;

import de.pascxl.challanges.Main;
import de.pascxl.challanges.modules.IModule;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Pascal K. on 19.06.2023.
 */
public class CommandTimer extends Command {
    public CommandTimer() {
        super("timer");
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {

        switch (strings.length) {

            case 1 -> {
                switch (strings[0].toUpperCase()) {
                    case "STOP" -> {
                        IModule iModule = Main.getInstance().getModuleHandler().getActiveModule();
                        if (iModule == null) {
                            commandSender.sendMessage(Component.text("§cEs ist aktuell kein Modul ausgewählt."));
                            return false;
                        }
                        commandSender.sendMessage(Component.text("§aDer Timer wurde pausiert."));
                        iModule.setTimerPause(true);
                    }
                    case "RESUME" -> {
                        IModule iModule = Main.getInstance().getModuleHandler().getActiveModule();
                        if (iModule == null) {
                            commandSender.sendMessage(Component.text("§cEs ist aktuell kein Modul ausgewählt."));
                            return false;
                        }
                        commandSender.sendMessage(Component.text("§aDer Timer wurde aktiviert."));
                        iModule.setTimerPause(false);
                    }
                    case "RESET" -> {
                        IModule iModule = Main.getInstance().getModuleHandler().getActiveModule();
                        if (iModule == null) {
                            commandSender.sendMessage(Component.text("§cEs ist aktuell kein Modul ausgewählt."));
                            return false;
                        }
                        commandSender.sendMessage(Component.text("§aDer Timer wurde zurückgesetzt."));
                        iModule.setTimerSeconds(0);
                        iModule.setTimerPause(false);
                    }
                }
            }
            case 2 -> {
                switch (strings[0].toUpperCase()) {
                    case "SET" -> {
                        IModule iModule = Main.getInstance().getModuleHandler().getActiveModule();
                        if (iModule == null) {
                            commandSender.sendMessage(Component.text("§cEs ist aktuell kein Modul ausgewählt."));
                            return false;
                        }

                        long seconds = 0L;
                        try {
                            seconds = Long.parseLong(strings[1]);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        commandSender.sendMessage(Component.text("§aDer Timer wurde gesetzt."));
                        iModule.setTimerSeconds(seconds);
                        iModule.setTimerPause(false);
                    }
                }
            }

            default -> {
                commandSender.sendMessage("§6/timer stop");
                commandSender.sendMessage("§6/timer resume");
                commandSender.sendMessage("§6/timer set <seconds>");
                commandSender.sendMessage("§6/timer reset");
            }

        }

        return false;
    }
}
