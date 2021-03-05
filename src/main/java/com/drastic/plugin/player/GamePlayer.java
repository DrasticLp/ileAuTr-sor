package com.drastic.plugin.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.drastic.plugin.scoreboard.ScoreboardManager;
import com.drastic.plugin.utils.RegionManager;

public class GamePlayer
{
    private String name;
    public List<UUID> team;

    public boolean isInBase;
    public RegionManager region;
    public boolean isGod = false;
    public static Map<String, GamePlayer> gamePlayers = new HashMap<String, GamePlayer>();
    private String power;
    public ScoreboardManager scoreboardManager;
    public Location loc1, loc2, loc3;
    
    private int points;
    
    public GamePlayer(String name)
    {
        this.name = name;
        this.team = new ArrayList<UUID>();
        this.isInBase = false;
        this.setPoints(0);
        this.scoreboardManager = new ScoreboardManager(Bukkit.getPlayer(name));

        gamePlayers.put(name, this);
    }

    public String getPower()
    {
        return this.power;
    }
    
    public void setPower(String p)
    {
        this.power = p;
    }
    
    public boolean isAssasin()
    {
        return this.power == "assassin";
    }
    
    public boolean isGuerrier()
    {
        return this.power == "guerrier";
    }
    
    public boolean isArcher()
    {
        return this.power == "archer";
    }
    
    public String getName()
    {
        return name;
    }

    public int getPoints()
    {
        return points;
    }

    public void setPoints(int points)
    {
        this.points = points;
    }
    
    public void shrinkPoints(int points)
    {
        this.points -= points;
    }
    
    public void addPoints(int points)
    {
        this.points += points;
    }
}
