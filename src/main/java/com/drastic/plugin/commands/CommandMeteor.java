package com.drastic.plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.drastic.plugin.Main;
import com.drastic.plugin.utils.EventUtils;

public class CommandMeteor implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args)
    {
        if(sender instanceof Player)
        {
            Player p = (Player)sender;

            if(args.length == 2)
            {
                try
                {
                    new EventUtils().sendMeteor(p.getWorld(), Double.parseDouble(args[0]), Double.parseDouble(args[1]));
                    return true;
                }
                catch(NumberFormatException e)
                {
                    e.printStackTrace();
                    return false;
                }

            }
            else
            {
                p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVeuillez utiliser la commande correctement");
                return false;
            }
        }
        else
        {
            return false;
        }
    }

}
