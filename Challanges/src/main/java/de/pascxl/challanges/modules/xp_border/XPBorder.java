package de.pascxl.challanges.modules.xp_border;

import de.pascxl.challanges.Main;
import de.pascxl.challanges.coloring.Coloring;
import de.pascxl.challanges.modules.IModule;
import de.pascxl.challanges.utils.Base64;
import de.pascxl.challanges.utils.TablistPrefixUtil;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Created by Pascal K. on 19.06.2023.
 */
@Getter
public class XPBorder implements IModule {

    private Location center;
    @Setter
    private int distance = 1;
    @Setter
    private float exp = 0;
    private int lastDistance = 0;
    private BukkitTask bukkitTask;
    private BukkitTask timeTask;

    private boolean paused = false;

    private long seconds = 0;

    private PlayerListener playerListener = new PlayerListener();

    private XPBorderConfig xpBorderConfig;

    @Override
    public void initial() {

        this.center = new Location(Bukkit.getWorld("world"), 60, 77, 83);

        for (Player all : Bukkit.getOnlinePlayers()) {
            all.showTitle(Title.title(MiniMessage.miniMessage().deserialize("<#d90429>XP BORDER"), Component.empty()));
            all.teleport(center);
            all.setHealth(all.getHealthScale());
            all.setFoodLevel(20);
            all.setLevel(0);
            all.setExp(0);
            all.setGameMode(GameMode.SURVIVAL);
            all.setGlowing(true);
        }

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "worldborder center " + center.getX() + " " + center.getZ());

        this.bukkitTask = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
            if (lastDistance != distance) {
                for (World world : Bukkit.getWorlds()) {
                    world.getWorldBorder().setSize(distance, TimeUnit.SECONDS, 10);
                }
                lastDistance = distance;
            }

            if (!this.paused) {
                seconds++;
            }

            for (Player all : Bukkit.getOnlinePlayers()) {
                Coloring coloring = Main.getInstance().getColoring(all.getUniqueId());
                all.sendActionBar(MiniMessage.miniMessage().deserialize("<gradient:" + coloring.getFirstGradientColor() + ":" + coloring.getSecondGradientColor() + "><b>" + (
                        this.paused ?
                                "Timer pausiert" : this.getIntToTime(this.seconds))));
                all.sendPlayerListHeaderAndFooter(
                        MiniMessage.miniMessage().deserialize("\n<gradient:" + coloring.getFirstGradientColor() + ":" + coloring.getSecondGradientColor() + "><b>CLAYMC.NET</b></gradient>\n<gray>Event<dark_gray>: <" + coloring.getFirstGradientColor() + ">XPBorder\n"),
                        MiniMessage.miniMessage().deserialize("\n<gradient:" + coloring.getFirstGradientColor() + ":" + coloring.getSecondGradientColor() + "><b>" + (
                                this.paused ?
                                        "Timer pausiert" : this.getIntToTime(this.seconds)) + "\n")
                );
            }

            TablistPrefixUtil.getInstance().setPrefix();

        }, 0, 30);

        this.xpBorderConfig = new XPBorderConfig(new File(Main.getInstance().getDataFolder(), "xpborder.yml"));
        try {
            this.xpBorderConfig.init();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        Main.getInstance().getServer().getPluginManager().registerEvents(playerListener, Main.getInstance());
    }

    public void saveConfig() {
        try {
            this.xpBorderConfig.setDistance(this.distance);
            this.xpBorderConfig.setTimerSeconds(this.seconds);
            this.xpBorderConfig.setExp(this.exp);
            for (Player all : Bukkit.getOnlinePlayers()) {
                this.xpBorderConfig.getInventory().put(
                        all.getUniqueId().toString(),
                        Base64.itemStackArrayToBase64(all.getInventory().getContents())
                );
            }
            this.xpBorderConfig.save();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void setTimerPause(boolean b) {
        this.paused = b;
    }

    private String getIntToTime(long i) {
        if (i <= 59) {
            return "00h 00min " + (i <= 9 ? "0" : "") + i + "s";
        }
        int minutes = 0;
        int hours = 0;
        int days = 0;
        while (i >= 60) {
            i -= 60;
            minutes++;
        }
        while (minutes >= 60) {
            minutes -= 60;
            hours++;
        }
        while (hours >= 24) {
            hours -= 24;
            days++;
        }
        return (days <= 9 ? "0" : "") + days + "d " + (hours <= 9 ? "0" : "") + hours + "h " + (minutes <= 9 ? "0" :
                "") + minutes + "min " + (i <= 9 ? "0" :
                "") + i + "s";
    }

    @Override
    public void setTimerSeconds(long l) {
        this.seconds = l;
    }

    @Override
    public void loadFromConfig() {
        this.seconds = this.xpBorderConfig.getTimerSeconds();
        this.distance = this.xpBorderConfig.getDistance();
        this.exp = Float.parseFloat(String.valueOf(this.xpBorderConfig.getExp()));

        for (Player all : Bukkit.getOnlinePlayers()) {
            all.setLevel(this.distance);
            all.setExp(Float.parseFloat(String.valueOf(this.xpBorderConfig.getExp())));
        }
    }

    @Override
    public boolean isTimerPause() {
        return paused;
    }

    @Override
    public void terminate() {
        this.saveConfig();
        for (World world : Bukkit.getWorlds()) {
            world.getWorldBorder().setSize(10000000, TimeUnit.SECONDS, 1);
        }
        HandlerList.unregisterAll(playerListener);
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.setGlowing(false);
        }
    }
}
