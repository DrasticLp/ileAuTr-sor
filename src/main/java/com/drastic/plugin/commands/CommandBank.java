package com.drastic.plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.drastic.plugin.Main;
import com.drastic.plugin.player.GamePlayer;

public class CommandBank implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args)
    {
        if(args.length == 1 && sender instanceof Player)
        {
            Player p = (Player)sender;
            try
            {
                int count = Integer.parseInt(args[0]);

                GamePlayer gp = GamePlayer.gamePlayers.get(p.getName());

                if(Main.getINSTANCE().blueTeam.contains(p.getUniqueId()))
                {
                    if(Main.getINSTANCE().bluePoints >= count)
                    {
                        gp.addPoints(count);
                        Main.getINSTANCE().bluePoints -= count;
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous venez de retirer §6" + count + " §2points de la banque de votre équipe");
                        return true;
                    }
                    else 
                    {
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVotre équipe n'a pas assez de points");
                        return false;
                    }
                }
                else if(Main.getINSTANCE().redTeam.contains(p.getUniqueId()))
                {
                    if(Main.getINSTANCE().redPoints >= count)
                    {
                        gp.addPoints(count);
                        Main.getINSTANCE().redPoints -= count;
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous venez de retirer §6" + count + " §2points de la banque de votre équipe");
                        return true;
                    }
                    else 
                    {
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVotre équipe n'a pas assez de points");
                        return false;
                    }
                }
                else if(Main.getINSTANCE().greenTeam.contains(p.getUniqueId()))
                {
                    if(Main.getINSTANCE().greenPoints >= count)
                    {
                        gp.addPoints(count);
                        Main.getINSTANCE().greenPoints -= count;
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous venez de retirer §6" + count + " §2points de la banque de votre équipe");
                        return true;
                    }
                    else 
                    {
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVotre équipe n'a pas assez de points");
                        return false;
                    }
                }
                else
                    return false;
            }
            catch(NumberFormatException e)
            {
                return false;
            }
        }
        return false;
    }

}
