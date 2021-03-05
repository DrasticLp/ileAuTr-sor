package com.drastic.plugin.listeners.entity;

import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.drastic.plugin.utils.GuiUtil;

public class EntityInteractListener implements Listener
{
    @EventHandler
    public void onClickVillager(PlayerInteractEntityEvent e)
    {
        if(e.getRightClicked() instanceof Villager)
        {
            Villager v = (Villager)e.getRightClicked();

            if(v.getCustomName() != null)
            {
                if(v.getCustomName().equalsIgnoreCase("§9Marchand"))
                {
                    e.setCancelled(true);
                    GuiUtil.openBuyGui(e.getPlayer());
                }
            }
        }
    }
}
