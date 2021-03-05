package com.drastic.plugin.listeners.player;

import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import com.drastic.plugin.GameStatus;
import com.drastic.plugin.Main;
import com.drastic.plugin.player.GamePlayer;

public class PlayerDamageListener implements Listener
{
    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent e)
    {
        if(e.getEntity() instanceof Player)
        {
            Player damaged = (Player)e.getEntity();

            if(!Main.getINSTANCE().invincibility)
            {
                if(GameStatus.isStatus(GameStatus.GAME) || GameStatus.isStatus(GameStatus.DEATHMATCH))
                {
                    if(e.getDamager() instanceof Player)
                    {
                        if(Main.getINSTANCE().playerUpdateRunnable.episode == 1)
                        {
                            e.setCancelled(true);
                        }
                        
                        GamePlayer gp = GamePlayer.gamePlayers.get(damaged.getName());

                        Player damager = (Player)e.getDamager();

                        if(gp.team.contains(damager.getUniqueId()))
                        {
                            damager.sendMessage(Main.getINSTANCE().getPrefix() + "§cVous ne pouvez pas frapper un allié");
                            e.setCancelled(true);
                        }
                    }
                }
            }
            else
            {
                e.setCancelled(true);
            }
        }
        else
        {
            if(e.getEntity() instanceof LivingEntity && e.getDamage() >= ((LivingEntity)e.getEntity()).getHealth())
            {                
                if(e.getDamager() instanceof Player)
                {
                    Player p = (Player)e.getDamager();
                    GamePlayer gp = GamePlayer.gamePlayers.get(p.getName());

                    if(!gp.isGod)
                    {
                        if(e.getEntity() instanceof Creeper)
                        {
                            gp.addPoints(4);
                        }
                        else if(e.getEntity() instanceof Skeleton)
                        {
                            gp.addPoints(2);
                        }
                        else if(e.getEntity() instanceof Zombie)
                        {
                            gp.addPoints(2);
                        }
                        else if(e.getEntity() instanceof Spider)
                        {
                            gp.addPoints(3);
                        }
                        else if(e.getEntity() instanceof CaveSpider)
                        {
                            gp.addPoints(4);
                        }
                        else if(e.getEntity() instanceof Cow)
                        {
                            gp.addPoints(1);
                        }
                        else if(e.getEntity() instanceof Pig)
                        {
                            gp.addPoints(1);
                        }
                        else if(e.getEntity() instanceof Chicken)
                        {
                            gp.addPoints(1);
                        }
                        else if(e.getEntity() instanceof Horse)
                        {
                            gp.addPoints(3);
                        }
                        else if(e.getEntity() instanceof Sheep)
                        {
                            gp.addPoints(1);
                        }
                        else if(e.getEntity() instanceof Witch)
                        {
                            gp.addPoints(5);
                        }
                    }
                }
            }
            
            if(e.getEntity() instanceof Villager)
            {
                if(e.getEntity().getCustomName() != null && e.getEntity().getCustomName().equalsIgnoreCase("§9Marchand"))
                {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDamageByEnvironnement(EntityDamageEvent e)
    {
        if(e.getEntity() instanceof Player)
        {
            if(Main.getINSTANCE().invincibility)
            {
                e.setCancelled(true);
            }
        }
    }
}
