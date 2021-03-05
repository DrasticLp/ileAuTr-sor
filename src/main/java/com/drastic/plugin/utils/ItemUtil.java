package com.drastic.plugin.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil
{
    public static ItemStack getIron(int count)
    {
        ItemStack stack = new ItemStack(Material.IRON_INGOT, count);
        ItemMeta m = stack.getItemMeta();
        m.setDisplayName("§6Lingot de Fer");
        stack.setItemMeta(m);
        return stack;
    }
    
    public static ItemStack getDiamond(int count)
    {
        ItemStack stack = new ItemStack(Material.DIAMOND, count);
        ItemMeta m = stack.getItemMeta();
        m.setDisplayName("§6Diamant");
        stack.setItemMeta(m);
        return stack;
    }
    
    public static ItemStack getGold(int count)
    {
        ItemStack stack = new ItemStack(Material.GOLD_INGOT, count);
        ItemMeta m = stack.getItemMeta();
        m.setDisplayName("§6Lingot d'Or");
        stack.setItemMeta(m);
        return stack;
    }
    
    public static ItemStack getCoal(int count)
    {
        ItemStack stack = new ItemStack(Material.COAL, count);
        ItemMeta m = stack.getItemMeta();
        m.setDisplayName("§6Charbon");
        stack.setItemMeta(m);
        return stack;
    }
    
    public static ItemStack getPortal(int count)
    {
        ItemStack stack = new ItemStack(Material.ENDER_PORTAL_FRAME, count);
        ItemMeta m = stack.getItemMeta();
        m.setDisplayName("§ePortail de l'END");
        stack.setItemMeta(m);
        return stack;
    }
}
