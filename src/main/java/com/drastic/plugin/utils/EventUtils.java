package com.drastic.plugin.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.drastic.plugin.GameStatus;
import com.drastic.plugin.Main;
import com.drastic.plugin.player.GamePlayer;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.session.ClipboardHolder;

public class EventUtils
{
    @SuppressWarnings("deprecation")
    public void sendMeteor(World worldIn, double xIn, double zIn)
    {
        double x = xIn;

        double z = zIn;

        double y;

        for(int i = 255; 1 > 0; i--)
        {
            Location loc = new Location(worldIn, x, i, z);
            if(loc.getBlock().getType() != Material.AIR && loc.getBlock().getType() != Material.BARRIER && loc.getBlock().getType() != Material.WATER)
            {
                y = i;
                break;
            }
        }

        worldIn.createExplosion(new Location(worldIn, x, y, z), 15f);

        z -= 4;
        y += 1;
        x += 4;

        BukkitWorld world = new BukkitWorld(worldIn);

        File f = new File(Bukkit.getWorldContainer() + "/plugins/WorldEdit/schematics/meteor.schematic");

        try
        {
            Clipboard clipboard;
            ClipboardFormat format = ClipboardFormat.findByFile(f);
            ClipboardReader reader = format.getReader(new FileInputStream(f));
            clipboard = reader.read(world.getWorldData());

            EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(world, -1);
            Operation operation = new ClipboardHolder(clipboard, world.getWorldData()).createPaste(editSession, world.getWorldData()).to(new Vector(x, y, z)).ignoreAirBlocks(true).build();
            Operations.complete(operation);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(WorldEditException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Location loc = new Location(worldIn, x - 4, y - 4, z + 4);
        loc.getBlock().setType(Material.CHEST);

        Random rand = new Random();

        ItemStack stack = ItemUtil.getDiamond(rand.nextInt(6));
        ItemStack stack2 = ItemUtil.getGold(rand.nextInt(8));
        ItemStack stack3 = ItemUtil.getIron(rand.nextInt(11));

        Chest c = (Chest)loc.getBlock().getState();

        int chestIdDiamond = rand.nextInt(26);
        int chestIdGold = rand.nextInt(26);

        while(chestIdGold == chestIdDiamond)
        {
            chestIdGold = rand.nextInt(26);
        }

        int chestIdIron = rand.nextInt(26);

        while(chestIdIron == chestIdGold || chestIdIron == chestIdDiamond)
        {
            chestIdIron = rand.nextInt(26);
        }

        c.getBlockInventory().setItem(chestIdDiamond, stack);
        c.getBlockInventory().setItem(chestIdGold, stack2);
        c.getBlockInventory().setItem(chestIdIron, stack3);

        for(Player player : Bukkit.getOnlinePlayers())
        {
            player.sendTitle("§cÉvénement : §6Météorite", "§cX: §6" + xIn + " §cZ: §6" + zIn);
            player.sendMessage(Main.getINSTANCE().getPrefix() + "§9Une météorite s'est écrasée aux coordonnées " + "§cX: §6" + xIn + " §cZ: §6" + zIn);
        }
    }

    @SuppressWarnings("deprecation")
    public void sendBoat(World worldIn, double xIn, double zIn)
    {
        double x = xIn;

        double z = zIn;

        double y;

        for(int i = 255; 1 > 0; i--)
        {
            Location loc = new Location(worldIn, x, i, z);
            if(loc.getBlock().getType() != Material.AIR && loc.getBlock().getType() != Material.BARRIER && loc.getBlock().getType() != Material.WATER)
            {
                y = i;
                break;
            }
        }

        z += 9;
        y -= 3;
        x -= 14;

        BukkitWorld world = new BukkitWorld(worldIn);

        File f = new File(Bukkit.getWorldContainer() + "/plugins/WorldEdit/schematics/boat.schematic");

        try
        {
            Clipboard clipboard;
            ClipboardFormat format = ClipboardFormat.findByFile(f);
            ClipboardReader reader = format.getReader(new FileInputStream(f));
            clipboard = reader.read(world.getWorldData());

            EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(world, -1);
            Operation operation = new ClipboardHolder(clipboard, world.getWorldData()).createPaste(editSession, world.getWorldData()).to(new Vector(x, y, z)).ignoreAirBlocks(true).build();
            Operations.complete(operation);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(WorldEditException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Location loc = new Location(worldIn, x + 14, y + 1, z - 9);

        loc.getBlock().setType(Material.CHEST);

        Random rand = new Random();

        ItemStack stack = ItemUtil.getDiamond(rand.nextInt(14));
        ItemStack stack2 = ItemUtil.getGold(rand.nextInt(18));
        ItemStack stack3 = ItemUtil.getIron(rand.nextInt(23));

        Chest c = (Chest)loc.getBlock().getState();

        int chestIdDiamond = rand.nextInt(26);
        int chestIdGold = rand.nextInt(26);

        while(chestIdGold == chestIdDiamond)
        {
            chestIdGold = rand.nextInt(26);
        }

        int chestIdIron = rand.nextInt(26);

        while(chestIdIron == chestIdGold || chestIdIron == chestIdDiamond)
        {
            chestIdIron = rand.nextInt(26);
        }

        c.getBlockInventory().setItem(chestIdDiamond, stack);
        c.getBlockInventory().setItem(chestIdGold, stack2);
        c.getBlockInventory().setItem(chestIdIron, stack3);

        Zombie zombie = (Zombie)worldIn.spawnEntity(loc.add(1, 3, 0), EntityType.ZOMBIE);
        zombie.setMaxHealth(zombie.getMaxHealth() * 3);
        zombie.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 10000000, 2));
        zombie.setCustomName("§cGardien");
        zombie.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));

        Zombie zombie2 = (Zombie)worldIn.spawnEntity(loc.add(2, 3, 0), EntityType.ZOMBIE);
        zombie2.setMaxHealth(zombie2.getMaxHealth() * 3);
        zombie2.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 10000000, 2));
        zombie2.setCustomName("§cGardien");
        zombie2.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));

        Zombie zombie3 = (Zombie)worldIn.spawnEntity(loc.add(4, 3, 0), EntityType.ZOMBIE);
        zombie3.setMaxHealth(zombie3.getMaxHealth() * 3);
        zombie3.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 10000000, 2));
        zombie3.setCustomName("§cGardien");
        zombie3.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));

        Zombie zombie4 = (Zombie)worldIn.spawnEntity(loc.add(5, 3, 0), EntityType.ZOMBIE);
        zombie4.setMaxHealth(zombie4.getMaxHealth() * 3);
        zombie4.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 10000000, 2));
        zombie4.setCustomName("§cGardien");
        zombie4.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));

        for(Player player : Bukkit.getOnlinePlayers())
        {
            player.sendTitle("§cÉvénement : §6Bateau", "§cX: §6" + xIn + " §cZ: §6" + zIn);
            player.sendMessage(Main.getINSTANCE().getPrefix() + "§9Un bateau a accosté aux coordonnées " + "§cX: §6" + xIn + " §cZ: §6" + zIn);
        }
    }

    public static Player getRandomPlayer(@Nullable Player first)
    {
        if(Bukkit.getOnlinePlayers().size() > 1)
        {
            int range = Bukkit.getOnlinePlayers().size();

            Random rand = new Random();

            Player p = (Player)Bukkit.getOnlinePlayers().toArray()[rand.nextInt(range)];

            GamePlayer gp = GamePlayer.gamePlayers.get(p.getName());

            if(p.equals(first) || gp.isGod)
            {
                return getRandomPlayer(first);
            }
            else
            {
                if(first != null)
                {
                    if(Main.getINSTANCE().redTeam.contains(p.getUniqueId()) && Main.getINSTANCE().redTeam.contains(first.getUniqueId()) || Main.getINSTANCE().blueTeam.contains(p.getUniqueId()) && Main.getINSTANCE().blueTeam.contains(first.getUniqueId()) || Main.getINSTANCE().greenTeam.contains(p.getUniqueId()) && Main.getINSTANCE().greenTeam.contains(first.getUniqueId()))
                    {
                        return getRandomPlayer(first);
                    }
                }
                return p;
            }
        }
        else
            return null;
    }

    public static void randomPlayerSwitch()
    {
        if(GameStatus.isStatus(GameStatus.GAME) || GameStatus.isStatus(GameStatus.DEATHMATCH))
        {
            Player p1 = EventUtils.getRandomPlayer(null);
            Player p2 = EventUtils.getRandomPlayer(p1);

            String team1 = getTeam(p1);
            String team2 = getTeam(p2);;

            switchPos(p1, p2);

            setTeam(team1, p2);
            setTeam(team2, p1);

            p1.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous avez été changé d'équipe avec le joueur : §6" + p2.getName() + "§2.");
            p1.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous êtes désormais dans l'équipe " + team2);

            p2.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous avez été changé d'équipe avec le joueur : §6" + p1.getName() + "§2.");
            p2.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous êtes désormais dans l'équipe " + team1);
        }
    }

    private static void switchPos(Player p1, Player p2)
    {
        Location loc1 = new Location(p1.getWorld(), p1.getLocation().getX(), p1.getLocation().getY(), p1.getLocation().getZ());
        Location loc2 = new Location(p2.getWorld(), p2.getLocation().getX(), p2.getLocation().getY(), p2.getLocation().getZ());

        p1.teleport(loc2);
        p2.teleport(loc1);
    }

    private static void setTeam(String team, Player p)
    {
        if(team.equalsIgnoreCase("§cRouge"))
        {
            clearTeams(p);

            Main.getINSTANCE().redTeam.add(p.getUniqueId());

            GamePlayer gp = GamePlayer.gamePlayers.get(p.getName());

            gp.region = Main.getINSTANCE().redBase;

            gp.isGod = false;
        }
        else if(team.equalsIgnoreCase("§9Bleue"))
        {
            clearTeams(p);

            Main.getINSTANCE().blueTeam.add(p.getUniqueId());

            GamePlayer gp = GamePlayer.gamePlayers.get(p.getName());

            gp.region = Main.getINSTANCE().blueBase;

            gp.isGod = false;
        }
        else if(team.equalsIgnoreCase("§aVerte"))
        {
            clearTeams(p);

            Main.getINSTANCE().greenTeam.add(p.getUniqueId());

            GamePlayer gp = GamePlayer.gamePlayers.get(p.getName());

            gp.region = Main.getINSTANCE().greenBase;

            gp.isGod = false;
        }
    }

    private static String getTeam(Player p)
    {
        if(Main.getINSTANCE().redTeam.contains(p.getUniqueId()))
        {
            return "§cRouge";
        }
        else if(Main.getINSTANCE().blueTeam.contains(p.getUniqueId()))
        {
            return "§9Bleue";
        }
        else if(Main.getINSTANCE().greenTeam.contains(p.getUniqueId()))
        {
            return "§aVerte";
        }
        else
            return "";
    }

    private static void clearTeams(Player p)
    {
        GamePlayer gp = GamePlayer.gamePlayers.get(p.getName());

        if(Main.getINSTANCE().blueTeam.contains(p.getUniqueId()))
        {
            Main.getINSTANCE().blueTeam.remove(p.getUniqueId());
        }
        else if(Main.getINSTANCE().greenTeam.contains(p.getUniqueId()))
        {
            Main.getINSTANCE().greenTeam.remove(p.getUniqueId());
        }
        else if(Main.getINSTANCE().redTeam.contains(p.getUniqueId()))
        {
            Main.getINSTANCE().redTeam.remove(p.getUniqueId());
        }

        gp.region = null;
        gp.team = null;
        gp.isInBase = false;
    }
}
