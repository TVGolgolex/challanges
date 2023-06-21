package de.pascxl.challanges.utils;

import de.pascxl.challanges.Main;
import de.pascxl.challanges.coloring.Coloring;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by Pascal K. on 21.10.2022.
 */
public class TablistPrefixUtil {

    private static volatile TablistPrefixUtil instance;

    public TablistPrefixUtil() {
        instance = this;
    }

    public static TablistPrefixUtil getInstance() {
        return instance;
    }

    public void setPrefix() {
        for (Player all : Bukkit.getOnlinePlayers()) {
            this.updatePrefix(all);
        }
    }

    public void updatePrefix(Player player) {
        if (player == null) return;
        Coloring coloring = Main.getInstance().getColoring(player.getUniqueId());
        String scoreName = /*sort +*/ player.getName();
        for (Player all : Bukkit.getOnlinePlayers()) {

            /*int sort = (teamEntry.map(uuidTeamEntry -> uuidTeamEntry.getValue().getSortId()).orElse(99));
            String prefix = (Main.getInstance().getAdminDuty().contains(player.getUniqueId()) ?
                    "§4§lA-DUTY§r §7| §4" :
                    (teamEntry.map(uuidTeamEntry -> "§f[" + uuidTeamEntry.getValue().getColorCode().getCode() + uuidTeamEntry.getValue().getTag() + "§f] §f").orElse("§7")));

            ChatColor chatColor = (Main.getInstance().getAdminDuty().contains(player.getUniqueId()) ? ChatColor.DARK_RED : ChatColor.WHITE);*/



            org.bukkit.scoreboard.Team scoreTeam = all.getScoreboard().getTeam(scoreName);
            if (scoreTeam == null) {
                scoreTeam = all.getScoreboard().registerNewTeam(scoreName);
            }

/*            scoreTeam.setPrefix(prefix);
            scoreTeam.setColor(chatColor);
            scoreTeam.setSuffix(" §e" + PlayerService.getInstance().getPlayer(player.getUniqueId())
                    .getProperty(PlayerProperty.DEATHS));*/
            scoreTeam.color(coloring.getNamedTextColor());
            scoreTeam.displayName(MiniMessage.miniMessage().deserialize("<gradient:" + coloring.getFirstGradientColor() + ":" + coloring.getSecondGradientColor() + "><b>" + player.getName()));

            scoreTeam.addEntry(player.getName());
            player.displayName(MiniMessage.miniMessage().deserialize("<gradient:" + coloring.getFirstGradientColor() + ":" + coloring.getSecondGradientColor() + "><b>" + player.getName()));
        }
    }

}
