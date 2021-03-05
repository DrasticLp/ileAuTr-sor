package com.drastic.plugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import com.drastic.plugin.Main;

public class FloatTextUtils
{
    public static void setFloatingText(Location location, String text, boolean sub)
    {
        Location customLocation = new Location(location.getWorld(), location.getX(), (!sub) ? location.getY() + 0.02d : location.getY() - 0.23d, location.getZ());
        ArmorStand armorStand = (ArmorStand)location.getWorld().spawnEntity(customLocation, EntityType.ARMOR_STAND);
        armorStand.setVisible(false);
        armorStand.setInvulnerable(true);
        armorStand.setCustomName(text);
        armorStand.setCustomNameVisible(true);
        armorStand.setGravity(false);
    }

    public static void deleteFloating(String name)
    {
        for(Entity e : Bukkit.getWorld(Main.getWorldName()).getEntities())
        {
            if(e instanceof ArmorStand)
            {
                String name2 = e.getName().substring(0, 7);
                if(name2.equalsIgnoreCase(name))
                {
                    e.remove();
                }
            }
        }
    }
}
