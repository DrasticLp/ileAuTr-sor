package com.drastic.plugin.scoreboard;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ScoreboardManager
{

    public Player player;
    public FastBoard scoreboardSign;
    public static Map<Player, FastBoard> scoreboardGame = new HashMap<Player, FastBoard>();

    public ScoreboardManager(Player player)
    {
        this.player = player;
        this.scoreboardSign = new FastBoard(player);
        scoreboardGame.put(player, this.scoreboardSign);
    }

    public void loadScoreboard()
    {
        this.scoreboardSign.updateTitle(ChatColor.YELLOW + " " + ChatColor.GOLD + ChatColor.BOLD + "Ile au Trésor");

        ((FastBoard)scoreboardGame.get(this.player)).updateLine(11, ChatColor.GOLD + "Saison 1");
        ((FastBoard)scoreboardGame.get(this.player)).updateLine(10, "§8-----------------------");
        ((FastBoard)scoreboardGame.get(this.player)).updateLine(9, "§fPoints: §c//");
        ((FastBoard)scoreboardGame.get(this.player)).updateLine(8, ChatColor.WHITE + "Début dans : Attente du Dieu");
        ((FastBoard)scoreboardGame.get(this.player)).updateLine(6, ChatColor.WHITE + "Joueurs: " + ChatColor.YELLOW + Bukkit.getOnlinePlayers().size() + "§8/§e" + Bukkit.getMaxPlayers());
        ((FastBoard)scoreboardGame.get(this.player)).updateLine(7, "§3");
        ((FastBoard)scoreboardGame.get(this.player)).updateLine(4, "§1");
        ((FastBoard)scoreboardGame.get(this.player)).updateLine(1, "§8-----------------------");
        ((FastBoard)scoreboardGame.get(this.player)).updateLine(0, ChatColor.GOLD + "");
    }
}