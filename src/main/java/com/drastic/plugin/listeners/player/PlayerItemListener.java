package com.drastic.plugin.listeners.player;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import com.drastic.plugin.GameStatus;
import com.drastic.plugin.Main;
import com.drastic.plugin.player.GamePlayer;

public class PlayerItemListener implements Listener
{
    @EventHandler
    public void onItemLoot(PlayerDropItemEvent e)
    {
        if(GameStatus.isStatus(GameStatus.GAME) || GameStatus.isStatus(GameStatus.DEATHMATCH))
        {
            if(e.getItemDrop().getItemStack().hasItemMeta() && e.getItemDrop().getItemStack().getItemMeta().hasDisplayName())
            {

                String s = e.getItemDrop().getItemStack().getItemMeta().getDisplayName();
                if(s.equalsIgnoreCase("§9Arc de l'Archer") || s.equalsIgnoreCase("§2Plastron du Guerrier") || s.equalsIgnoreCase("§cÉpée de l'Assassin"))
                {
                    e.getPlayer().sendMessage(Main.getINSTANCE().getPrefix() + "§cVous ne pouvez pas jeter cet item");
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent e)
    {
        if(e.getEntity() instanceof Player)
        {
            Player p = (Player)e.getEntity();
            GamePlayer gp = GamePlayer.gamePlayers.get(p.getName());

            ItemStack current = e.getItem().getItemStack();

            if(!gp.isGod && current != null && current.getItemMeta().getDisplayName() != null)
            {
                if(current.getItemMeta().getDisplayName().startsWith("§fPoints:") && current.getType() == Material.ANVIL)
                {
                    String s = current.getItemMeta().getDisplayName().substring(12);
                    e.setCancelled(true);
                    e.getItem().remove();

                    try
                    {
                        gp.addPoints(Integer.parseInt(s));
                    }
                    catch(NumberFormatException err)
                    {
                        err.printStackTrace();
                    }
                }
            }
        }
    }
}
