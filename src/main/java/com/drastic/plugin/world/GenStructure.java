package com.drastic.plugin.world;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import com.drastic.plugin.Main;

public class GenStructure
{
    public static void generateSpawn()
    {
        World w = Bukkit.getWorld(Main.getWorldName());

        int y = 255;

        if(new Location(w, 0, y, 0).getBlock().getType() != Material.BARRIER)
        {
            for(int x = -600; x < 601; x++)
            {
                for(int z = -600; z < 601; z++)
                {
                    Location loc = new Location(w, x, y, z);

                    loc.getBlock().setType(Material.BARRIER);

                }
            }
        }

        w.setSpawnLocation(new Location(w, 265, 256, -100));
    }

    public static void deleteSpawn()
    {
        World w = Bukkit.getWorld(Main.getWorldName());

        int y = 255;

        for(int x = -600; x < 601; x++)
        {
            for(int z = -600; z < 601; z++)
            {
                Location loc = new Location(w, x, y, z);

                loc.getBlock().setType(Material.AIR);

            }
        }

        w.setSpawnLocation(new Location(w, 265, 256, -100));
    }
}
