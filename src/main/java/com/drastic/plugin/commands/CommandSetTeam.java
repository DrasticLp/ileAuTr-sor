package com.drastic.plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.drastic.plugin.Main;
import com.drastic.plugin.player.GamePlayer;

public class CommandSetTeam implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args)
    {
        if(args.length == 2)
        {
            try
            {
                Player p = Bukkit.getPlayer(args[0]);
                GamePlayer gp = GamePlayer.gamePlayers.get(p.getName());

                if(args[1].equalsIgnoreCase("red"))
                {
                    Main.getINSTANCE().redTeam.add(p.getUniqueId());
                    if(Main.getINSTANCE().greenTeam.contains(p.getUniqueId()))
                    {
                        Main.getINSTANCE().greenTeam.remove(p.getUniqueId());
                    }
                    else if(Main.getINSTANCE().blueTeam.contains(p.getUniqueId()))
                    {
                        Main.getINSTANCE().blueTeam.remove(p.getUniqueId());
                    }

                    gp.region = Main.getINSTANCE().redBase;
                    
                    gp.isGod = false;
                }
                else if(args[1].equalsIgnoreCase("blue"))
                {
                    Main.getINSTANCE().blueTeam.add(p.getUniqueId());
                    if(Main.getINSTANCE().greenTeam.contains(p.getUniqueId()))
                    {
                        Main.getINSTANCE().greenTeam.remove(p.getUniqueId());
                    }
                    else if(Main.getINSTANCE().redTeam.contains(p.getUniqueId()))
                    {
                        Main.getINSTANCE().redTeam.remove(p.getUniqueId());
                    }
                    
                    gp.region = Main.getINSTANCE().blueBase;
                    
                    gp.isGod = false;
                }
                else if(args[1].equalsIgnoreCase("green"))
                {
                    Main.getINSTANCE().greenTeam.add(p.getUniqueId());
                    if(Main.getINSTANCE().blueTeam.contains(p.getUniqueId()))
                    {
                        Main.getINSTANCE().blueTeam.remove(p.getUniqueId());
                    }
                    else if(Main.getINSTANCE().redTeam.contains(p.getUniqueId()))
                    {
                        Main.getINSTANCE().redTeam.remove(p.getUniqueId());
                    }
                    
                    gp.region = Main.getINSTANCE().greenBase;
                    
                    gp.isGod = false;
                }
                else if(args[1].equalsIgnoreCase("god"))
                {
                    gp.isGod = true;
                    
                    if(Main.getINSTANCE().blueTeam.contains(p.getUniqueId()))
                    {
                        Main.getINSTANCE().blueTeam.remove(p.getUniqueId());
                    }
                    else if(Main.getINSTANCE().redTeam.contains(p.getUniqueId()))
                    {
                        Main.getINSTANCE().redTeam.remove(p.getUniqueId());
                    }
                    else if(Main.getINSTANCE().greenTeam.contains(p.getUniqueId()))
                    {
                        Main.getINSTANCE().greenTeam.remove(p.getUniqueId());
                    }
                }
            }
            catch(NullPointerException e)
            {
                sender.sendMessage("§cLe joueur que vous cherchez n'existe pas");
            }
        }
        return false;
    }

}
