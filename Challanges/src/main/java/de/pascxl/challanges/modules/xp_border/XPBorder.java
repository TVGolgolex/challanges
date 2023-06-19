package de.pascxl.challanges.modules.xp_border;

import de.pascxl.challanges.Main;
import de.pascxl.challanges.modules.IModule;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitTask;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.color.RegularColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pascal K. on 19.06.2023.
 */
@Getter
public class XPBorder implements IModule {

    private Location center;
    @Setter
    private int distance = 1;
    private int lastDistance = 0;
    private BukkitTask bukkitTask;
    private BukkitTask timeTask;

    private boolean paused = false;

    private long seconds = 0;

    private PlayerListener playerListener = new PlayerListener();

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
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "worldborder set " + this.distance + " 1");
                lastDistance = distance;
            }

            if (!this.paused) {
                seconds++;
            }
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.sendActionBar(MiniMessage.miniMessage().deserialize("<gradient:#a95dc0:#6f7bf2>" + (this.paused ? "Timer pausiert"  : this.getIntToTime(this.seconds))));
            }

        }, 0, 20);

        Main.getInstance().getServer().getPluginManager().registerEvents(playerListener, Main.getInstance());
    }

    @Override
    public void setTimerPause(boolean b) {
        this.paused = b;
    }

    private String getIntToTime(long i) {
        if (i <= 59) {
            return "00:" + (i <= 9 ? "0" : "") + i;
        }
        int minutes = 0;
        while (i >= 60) {
            i -= 60;
            minutes++;
        }
        return minutes + ":" + (i <= 9 ? "0" : "") + i;
    }

    @Override
    public void setTimerSeconds(long l) {
        this.seconds = l;
    }

    @Override
    public boolean isTimerPause() {
        return paused;
    }

    @Override
    public void terminate() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "worldborder set 10000000  1");
        HandlerList.unregisterAll(playerListener);
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.setGlowing(false);
        }
    }
}
