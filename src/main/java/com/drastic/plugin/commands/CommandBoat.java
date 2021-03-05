package com.drastic.plugin.commands;

import java.util.Random;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.drastic.plugin.Main;
import com.drastic.plugin.utils.EventUtils;

public class CommandBoat implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args)
    {
        if(sender instanceof Player)
        {
            Player p = (Player)sender;

            int i = new Random().nextInt(3);

            if(i == 0)
            {
                double x = Main.getINSTANCE().getConfig().getDouble("boat.firstLoc.X");
                double z = Main.getINSTANCE().getConfig().getDouble("boat.firstLoc.Z");

                new EventUtils().sendBoat(p.getWorld(), x, z);
            }
            else if(i == 1)
            {
                double x = Main.getINSTANCE().getConfig().getDouble("boat.secondLoc.X");
                double z = Main.getINSTANCE().getConfig().getDouble("boat.secondLoc.Z");
                
                new EventUtils().sendBoat(p.getWorld(), x, z);
            }
            else
            {
                double x = Main.getINSTANCE().getConfig().getDouble("boat.thirdLoc.X");
                double z = Main.getINSTANCE().getConfig().getDouble("boat.thirdLoc.Z");
                
                new EventUtils().sendBoat(p.getWorld(), x, z);
            }

            return true;

        }
        else
        {
            return false;
        }
    }

}
