package com.drastic.plugin.utils;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.drastic.plugin.Main;
import com.drastic.plugin.player.GamePlayer;

public class GuiUtil
{
    public static void spawnPnj()
    {
        World w = Bukkit.getWorld(Main.getWorldName());

        for(Entity e : w.getEntities())
        {
            if(e instanceof Villager)
            {
                e.remove();
            }
        }

        double x = Main.getINSTANCE().getConfig().getDouble("egg.X");
        double y = Main.getINSTANCE().getConfig().getDouble("egg.Y");
        double z = Main.getINSTANCE().getConfig().getDouble("egg.Z");

        Villager pnj1 = (Villager)w.spawnEntity(new Location(w, x + 3, y, z), EntityType.VILLAGER);
        pnj1.setCustomName("§9Marchand");
        pnj1.setInvulnerable(true);
        pnj1.setAdult();

        Villager pnj2 = (Villager)w.spawnEntity(new Location(w, x - 3, y, z), EntityType.VILLAGER);
        pnj2.setCustomName("§9Marchand");
        pnj2.setInvulnerable(true);
        pnj2.setAdult();

        Villager pnj3 = (Villager)w.spawnEntity(new Location(w, x, y, z + 3), EntityType.VILLAGER);
        pnj3.setCustomName("§9Marchand");
        pnj3.setInvulnerable(true);
        pnj3.setAdult();

        Villager pnj4 = (Villager)w.spawnEntity(new Location(w, x, y, z - 3), EntityType.VILLAGER);
        pnj4.setCustomName("§9Marchand");
        pnj4.setInvulnerable(true);
        pnj4.setAdult();

    }

    public static void openBuyGui(Player p)
    {
        Inventory inv = Bukkit.createInventory(null, 27, "§7Boutique");

        ItemStack glassPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7);
        ItemMeta metaPane = glassPane.getItemMeta();
        metaPane.setDisplayName("§cXXX");
        glassPane.setItemMeta(metaPane);

        ItemStack pane2 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)9);
        ItemMeta metaPane2 = pane2.getItemMeta();
        GamePlayer gp = GamePlayer.gamePlayers.get(p.getName());
        metaPane2.setDisplayName("§9Points: §6" + gp.getPoints());
        pane2.setItemMeta(metaPane2);
        
        inv.setItem(0, glassPane);
        inv.setItem(1, glassPane);
        inv.setItem(2, glassPane);
        inv.setItem(3, glassPane);
        inv.setItem(4, glassPane);
        inv.setItem(5, glassPane);
        inv.setItem(6, glassPane);
        inv.setItem(7, glassPane);
        inv.setItem(8, pane2);
        inv.setItem(17, glassPane);
        inv.setItem(18, glassPane);
        inv.setItem(19, glassPane);
        inv.setItem(20, glassPane);
        inv.setItem(21, glassPane);
        inv.setItem(22, glassPane);
        inv.setItem(23, glassPane);
        inv.setItem(24, glassPane);
        inv.setItem(25, glassPane);
        inv.setItem(26, glassPane);

        ItemStack portal = new ItemStack(Material.ENDER_PORTAL_FRAME, 1);
        ItemMeta metaPortal = portal.getItemMeta();
        metaPortal.setDisplayName("§ePortail de l'END");
        metaPortal.setLore(Arrays.asList("§ePrix: §6700"));
        portal.setItemMeta(metaPortal);
        inv.setItem(9, portal);

        inv.setItem(10, glassPane);

        ItemStack fireResistance = new ItemStack(Material.FIREBALL, 1);
        ItemMeta metaFireResistance = fireResistance.getItemMeta();
        metaFireResistance.setDisplayName("§6Résistance au Feu");
        metaFireResistance.setLore(Arrays.asList("§ePrix: §6350"));
        fireResistance.setItemMeta(metaFireResistance);
        inv.setItem(11, fireResistance);

        inv.setItem(12, glassPane);

        ItemStack speed = new ItemStack(Material.FEATHER, 1);
        ItemMeta metaSpeed = speed.getItemMeta();
        metaSpeed.setDisplayName("§9Vitesse");
        metaSpeed.setLore(Arrays.asList("§ePrix: §6350"));
        speed.setItemMeta(metaSpeed);

        inv.setItem(13, speed);

        inv.setItem(14, glassPane);

        ItemStack resistance = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
        ItemMeta metaResistance = resistance.getItemMeta();
        metaResistance.setDisplayName("§aRésistance");
        metaResistance.setLore(Arrays.asList("§ePrix: §6450"));  
        resistance.setItemMeta(metaResistance);

        inv.setItem(15, resistance);

        inv.setItem(16, glassPane);

        ItemStack strength = new ItemStack(Material.GOLD_SWORD, 1);
        ItemMeta metaStrength = strength.getItemMeta();
        metaStrength.setDisplayName("§cStrength");
        metaStrength.setLore(Arrays.asList("§ePrix: §6450"));  
        strength.setItemMeta(metaStrength);

        inv.setItem(17, strength);

        p.openInventory(inv);
    }
}
