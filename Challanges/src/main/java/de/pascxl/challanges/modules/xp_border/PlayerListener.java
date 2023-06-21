package de.pascxl.challanges.modules.xp_border;

import de.pascxl.challanges.Main;
import de.pascxl.challanges.coloring.Coloring;
import de.pascxl.challanges.utils.Base64;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Pascal K. on 19.06.2023.
 */
public class PlayerListener implements Listener {

    @EventHandler
    public void handle(@NotNull AsyncChatEvent event) {
        event.setCancelled(true);
        Coloring coloring = Main.getInstance().getColoring(event.getPlayer().getUniqueId());
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.sendMessage(MiniMessage.miniMessage().deserialize("<gradient:" + coloring.getFirstGradientColor() + ":" + coloring.getSecondGradientColor() + "><b>" + event.getPlayer().getName() + "</b><gray>: ").append(
                    event.message()));
        }
    }

    @EventHandler
    public void handle(@NotNull PlayerGameModeChangeEvent e) {
        if (e.getNewGameMode() == GameMode.SURVIVAL) {
            e.setCancelled(false);
            return;
        }
        Bukkit.getOnlinePlayers().stream().filter(player -> player.getName().equalsIgnoreCase("GolgolexTV"))
                .findFirst().ifPresentOrElse(
                        player -> {
                            player.sendMessage(Component.text("§d" + e.getPlayer().getName() + " §7ist nun im Gamemode: §a" + e.getNewGameMode().name()));
                            e.setCancelled(false);
                        }, () -> {
                            e.getPlayer().sendMessage(Component.text("§cNicht erlaubt!"));
                            e.setCancelled(true);
                        }
                );
    }

    @EventHandler
    public void handle(@NotNull PlayerQuitEvent event) {
        XPBorder xpBorder = Main.getInstance().getModuleHandler().getActiveModule();
        if ((Bukkit.getOnlinePlayers().size() - 1) < 1) {
            xpBorder.setTimerPause(true);
        }
        xpBorder.saveConfig();
    }

    @EventHandler
    public void handle(@NotNull PlayerJoinEvent event) {
        XPBorder xpBorder = Main.getInstance().getModuleHandler().getActiveModule();
        event.getPlayer().setGlowing(true);
        event.getPlayer().setLevel(xpBorder.getDistance());

    }

    @EventHandler
    public void handle(PlayerExpChangeEvent event) {
        XPBorder xpBorder = Main.getInstance().getModuleHandler().getActiveModule();
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.setExp(event.getPlayer().getExp());
            }
        }, 2);
        xpBorder.setExp(event.getPlayer().getExp());
        xpBorder.saveConfig();
    }

    @EventHandler
    public void handle(PlayerLevelChangeEvent event) {
        XPBorder xpBorder = Main.getInstance().getModuleHandler().getActiveModule();
        xpBorder.setDistance(event.getNewLevel());
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.setLevel(event.getNewLevel());
            all.setExp(0);
        }
    }

    @EventHandler
    public void handle(PlayerDeathEvent event) {
        XPBorder xpBorder = Main.getInstance().getModuleHandler().getActiveModule();
        xpBorder.setDistance((xpBorder.getDistance() - 10));
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.setLevel((all.getLevel() - 10));
            all.setExp(0);
        }
    }

    @EventHandler
    public void handle(@NotNull PlayerRespawnEvent event) {
        XPBorder xpBorder = Main.getInstance().getModuleHandler().getActiveModule();
        event.setRespawnLocation(xpBorder.getCenter());
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.setLevel(xpBorder.getDistance());
            all.setExp(xpBorder.getExp());
        }
    }

}
