package de.pascxl.challanges;

import de.pascxl.challanges.backpack.BackpackManager;
import de.pascxl.challanges.coloring.Coloring;
import de.pascxl.challanges.coloring.ColoringConfig;
import de.pascxl.challanges.coloring.ColoringConverter;
import de.pascxl.challanges.command.*;
import de.pascxl.challanges.modules.ModuleHandler;
import de.pascxl.challanges.utils.AnsiColor;
import de.pascxl.challanges.utils.TablistPrefixUtil;
import de.pascxl.challanges.utils.config.InvalidConfigurationException;
import de.pascxl.challanges.utils.config.InvalidConverterException;
import lombok.Getter;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.UUID;
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

    private ColoringConfig coloringConfig;

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

        this.coloringConfig = new ColoringConfig(new File(this.getDataFolder(), "coloring.yml"));
        try {
            coloringConfig.addConverter(ColoringConverter.class);
            coloringConfig.init();
        } catch (InvalidConfigurationException | InvalidConverterException e) {
            e.printStackTrace();
        }

        new TablistPrefixUtil();

        this.getServer().getCommandMap().register(this.getName(), new CommandChallange());
        this.getServer().getCommandMap().register(this.getName(), new CommandTimer());
        this.getServer().getCommandMap().register(this.getName(), new CommandBackpack());
        this.getServer().getCommandMap().register(this.getName(), new CommandColor());
        this.getServer().getCommandMap().register(this.getName(), new CommandGive());
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

    public Coloring getColoring(UUID uuid) {
        if (!this.coloringConfig.getColoringMap().containsKey(uuid.toString())) {
            return new Coloring("#F931EC", "#6f7bf2", NamedTextColor.LIGHT_PURPLE);
        }
        return this.coloringConfig.getColoringMap().get(uuid.toString());
    }

}
