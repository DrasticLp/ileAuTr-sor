package com.drastic.plugin.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.drastic.plugin.Main;
import com.drastic.plugin.player.GamePlayer;
import com.drastic.plugin.utils.RegionManager;

public class CommandSetRegion implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args)
    {
        if(args.length == 1 && sender instanceof Player && sender.isOp())
        {
            Player player = (Player)sender;

            GamePlayer gp = GamePlayer.gamePlayers.get(player.getName());

            if(gp.loc1 != null && gp.loc2 != null && gp.loc3 != null)
            {

                if(args[0].equalsIgnoreCase("red"))
                {
                    Main.getINSTANCE().redBase = new RegionManager(gp.loc1, gp.loc2, gp.loc3);
                    sender.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous venez de créer la base de l'équipe " + "§crouge§2 " + "à partir des coordonnées X: " + gp.loc1.getX() + " Z: " + gp.loc1.getZ() + " jusqu'aux coordonnées X: " + gp.loc2.getX() + " Z: " + gp.loc2.getZ());
                }
                else if(args[0].equalsIgnoreCase("blue"))
                {
                    Main.getINSTANCE().blueBase = new RegionManager(gp.loc1, gp.loc2, gp.loc3);
                    sender.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous venez de créer la base de l'équipe " + "§9bleue§2 " + "à partir des coordonnées X: " + gp.loc1.getX() + " Z: " + gp.loc1.getZ() + " jusqu'aux coordonnées X: " + gp.loc2.getX() + " Z: " + gp.loc2.getZ());
                }
                else if(args[0].equalsIgnoreCase("green"))
                {
                    Main.getINSTANCE().greenBase = new RegionManager(gp.loc1, gp.loc2, gp.loc3);
                    sender.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous venez de créer la base de l'équipe " + "§averte§2 " + "à partir des coordonnées X: " + gp.loc1.getX() + " Z: " + gp.loc1.getZ() + " jusqu'aux coordonnées X: " + gp.loc2.getX() + " Z: " + gp.loc2.getZ());
                }

                setConfig(args[0], gp.loc1, gp.loc2, gp.loc3);
                Main.getINSTANCE().saveConfig();
                Main.getINSTANCE().reloadConfig();
                
                gp.loc1 = null;
                gp.loc2 = null;
                gp.loc3 = null;
                
                return true;
            }
            else
            {
                sender.sendMessage(Main.getINSTANCE().getPrefix() + "§cVeuillez d'abord choisir la zone");
                return false;
            }

        }
        else
        {
            return false;
        }
    }
    
    private void setConfig(String color, Location loc1, Location loc2, Location loc3)
    {
        Main.getINSTANCE().getConfig().set(color + "Base.firstCorner.X", loc1.getX());
        Main.getINSTANCE().getConfig().set(color + "Base.firstCorner.Z", loc1.getZ());
        
        Main.getINSTANCE().getConfig().set(color + "Base.secondCorner.X", loc2.getX());
        Main.getINSTANCE().getConfig().set(color + "Base.secondCorner.Z", loc2.getZ());
        
        Main.getINSTANCE().getConfig().set(color + "Base.spawn.X", loc3.getX());
        Main.getINSTANCE().getConfig().set(color + "Base.spawn.Y", loc3.getY());
        Main.getINSTANCE().getConfig().set(color + "Base.spawn.Z", loc3.getZ());
    }
}
