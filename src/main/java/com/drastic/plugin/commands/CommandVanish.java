package com.drastic.plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.drastic.plugin.Main;

public class CommandVanish implements CommandExecutor
{    
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args)
    {
        if(args.length == 1)
        {
            try
            {
                Player p = Bukkit.getPlayer(args[0]);
                
                if(Main.getINSTANCE().invisible_list.contains(p))
                {
                    Main.getINSTANCE().invisible_list.remove(p);
                    p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous êtes de nouveau visible");
                    
                    for(Player player : Bukkit.getOnlinePlayers())
                    {
                        player.showPlayer(Main.getINSTANCE(), p);
                    }
                }
                else if(!Main.getINSTANCE().invisible_list.contains(p))
                {
                    Main.getINSTANCE().invisible_list.add(p);
                    p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous à présent invisible");
                    
                    for(Player player : Bukkit.getOnlinePlayers())
                    {
                        player.hidePlayer(Main.getINSTANCE(), p);
                    }
                }
            }
            catch(NullPointerException e)
            {
                sender.sendMessage(Main.getINSTANCE().getPrefix() + "§cImpossible de trouver le joueur spécifié");
                return false;
            }
        }
        else if(sender instanceof Player)
        {
            Player p = (Player)sender;
            
            if(Main.getINSTANCE().invisible_list.contains(p))
            {
                Main.getINSTANCE().invisible_list.remove(p);
                p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous êtes de nouveau visible");
                
                for(Player player : Bukkit.getOnlinePlayers())
                {
                    player.showPlayer(Main.getINSTANCE(), p);
                }
            }
            else if(!Main.getINSTANCE().invisible_list.contains(p))
            {
                Main.getINSTANCE().invisible_list.add(p);
                p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous à présent invisible");
                
                for(Player player : Bukkit.getOnlinePlayers())
                {
                    player.hidePlayer(Main.getINSTANCE(), p);
                }
            }
        }
        return true;
    }

}
