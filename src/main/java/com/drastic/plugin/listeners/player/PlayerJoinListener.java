package com.drastic.plugin.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;

import com.drastic.plugin.Main;
import com.drastic.plugin.player.GamePlayer;
import com.drastic.plugin.scoreboard.ScoreboardManager;

public class PlayerJoinListener implements Listener
{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();
        
        if(GamePlayer.gamePlayers.containsKey(player.getName()))
        {
            e.setJoinMessage(Main.getINSTANCE().getPrefix() + player.getName() + " s'est reconnecté");
            GamePlayer gp = GamePlayer.gamePlayers.get(player.getName());
            gp.scoreboardManager = new ScoreboardManager(Bukkit.getPlayer(player.getName()));

            gp.scoreboardManager.loadScoreboard();
        }
        else
        {
            new GamePlayer(player.getName());
            e.setJoinMessage(Main.getINSTANCE().getPrefix() + player.getName() + " à rejoint la partie");
            GamePlayer gp = GamePlayer.gamePlayers.get(player.getName());

            gp.scoreboardManager.loadScoreboard();

            if(player.getName().equalsIgnoreCase("YT_DrasticLp") || player.getName().equalsIgnoreCase("misterjmv") || player.getName().equalsIgnoreCase("Seanixxu"))
            {
                gp.isGod = true;
            }
        }

        for(Player p : Bukkit.getOnlinePlayers())
        {
            ScoreboardManager.scoreboardGame.get(p).updateLine(6, ChatColor.WHITE + "Joueurs: " + ChatColor.YELLOW + Bukkit.getOnlinePlayers().size() + "§8/§e" + Bukkit.getMaxPlayers());
        }

        if(!Main.getINSTANCE().perms.containsKey(player.getUniqueId()))
        {
            PermissionAttachment attachment = player.addAttachment(Main.getINSTANCE());
            attachment.setPermission("iot.team", true);
            attachment.setPermission("iot.bank", true);
            attachment.setPermission("iot.link", true);
            Main.getINSTANCE().perms.put(player.getUniqueId(), attachment);
        }

        if(Main.getINSTANCE().invisible_list.contains(player))
        {
            for(Player p : Bukkit.getOnlinePlayers())
            {
                p.hidePlayer(Main.getINSTANCE(), player);
            }
        }

        for(Player p : Main.getINSTANCE().invisible_list)
        {
            if(!player.isOp())
            {
                player.hidePlayer(Main.getINSTANCE(), p);
            }
        }
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent e)
    {

        e.setQuitMessage(Main.getINSTANCE().getPrefix() + "§2" + e.getPlayer().getName() + " s'est déconnecté");
    }
}
