package com.drastic.plugin.listeners.player;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.drastic.plugin.GameStatus;
import com.drastic.plugin.Main;
import com.drastic.plugin.player.GamePlayer;
import com.drastic.plugin.utils.ArrowTargetUtils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class PlayerMoveListener implements Listener
{
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e)
    {
        Player player = e.getPlayer();
        String name = player.getName();
        UUID uuid = player.getUniqueId();

        if(GameStatus.isStatus(GameStatus.GAME) || GameStatus.isStatus(GameStatus.DEATHMATCH))
        {
            GamePlayer gp = GamePlayer.gamePlayers.get(name);

            if(!gp.isGod)
            {
                if(gp.team != null)
                {
                    if(!gp.isInBase)
                    {
                        Location tempLoc = gp.region.getMiddle();
                        tempLoc.setY(player.getLocation().getY());

                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§7Votre base: §f" + (int)player.getLocation().distance(tempLoc) + " blocks " + getArrowColor(player.getLocation().distance(tempLoc)) + ArrowTargetUtils.calculateArrow(player, tempLoc)));
                    }
                    else
                    {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§bVous êtes dans votre base"));
                    }
                }

                // red
                if(Main.getINSTANCE().redBase.isInArea(player.getLocation()))
                {
                    if(Main.getINSTANCE().redTeam.contains(uuid))
                    {
                        if(!gp.isInBase)
                        {
                            gp.isInBase = true;
                            Main.getINSTANCE().redPoints += gp.getPoints();
                            if(gp.getPoints() > 0)
                            {
                                player.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous venez de déposer §6" + gp.getPoints() + " §2points");
                            }
                            gp.setPoints(0);

                            player.sendMessage(Main.getINSTANCE().getPrefix() + "§bVous entrez dans votre base");
                        }
                    }
                }
                else
                {
                    if(Main.getINSTANCE().redTeam.contains(uuid))
                    {
                        if(gp.isInBase)
                        {
                            gp.isInBase = false;
                            player.sendMessage(Main.getINSTANCE().getPrefix() + "§bVous sortez de votre base");
                        }
                    }
                }

                // blue
                if(Main.getINSTANCE().blueBase.isInArea(player.getLocation()))
                {
                    if(Main.getINSTANCE().blueTeam.contains(uuid))
                    {
                        if(!gp.isInBase)
                        {
                            gp.isInBase = true;
                            Main.getINSTANCE().bluePoints += gp.getPoints();
                            if(gp.getPoints() > 0)
                            {
                                player.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous venez de déposer §6" + gp.getPoints() + " §2points");
                            }
                            gp.setPoints(0);
                            player.sendMessage(Main.getINSTANCE().getPrefix() + "§bVous entrez dans votre base");
                        }
                    }
                }
                else
                {
                    if(Main.getINSTANCE().blueTeam.contains(uuid))
                    {
                        if(gp.isInBase)
                        {
                            gp.isInBase = false;
                            player.sendMessage(Main.getINSTANCE().getPrefix() + "§bVous sortez de votre base");
                        }
                    }
                }

                // green
                if(Main.getINSTANCE().greenBase.isInArea(player.getLocation()))
                {
                    if(Main.getINSTANCE().greenTeam.contains(uuid))
                    {
                        if(!gp.isInBase)
                        {
                            gp.isInBase = true;
                            Main.getINSTANCE().greenPoints += gp.getPoints();
                            if(gp.getPoints() > 0)
                            {
                                player.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous venez de déposer §6" + gp.getPoints() + " §2points");
                            }
                            gp.setPoints(0);
                            player.sendMessage(Main.getINSTANCE().getPrefix() + "§bVous entrez dans votre base");
                        }
                    }
                }
                else
                {
                    if(Main.getINSTANCE().greenTeam.contains(uuid))
                    {
                        if(gp.isInBase)
                        {
                            gp.isInBase = false;
                            player.sendMessage(Main.getINSTANCE().getPrefix() + "§bVous sortez de votre base");
                        }
                    }
                }
            }
        }
    }

    public String getArrowColor(double d)
    {
        if(d >= 100)
            return "§c";
        if(d > 20 && d < 100)
            return "§e";
        if(d <= 20)
            return "§a";
        return "§a";
    }
}
