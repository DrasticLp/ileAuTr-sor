package com.drastic.plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.drastic.plugin.GameStatus;
import com.drastic.plugin.Main;

public class CommandTeam implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args)
    {
        if(sender instanceof Player)
        {
            Player p = (Player)sender;

            if(GameStatus.isStatus(GameStatus.GAME) || GameStatus.isStatus(GameStatus.DEATHMATCH))
            {
                p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVous ne pouvez plus changer d'équipe");
                
                return false;

            }
            else
            {
                openTeamInv(p);
                return true;
            }
        }
        return false;
    }

    private void openTeamInv(Player p)
    {
        Inventory inv = Bukkit.createInventory(null, 9, "§7Choix de Team");

        ItemStack glassPane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)7);
        ItemMeta metaPane = glassPane.getItemMeta();
        metaPane.setDisplayName("§cXXX");
        glassPane.setItemMeta(metaPane);

        inv.setItem(0, glassPane);

        ItemStack red = new ItemStack(Material.WOOL, 1, (short)14);
        ItemMeta redM = red.getItemMeta();
        redM.setDisplayName("§cRouge");
        red.setItemMeta(redM);

        ItemStack green = new ItemStack(Material.WOOL, 1, (short)13);
        ItemMeta greenM = green.getItemMeta();
        greenM.setDisplayName("§aVert");
        green.setItemMeta(greenM);

        ItemStack blue = new ItemStack(Material.WOOL, 1, (short)11);
        ItemMeta blueM = blue.getItemMeta();
        blueM.setDisplayName("§9Bleu");
        blue.setItemMeta(blueM);

        inv.setItem(1, red);
        inv.setItem(2, glassPane);
        inv.setItem(3, glassPane);
        inv.setItem(4, green);
        inv.setItem(5, glassPane);
        inv.setItem(6, glassPane);
        inv.setItem(7, blue);
        inv.setItem(8, glassPane);

        p.openInventory(inv);
    }
}
