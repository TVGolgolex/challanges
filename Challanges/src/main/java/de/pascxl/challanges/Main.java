package de.pascxl.challanges;

import de.pascxl.challanges.backpack.BackpackManager;
import de.pascxl.challanges.command.CommandBackpack;
import de.pascxl.challanges.command.CommandChallange;
import de.pascxl.challanges.command.CommandTimer;
import de.pascxl.challanges.modules.ModuleHandler;
import de.pascxl.challanges.utils.AnsiColor;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

/**
 * Created by Pascal K. on 19.06.2023.
 */
@Getter
public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    private ModuleHandler moduleHandler;

    private BackpackManager backpackManager;

    @Override
    public void onEnable() {
        instance = this;

        this.getLogger().log(Level.INFO, "==============================================================");
        this.getLogger().log(
                Level.INFO,
                "      ██████ ██   ██  █████  ██      ██       █████  ███    ██  ██████  ███████ ███████ "
        );
        this.getLogger().log(
                Level.INFO,
                "     ██      ██   ██ ██   ██ ██      ██      ██   ██ ████   ██ ██       ██      ██      "
        );
        this.getLogger().log(
                Level.INFO,
                "     ██      ███████ ███████ ██      ██      ███████ ██ ██  ██ ██   ███ █████   ███████ "
        );
        this.getLogger().log(
                Level.INFO,
                "     ██      ██   ██ ██   ██ ██      ██      ██   ██ ██  ██ ██ ██    ██ ██           ██ "
        );
        this.getLogger().log(
                Level.INFO,
                "      ██████ ██   ██ ██   ██ ███████ ███████ ██   ██ ██   ████  ██████  ███████ ███████ "
        );
        this.getLogger().log(Level.INFO, "     ");
        this.getLogger().log(Level.INFO, AnsiColor.GREEN.getCode() + "     --- Startup!" + AnsiColor.RESET.getCode());
        this.getLogger().log(Level.INFO, "     ");
        this.getLogger().log(Level.INFO, "=====================================================================");

        this.moduleHandler = new ModuleHandler();
        this.backpackManager = new BackpackManager();
        this.getServer().getCommandMap().register(this.getName(), new CommandChallange());
        this.getServer().getCommandMap().register(this.getName(), new CommandTimer());
        this.getServer().getCommandMap().register(this.getName(), new CommandBackpack());
    }

    @Override
    public void onDisable() {

        this.getLogger().log(Level.INFO, "==============================================================");
        this.getLogger().log(
                Level.INFO,
                "      ██████ ██   ██  █████  ██      ██       █████  ███    ██  ██████  ███████ ███████ "
        );
        this.getLogger().log(
                Level.INFO,
                "     ██      ██   ██ ██   ██ ██      ██      ██   ██ ████   ██ ██       ██      ██      "
        );
        this.getLogger().log(
                Level.INFO,
                "     ██      ███████ ███████ ██      ██      ███████ ██ ██  ██ ██   ███ █████   ███████ "
        );
        this.getLogger().log(
                Level.INFO,
                "     ██      ██   ██ ██   ██ ██      ██      ██   ██ ██  ██ ██ ██    ██ ██           ██ "
        );
        this.getLogger().log(
                Level.INFO,
                "      ██████ ██   ██ ██   ██ ███████ ███████ ██   ██ ██   ████  ██████  ███████ ███████ "
        );
        this.getLogger().log(Level.INFO, "     ");
        this.getLogger().log(Level.INFO, AnsiColor.RED.getCode() + "     --- Shutdown!" + AnsiColor.RESET.getCode());
        this.getLogger().log(Level.INFO, "     ");
        this.getLogger().log(Level.INFO, "=====================================================================");

        this.backpackManager.save();
        this.moduleHandler.getActiveModule().terminate();

    }
}
