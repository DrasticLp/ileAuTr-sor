package com.drastic.plugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.drastic.plugin.Main;

public class RegionManager
{
    public Location minLoc, maxLoc, teleportLoc;

    public RegionManager(Location firstPoint, Location secondPoint, Location thirdPoint)
    {
        this.minLoc = new Location(firstPoint.getWorld(), min(firstPoint.getX(), secondPoint.getX()), min(firstPoint.getY(), secondPoint.getY()), min(firstPoint.getZ(), secondPoint.getZ()));
        this.maxLoc = new Location(firstPoint.getWorld(), max(firstPoint.getX(), secondPoint.getX()), max(firstPoint.getY(), secondPoint.getY()), max(firstPoint.getZ(), secondPoint.getZ()));
        this.teleportLoc = thirdPoint;
        this.teleportLoc.add(0.5, 1, 0.5);
    }

    public double min(double a, double b)
    {
        return a < b ? a : b;
    }

    public double max(double a, double b)
    {
        return a > b ? a : b;
    }

    public boolean isInArea(Location loc)
    {
        return this.minLoc.getX() <= loc.getX() && this.minLoc.getZ() <= loc.getZ() && this.maxLoc.getX() >= loc.getX() && this.maxLoc.getZ() >= loc.getZ();
    }

    public Location getMiddle()
    {
        double a, b;
        a = (this.maxLoc.getX() - this.minLoc.getX()) / 2D + this.minLoc.getX();
        b = (this.maxLoc.getZ() - this.minLoc.getZ()) / 2D + this.minLoc.getZ();

        return new Location(Bukkit.getWorld(Main.getWorldName()), a, this.minLoc.getY(), b);
    }
}
