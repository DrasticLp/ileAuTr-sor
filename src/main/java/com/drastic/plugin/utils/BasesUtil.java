package com.drastic.plugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import com.drastic.plugin.Main;

public class BasesUtil
{
    public static void setRedBaseByDefault(double[] loc1, double[] loc2, double[] loc3)
    {
        if(areCoordinatesOk(loc1, loc2, loc3))
        {
            Location location1 = new Location(Bukkit.getWorld(Main.getWorldName()), loc1[0], 0, loc1[1]);
            Location location2 = new Location(Bukkit.getWorld(Main.getWorldName()), loc2[0], 255, loc2[1]);
            Location location3 = new Location(Bukkit.getWorld(Main.getWorldName()), loc3[0], loc3[1], loc3[2]);

            Main.getINSTANCE().redBase = new RegionManager(location1, location2, location3);
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Red Base " + ChatColor.YELLOW + "setup from X: " + loc1[0] + " Z: " + loc1[1] + " to X: " + loc2[0] + " Z: " + loc2[1] + " with spawn locs to X: " + loc3[0] + " Y: " + loc3[1] + " Z: " + loc3[2]);
        }
        else
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Red Base " + ChatColor.YELLOW + "set on : null");
        }
    }

    public static void setBlueBaseByDefault(double[] loc1, double[] loc2, double[] loc3)
    {
        if(areCoordinatesOk(loc1, loc2, loc3))
        {
            Location location1 = new Location(Bukkit.getWorld(Main.getWorldName()), loc1[0], 0, loc1[1]);
            Location location2 = new Location(Bukkit.getWorld(Main.getWorldName()), loc2[0], 255, loc2[1]);
            Location location3 = new Location(Bukkit.getWorld(Main.getWorldName()), loc3[0], loc3[1], loc3[2]);

            Main.getINSTANCE().blueBase = new RegionManager(location1, location2, location3);
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "Blue Base " + ChatColor.YELLOW + "setup from X: " + loc1[0] + " Z: " + loc1[1] + " to X: " + loc2[0] + " Z: " + loc2[1] + " with spawn locs to X: " + loc3[0] + " Y: " + loc3[1] + " Z: " + loc3[2]);
        }
        else
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "Blue Base " + ChatColor.YELLOW + "set on : null");
        }
    }

    public static void setGreenBaseByDefault(double[] loc1, double[] loc2, double[] loc3)
    {
        if(areCoordinatesOk(loc1, loc2, loc3))
        {
            Location location1 = new Location(Bukkit.getWorld(Main.getWorldName()), loc1[0], 0, loc1[1]);
            Location location2 = new Location(Bukkit.getWorld(Main.getWorldName()), loc2[0], 255, loc2[1]);
            Location location3 = new Location(Bukkit.getWorld(Main.getWorldName()), loc3[0], loc3[1], loc3[2]);

            Main.getINSTANCE().greenBase = new RegionManager(location1, location2, location3);
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Green Base " + ChatColor.YELLOW + "setup from X: " + loc1[0] + " Z: " + loc1[1] + " to X: " + loc2[0] + " Z: " + loc2[1] + " with spawn locs to X: " + loc3[0] + " Y: " + loc3[1] + " Z: " + loc3[2]);
        }
        else
        {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Green Base " + ChatColor.YELLOW + "set on : null");
        }
    }

    private static boolean areCoordinatesOk(double[] loc1, double[] loc2, double[] loc3)
    {
        return !(loc1[0] == loc2[0] || loc1[1] == loc2[1] || loc1[0] == loc3[0] || loc2[0] == loc3[0] || loc1[1] == loc3[2] || loc2[1] == loc3[2] || loc3[0] == loc3[1] || loc3[0] == loc3[2] || loc3[1] == loc3[2]);
    }
}
