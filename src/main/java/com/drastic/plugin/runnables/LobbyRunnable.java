package com.drastic.plugin.runnables;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.drastic.plugin.GameStatus;
import com.drastic.plugin.Main;
import com.drastic.plugin.player.GamePlayer;
import com.drastic.plugin.scoreboard.ScoreboardManager;
import com.drastic.plugin.utils.JDAUtil;

public class LobbyRunnable extends BukkitRunnable
{
    public int timer = 121;
    public boolean start = false;

    public void run()
    {
        if(!GameStatus.isStatus(GameStatus.ATTENTE))
        {
            timer = 121;
            start = false;
            this.cancel();
            return;
        }

        if(!arePlayersRegistered())
        {
            return;
        }

        if(Bukkit.getOnlinePlayers().size() < 1)
        {
            Bukkit.broadcastMessage(Main.getINSTANCE().getPrefix() + "§cIl n'y a pas assez de joueurs dans la partie");
            timer = 121;
            start = false;
            this.cancel();
            return;
        }

        timer--;
        setLevel();

        if(timer == 0)
        {
            GameStatus.setSatus(GameStatus.GAME);
            Bukkit.broadcastMessage(Main.getINSTANCE().getPrefix() + "§6La partie commence");

            Main.getINSTANCE().randomTimer = getRandomTimer();
            Main.getINSTANCE().randomEp = getRandomEp();

            Bukkit.getWorld(Main.getWorldName()).setTime(0);

            JDAUtil.linkPlayers();

            for(Player p : Bukkit.getOnlinePlayers())
            {
                ScoreboardManager.scoreboardGame.get(p).updateLine(8, ChatColor.WHITE + "Phase: " + ChatColor.GREEN + GameStatus.getName());

                if(Main.getINSTANCE().blueTeam.contains(p.getUniqueId()))
                {
                    Location temploc = Main.getINSTANCE().blueBase.teleportLoc;

                    p.teleport(temploc);
                }
                else if(Main.getINSTANCE().greenTeam.contains(p.getUniqueId()))
                {
                    Location temploc = Main.getINSTANCE().greenBase.teleportLoc;

                    p.teleport(temploc);
                }
                else if(Main.getINSTANCE().redTeam.contains(p.getUniqueId()))
                {
                    Location temploc = Main.getINSTANCE().redBase.teleportLoc;

                    p.teleport(temploc);
                }

                GamePlayer gp = GamePlayer.gamePlayers.get(p.getName());

                if(!gp.isGod)
                {
                    openPowerInv(p);
                }

                ScoreboardManager.scoreboardGame.get(p).updateLine(2, "§6Épisode: §e1");
                ScoreboardManager.scoreboardGame.get(p).updateLine(8, ChatColor.WHITE + "");

                p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous êtes invincible pour 30s");
                p.sendMessage(Main.getINSTANCE().getPrefix() + "§9Début du PvP à l'épisode 2");

                p.getInventory().clear();
            }

        }

        if(shouldBroadcast())
        {
            Bukkit.broadcastMessage(Main.getINSTANCE().getPrefix() + "§6Démarrage de la partie dans §e " + getSeconds() + "§6.");
            for(Player p : Bukkit.getOnlinePlayers())
            {
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10f, 1f);
            }
        }
    }

    private int getRandomEp()
    {
        int rInt = new Random().nextInt(6);

        if(rInt == 0)
        {
            return getRandomEp();
        }
        else
            return rInt;
    }

    private int getRandomTimer()
    {
        int rInt = new Random().nextInt(20 * 60);

        if(rInt == 0)
        {
            return getRandomTimer();
        }
        else
            return rInt;
    }

    private void openPowerInv(Player p)
    {
        Inventory inv = Bukkit.createInventory(null, 9, "§7Choix de Classe");

        ItemStack glassPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7);
        ItemMeta metaPane = glassPane.getItemMeta();
        metaPane.setDisplayName("§cXXX");
        glassPane.setItemMeta(metaPane);

        inv.setItem(0, glassPane);

        ItemStack assassin = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta metaAssassin = assassin.getItemMeta();
        metaAssassin.setLore(Arrays.asList("§cÉpée en Fer", "§cShapness 4", "§cSmite 4", "§cBane of Arthropods 4", "§cJour : Speed + Weakness", "§cNuit : Strength"));
        metaAssassin.setDisplayName("§cAssassin");
        assassin.setItemMeta(metaAssassin);

        ItemStack guerrier = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemMeta metaGuerrier = guerrier.getItemMeta();
        metaGuerrier.setDisplayName("§aGuerrier");
        metaGuerrier.setLore(Arrays.asList("§aPlastron en Fer", "§aProtection 3", "§aSlowness", "§aResistance"));
        guerrier.setItemMeta(metaGuerrier);

        ItemStack archer = new ItemStack(Material.BOW, 1);
        ItemMeta metaArcher = archer.getItemMeta();
        metaArcher.setDisplayName("§9Archer");
        metaArcher.setLore(Arrays.asList("§9Arc", "§9Power 4", "§9Infinity", "§9Speed", "§9Depth Strider 3"));
        archer.setItemMeta(metaArcher);

        inv.setItem(1, assassin);
        inv.setItem(2, glassPane);
        inv.setItem(3, glassPane);
        inv.setItem(4, guerrier);
        inv.setItem(5, glassPane);
        inv.setItem(6, glassPane);
        inv.setItem(7, archer);
        inv.setItem(8, glassPane);

        p.openInventory(inv);
    }

    private String getSeconds()
    {
        return timer == 1 ? timer + " seconde" : timer + " secondes";
    }

    private void setLevel()
    {
        for(Player p : Bukkit.getOnlinePlayers())
        {
            p.setLevel(timer);
            if(ScoreboardManager.scoreboardGame.containsKey(p))
            {
                ScoreboardManager.scoreboardGame.get(p).updateLine(8, ChatColor.WHITE + "Début dans: " + ChatColor.GREEN + new SimpleDateFormat("mm:ss").format(new Date(Main.getINSTANCE().lobbyRunnable.timer * 1000)));
            }
        }
    }

    private boolean arePlayersRegistered()
    {
        List<String> names = new ArrayList<String>();

        for(Player p : Bukkit.getOnlinePlayers())
        {
            String configName = "token." + p.getUniqueId().toString();

            if(Main.getINSTANCE().getConfig().getString(configName) == null)
            {
                names.add(p.getName());
            }
        }

        if(names.isEmpty())
            return true;
        else
        {
            String s = "";

            for(String s2 : names)
            {
                s += s2.toUpperCase() + " ";
            }

            if(names.size() == 1)
            {
                Bukkit.broadcastMessage(Main.getINSTANCE().getPrefix() + "§cLe joueur " + s + "n'a pas enregistré son ID Discord");
            }
            else
            {
                Bukkit.broadcastMessage(Main.getINSTANCE().getPrefix() + "§cLes joueurs " + s + "n'ont pas enregistré leur ID Discord");
            }

            this.cancel();
            this.timer = 121;
            this.start = false;
            return false;
        }

    }

    private boolean shouldBroadcast()
    {
        return timer == 120 || timer == 90 || timer == 60 || timer == 30 || timer == 15 || timer == 10 || timer <= 5 && timer != 0;
    }
}
