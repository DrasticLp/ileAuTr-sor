package com.drastic.plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.drastic.plugin.Main;
import com.drastic.plugin.player.GamePlayer;

public class CommandPoints implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args)
    {
        try
        {
            int i = Integer.parseInt(args[2]);
            if(args.length == 3)
            {
                if(args[1].equalsIgnoreCase("red"))
                {
                    if(args[0].equalsIgnoreCase("add"))
                    {
                        Main.getINSTANCE().redPoints += i;
                        return true;
                    }
                    else if(args[0].equalsIgnoreCase("remove"))
                    {
                        if(Main.getINSTANCE().redPoints >= i)
                        {
                            Main.getINSTANCE().redPoints -= i;
                            return true;
                        }
                        else
                        {
                            Main.getINSTANCE().redPoints = 0;
                            return true;
                        }
                    }
                    else
                        return false;
                }
                else if(args[1].equalsIgnoreCase("green"))
                {
                    if(args[0].equalsIgnoreCase("add"))
                    {
                        Main.getINSTANCE().greenPoints += i;
                        return true;
                    }
                    else if(args[0].equalsIgnoreCase("remove"))
                    {
                        if(Main.getINSTANCE().greenPoints >= i)
                        {
                            Main.getINSTANCE().greenPoints -= i;
                            return true;
                        }
                        else
                        {
                            Main.getINSTANCE().greenPoints = 0;
                            return true;
                        }
                    }
                    else
                        return false;
                }
                else if(args[1].equalsIgnoreCase("blue"))
                {
                    if(args[0].equalsIgnoreCase("add"))
                    {
                        Main.getINSTANCE().bluePoints += i;
                        return true;
                    }
                    else if(args[0].equalsIgnoreCase("remove"))
                    {
                        if(Main.getINSTANCE().bluePoints >= i)
                        {
                            Main.getINSTANCE().bluePoints -= i;
                            return true;
                        }
                        else
                        {
                            Main.getINSTANCE().bluePoints = 0;
                            return true;
                        }
                    }
                    else
                        return false;
                }
                else
                {
                    try
                    {
                        Player p = Bukkit.getPlayer(args[1]);

                        GamePlayer gp = GamePlayer.gamePlayers.get(p.getName());

                        if(args[0].equalsIgnoreCase("add"))
                        {
                            gp.addPoints(i);
                            return true;
                        }
                        else if(args[0].equalsIgnoreCase("remove"))
                        {
                            if(gp.getPoints() >= i)
                            {
                                gp.shrinkPoints(i);
                                return true;

                            }
                            else
                                return false;
                        }
                        else
                            return false;
                    }
                    catch(NullPointerException e)
                    {
                        sender.sendMessage(Main.getINSTANCE().getPrefix() + "§cImpossible de trouver le joueur spécifié");
                        return false;
                    }
                }
            }
            else
                return false;
        }
        catch(NumberFormatException e)
        {
            sender.sendMessage(Main.getINSTANCE().getPrefix() + "§cVeuillez entrer un nombre valide");
            return false;
        }
    }

}
