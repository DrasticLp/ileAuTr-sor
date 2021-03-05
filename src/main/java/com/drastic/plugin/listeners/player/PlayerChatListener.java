package com.drastic.plugin.listeners.player;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.drastic.plugin.GameStatus;
import com.drastic.plugin.Main;
import com.drastic.plugin.player.GamePlayer;

public class PlayerChatListener implements Listener
{
    @EventHandler
    public void onPlayerMessage(AsyncPlayerChatEvent e)
    {

        if(GameStatus.isStatus(GameStatus.GAME) || GameStatus.isStatus(GameStatus.DEATHMATCH))
        {
            Player p = e.getPlayer();
            GamePlayer gp = GamePlayer.gamePlayers.get(p.getName());
            
            if(e.getMessage().startsWith("*"))
            {
                if(Main.getINSTANCE().blueTeam.contains(p.getUniqueId()))
                {
                    this.sendMessageToTeam(Main.getINSTANCE().blueTeam, "§6[§2Team§6] §9" + p.getDisplayName()  + " §f» " + e.getMessage().substring(1));
                }
                else if(Main.getINSTANCE().greenTeam.contains(p.getUniqueId()))
                {
                    this.sendMessageToTeam(Main.getINSTANCE().greenTeam, "§6[§2Team§6] §a" + p.getDisplayName()  + " §f» " + e.getMessage().substring(1));
                }
                else if(Main.getINSTANCE().redTeam.contains(p.getUniqueId()))
                {
                    this.sendMessageToTeam(Main.getINSTANCE().redTeam, "§6[§2Team§6] §c" + p.getDisplayName()  + " §f» " + e.getMessage().substring(1));
                }
                
                e.setCancelled(true);
            }
            else
            {
                if(gp.isGod)
                {
                    sendMessage("§6" + p.getDisplayName() + "§2 » §f" + e.getMessage());
                }
                else if(Main.getINSTANCE().redTeam.contains(p.getUniqueId()))
                {
                    sendMessage("§c" + p.getDisplayName() + "§2 » §f" + e.getMessage());
                }
                else if(Main.getINSTANCE().greenTeam.contains(p.getUniqueId()))
                {
                    sendMessage("§a" + p.getDisplayName() + "§2 » §f" + e.getMessage());
                }
                else if(Main.getINSTANCE().blueTeam.contains(p.getUniqueId()))
                {
                    sendMessage("§9" + p.getDisplayName() + "§2 » §f" + e.getMessage());
                }
           }

            e.setCancelled(true);
        }
    }

    public void sendMessageToTeam(List<UUID> team, String s)
    {
        for(UUID id : team)
        {
            Player player = Bukkit.getPlayer(id);
            player.sendMessage(s);
        }
    }

    public void sendMessage(String s)
    {
        for(Player p : Bukkit.getOnlinePlayers())
        {
            p.sendMessage(s);
        }
    }
}
