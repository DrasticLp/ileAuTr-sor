package com.drastic.plugin.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.drastic.plugin.GameStatus;
import com.drastic.plugin.Main;

public class PlayerTeleportListener implements Listener
{
    @EventHandler
    public void onPlayerTeleport(PlayerPortalEvent e)
    {
        if(GameStatus.isStatus(GameStatus.DEATHMATCH))
        {
            if(e.getCause() == TeleportCause.END_PORTAL)
            {
                if(e.getFrom().getWorld().getName().equalsIgnoreCase(Main.getWorldName()))
                {
                    Bukkit.broadcastMessage(Main.getINSTANCE().getPrefix() + "§c" + e.getPlayer().getDisplayName() + " vient d'entrer dans l'§eEND");
                }
                else if(e.getFrom().getWorld().getName().equalsIgnoreCase(Main.getWorldName() + "_the_end"))
                {
                    Bukkit.broadcastMessage(Main.getINSTANCE().getPrefix() + "§c" + e.getPlayer().getDisplayName() + " vient de sortir de l'§eEND");

                    if(e.getPlayer().getBedSpawnLocation() != null)
                        e.setTo(e.getPlayer().getBedSpawnLocation());
                }
            }
        }
        else
        {
            e.getPlayer().sendMessage(Main.getINSTANCE().getPrefix() + "§cVeuillez attendre la phase §9MATCH à MORT");
            e.setCancelled(true);
        }
    }
}
