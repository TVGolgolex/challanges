package de.pascxl.challanges.modules.xp_border;

import de.pascxl.challanges.Main;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * Created by Pascal K. on 19.06.2023.
 */
public class PlayerListener implements Listener {

    @EventHandler
    public void handle(AsyncChatEvent event) {

    }

    @EventHandler
    public void handle(PlayerExpChangeEvent event) {
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.setExp(event.getPlayer().getExp());
        }
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
    public void handle(PlayerRespawnEvent event) {
        XPBorder xpBorder = Main.getInstance().getModuleHandler().getActiveModule();
        event.setRespawnLocation(xpBorder.getCenter());
    }

}
