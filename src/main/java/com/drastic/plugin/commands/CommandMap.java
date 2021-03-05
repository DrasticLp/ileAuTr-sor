package com.drastic.plugin.commands;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.drastic.plugin.Main;
import com.drastic.plugin.utils.ItemUtil;

public class CommandMap implements CommandExecutor
{
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args)
    {
        if(args.length == 3)
        {
            int x = Integer.parseInt(args[0]);
            int y = Integer.parseInt(args[1]);
            int z = Integer.parseInt(args[2]);

            Location loc = new Location(Bukkit.getWorld(Main.getWorldName()), (double)x, (double)y, (double)z);

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

            for(Player p : Bukkit.getOnlinePlayers())
            {
                p.sendTitle("§cÉvénement : §6Trésor", "");
                p.sendMessage(Main.getINSTANCE().getPrefix() + "§9Vous venez de trouver une carte au trésor !");

                ItemStack map = new ItemStack(Material.PAPER, 1);
                ItemMeta meta = map.getItemMeta();
                meta.setDisplayName("§6Carte au trésor");
                meta.setLore(Arrays.asList("§9X: §6" + x, "§9Z: §6" + z));
                map.setItemMeta(meta);

                p.getInventory().addItem(map);
            }
            return true;
        }
        return false;
    }

}
