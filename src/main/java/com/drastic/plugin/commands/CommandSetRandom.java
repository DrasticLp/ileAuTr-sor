package com.drastic.plugin.commands;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.drastic.plugin.Main;

public class CommandSetRandom implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args)
    {
        if(args.length == 2)
        {
            try
            {
                int ep = Integer.parseInt(args[0]);

                String[] waw = args[1].split(":");
                
                if(!(Integer.parseInt(waw[0]) <= 19 && Integer.parseInt(waw[1]) > 0 && Integer.parseInt(waw[1]) <= 59))
                {
                    return false;
                }
                
                int timer = Integer.parseInt(waw[0]) * 60 + Integer.parseInt(waw[1]);
                
                if(ep <= 5 && timer < 20 * 60 && ep > 0 && timer > 0)
                {                    
                    Main.getINSTANCE().randomTimer = timer;
                    Main.getINSTANCE().randomEp = ep;
                    
                    System.out.println(new SimpleDateFormat("mm:ss").format(new Date(timer * 1000)));
                    return true;
                }
                else
                {
                    sender.sendMessage(Main.getINSTANCE().getPrefix() + "§cVeuillez entrer des nombre valides");
                    return false;
                }
            }
            catch(NumberFormatException e)
            {
                sender.sendMessage(Main.getINSTANCE().getPrefix() + "§cVeuillez entrer des nombre valides");
                return false;
            }
        }
        else
            return false;
    }

}
