package com.drastic.plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.drastic.plugin.Main;
import com.drastic.plugin.utils.JDAUtil;

import net.dv8tion.jda.api.entities.User;

public class CommandLink implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args)
    {
        if(args.length == 1 && sender instanceof Player)
        {
            Player p = (Player)sender;

            if(args[0].contains("#"))
            {
                String[] args2 = args[0].split("#");

                if(args2[1].length() == 4)
                {
                    try
                    {
                        @SuppressWarnings("unused")
                        int d = Integer.parseInt(args2[1]);
                    }
                    catch(NumberFormatException nfe)
                    {
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVeuillez entrer un tag discord valide");
                        return false;
                    }

                    try
                    {
                        User u = JDAUtil.getJDA().getUserByTag(args2[0], args2[1]);

                        if(u == null)
                        {
                            p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVeuillez entrer un tag discord valide");
                            return false;
                        }

                        JDAUtil.linkAccount(p, args[0]);

                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§6Votre compte a bien été lié");
                        return true;
                    }
                    catch(NullPointerException e)
                    {
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVeuillez entrer un tag discord valide");
                        return false;
                    }
                }
                else
                {
                    p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVeuillez entrer un tag discord valide");
                    return false;
                }
            }
            else
            {
                p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVeuillez entrer un tag discord valide");
                return false;
            }
        }
        return false;
    }

}
