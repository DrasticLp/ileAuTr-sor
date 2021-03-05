package com.drastic.plugin.listeners.player;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.drastic.plugin.Main;
import com.drastic.plugin.player.GamePlayer;

public class PlayerRespawnListener implements Listener
{
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e)
    {
        Player p = e.getPlayer();

        ItemStack assassin = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta esm = assassin.getItemMeta();
        esm.addEnchant(Enchantment.DAMAGE_ALL, 4, true);
        esm.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 4, true);
        esm.addEnchant(Enchantment.DAMAGE_UNDEAD, 4, true);
        esm.setDisplayName("§cÉpée de l'Assassin");
        assassin.setItemMeta(esm);

        ItemStack guerrrier = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemMeta meta = guerrrier.getItemMeta();
        meta.setDisplayName("§2Plastron du Guerrier");
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
        guerrrier.setItemMeta(meta);

        ItemStack archer = new ItemStack(Material.BOW, 1);
        ItemMeta im = archer.getItemMeta();
        im.addEnchant(Enchantment.ARROW_DAMAGE, 4, true);
        im.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        im.setDisplayName("§9Arc de l'Archer");
        archer.setItemMeta(im);

        if(GamePlayer.gamePlayers.containsKey(p.getName()))
        {
            GamePlayer gp = GamePlayer.gamePlayers.get(p.getName());

            if(gp.getPoints() > 0)
            {
                ItemStack s = new ItemStack(Material.ANVIL);
                ItemMeta m = s.getItemMeta();
                m.setDisplayName("§fPoints: §9" + gp.getPoints());
                s.setItemMeta(m);
                p.getWorld().dropItemNaturally(p.getLocation(), s);
            }
            gp.setPoints(0);

            if(!gp.isGod)
            {
                if(gp.region != null)
                {
                    Location tempLoc = gp.region.teleportLoc;
                    e.setRespawnLocation(tempLoc);
                }
            }

            if(gp.isArcher())
            {
                p.getInventory().addItem(archer);
            }
            else if(gp.isGuerrier())
            {
                p.getInventory().addItem(guerrrier);
            }
            else if(gp.isAssasin())
            {
                p.getInventory().addItem(assassin);
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e)
    {
        e.setDeathMessage(Main.getINSTANCE().getPrefix() + "§6" + e.getEntity().getDisplayName() + " §eest mort.");

        for(ItemStack s : e.getDrops())
        {
            if(s.hasItemMeta())
            {
                if(s.getItemMeta().hasDisplayName())
                {
                    String name = s.getItemMeta().getDisplayName();
                    if(name.equalsIgnoreCase("§2Plastron du Guerrier") || name.equalsIgnoreCase("§9Arc de l'Archer") || name.equalsIgnoreCase("§cÉpée de l'Assassin"))
                    {
                        e.getDrops().remove(s);
                    }
                }
            }
        }
    }
}
