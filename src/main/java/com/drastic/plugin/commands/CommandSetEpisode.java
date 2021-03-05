package com.drastic.plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.drastic.plugin.GameStatus;
import com.drastic.plugin.Main;
import com.drastic.plugin.utils.GuiUtil;

public class CommandSetEpisode implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args)
    {
        try
        {
            Main.getINSTANCE().playerUpdateRunnable.episode = Integer.parseInt(args[0]);

            Main.getINSTANCE().playerUpdateRunnable.timer = 20 * 60;

            if(Integer.parseInt(args[0]) >= 4)
            {
                GameStatus.setSatus(GameStatus.DEATHMATCH);

                for(Player p : Bukkit.getOnlinePlayers())
                {
                    p.sendMessage(Main.getINSTANCE().getPrefix() + "§9Début de la phase §cDEATHMATCH");
                    p.playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 10f, 1f);
                    GuiUtil.spawnPnj();
                }
            }
            else if(Integer.parseInt(args[0]) >= 2)
            {
                for(Player p : Bukkit.getOnlinePlayers())
                {
                    p.sendMessage(Main.getINSTANCE().getPrefix() + "§9Le PvP vient d'être activé");
                    p.playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 10f, 1f);
                    p.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 1, 5));
                }
            }

            if(Integer.parseInt(args[0]) > 1)
            {
                Main.getINSTANCE().invincibility = false;
            }
            
            for(Player p : Bukkit.getOnlinePlayers())
            {
                p.sendMessage(Main.getINSTANCE().getPrefix() + "§9Début de l'épisode §6" + Main.getINSTANCE().playerUpdateRunnable.episode);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10f, 1f);
            }

            return true;
        }
        catch(NumberFormatException e)
        {
            sender.sendMessage(Main.getINSTANCE().getPrefix() + "§cVeuillez entrer un nombre valide");
            return false;
        }
    }

}
