package com.drastic.plugin.listeners.player;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

import com.drastic.plugin.Main;
import com.drastic.plugin.player.GamePlayer;
import com.drastic.plugin.utils.ItemUtil;
import com.drastic.plugin.utils.PointsUtils;

public class PlayerGuiListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGH)
    public void onGuiClick(InventoryClickEvent e)
    {
        Player p = (Player)e.getWhoClicked();
        ItemStack current = e.getCurrentItem();
        Inventory inv = e.getInventory();
        GamePlayer gp = GamePlayer.gamePlayers.get(p.getName());

        if(current != null)
        {
            if(inv.getName().equalsIgnoreCase("§7Choix de classe"))
            {
                e.setCancelled(true);

                if(current.getType() == Material.IRON_SWORD)
                {
                    gp.setPower("assassin");

                    ItemStack stack = new ItemStack(Material.IRON_SWORD, 1);
                    ItemMeta esm = stack.getItemMeta();
                    esm.addEnchant(Enchantment.DAMAGE_ALL, 4, true);
                    esm.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 4, true);
                    esm.addEnchant(Enchantment.DAMAGE_UNDEAD, 4, true);
                    esm.setDisplayName("§cÉpée de l'Assassin");
                    esm.setUnbreakable(true);
                    stack.setItemMeta(esm);
                    p.getInventory().addItem(stack);

                    p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous avez choisi le type : §cAssassin");
                }
                else if(current.getType() == Material.IRON_CHESTPLATE)
                {
                    gp.setPower("guerrier");

                    ItemStack stack2 = new ItemStack(Material.IRON_CHESTPLATE, 1);
                    ItemMeta meta = stack2.getItemMeta();
                    meta.setDisplayName("§2Plastron du Guerrier");
                    meta.setUnbreakable(true);
                    meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);

                    stack2.setItemMeta(meta);

                    p.getInventory().addItem(stack2);

                    p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous avez choisi le type : §aGuerrier");
                }
                else if(current.getType() == Material.BOW)
                {
                    gp.setPower("archer");

                    ItemStack bow = new ItemStack(Material.BOW, 1);
                    ItemMeta im = bow.getItemMeta();
                    im.addEnchant(Enchantment.ARROW_DAMAGE, 4, true);
                    im.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
                    im.setDisplayName("§9Arc de l'Archer");
                    im.setUnbreakable(true);
                    bow.setItemMeta(im);

                    ItemStack s = new ItemStack(Material.ARROW, 1);
                    p.getInventory().addItem(s);

                    ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
                    EnchantmentStorageMeta esm = (EnchantmentStorageMeta)book.getItemMeta();
                    esm.addStoredEnchant(Enchantment.DEPTH_STRIDER, 3, true);
                    esm.setDisplayName("§9Livre de l'Archer");
                    book.setItemMeta(esm);

                    p.getInventory().addItem(bow);
                    p.getInventory().addItem(book);

                    p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous avez choisi le type : §9Archer");

                }
                p.closeInventory();
            }
            else if(inv.getName().equalsIgnoreCase("§7Boutique"))
            {
                e.setCancelled(true);

                if(current.getType() == Material.ENDER_PORTAL_FRAME)
                {
                    if(gp.getPoints() >= 700)
                    {
                        gp.shrinkPoints(700);
                        p.getInventory().addItem(ItemUtil.getPortal(1));
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous venez d'acheter un §ePortail de l'END");
                    }
                    else
                    {
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVous n'avez pas assez de points pour acheter cet item");
                    }
                }
                else
                {
                    buyPowers(gp, p, current);
                }
                
                ItemStack pane2 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)9);
                ItemMeta metaPane2 = pane2.getItemMeta();
                metaPane2.setDisplayName("§9Points: §6" + gp.getPoints());
                pane2.setItemMeta(metaPane2);

                inv.setItem(8, pane2);
            }
            else if(inv.getName().equalsIgnoreCase("§7Choix de Team"))
            {
                e.setCancelled(true);

                if(current.getType() == Material.WOOL)
                {

                    if(((Wool)current.getData()).getColor() == DyeColor.RED)
                    {
                        if(Main.getINSTANCE().redTeam.size() < 5)
                        {
                            clearTeams(p);

                            Main.getINSTANCE().redTeam.add(p.getUniqueId());

                            gp.region = Main.getINSTANCE().redBase;

                            gp.isGod = false;

                            p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous venez de choisir l'équipe §crouge");
                        }
                        else
                        {
                            p.sendMessage(Main.getINSTANCE().getPrefix() + "§cCette équipe est pleine");
                        }
                    }
                    else if(((Wool)current.getData()).getColor() == DyeColor.GREEN)
                    {
                        if(Main.getINSTANCE().greenTeam.size() < 5)
                        {
                            clearTeams(p);

                            Main.getINSTANCE().greenTeam.add(p.getUniqueId());

                            gp.region = Main.getINSTANCE().greenBase;

                            gp.isGod = false;

                            p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous venez de choisir l'équipe §averte");
                        }
                        else
                        {
                            p.sendMessage(Main.getINSTANCE().getPrefix() + "§cCette équipe est pleine");
                        }
                    }
                    else if(((Wool)current.getData()).getColor() == DyeColor.BLUE)
                    {
                        if(Main.getINSTANCE().blueTeam.size() < 5)
                        {
                            clearTeams(p);

                            Main.getINSTANCE().blueTeam.add(p.getUniqueId());

                            gp.region = Main.getINSTANCE().blueBase;

                            gp.isGod = false;

                            p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous venez de choisir l'équipe §9bleue");
                        }
                        else
                        {
                            p.sendMessage(Main.getINSTANCE().getPrefix() + "§cCette équipe est pleine");
                        }
                    }

                    if(!gp.isGod)
                    {
                        p.closeInventory();
                        Location temploc = gp.region.teleportLoc;

                        p.setBedSpawnLocation(temploc);
                    }
                }
            }
            else
            {
                if(current.getItemMeta() != null && current.getItemMeta().getDisplayName() != null)
                {
                    if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§6Lingot de Fer"))
                    {
                        gp.addPoints((PointsUtils.iron - 1) * current.getAmount());
                        ItemStack s = new ItemStack(Material.IRON_INGOT, current.getAmount());
                        e.setCurrentItem(s);
                        p.updateInventory();

                        return;
                    }
                    else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§6Lingot d'Or"))
                    {
                        gp.addPoints((PointsUtils.gold - 1) * current.getAmount());
                        ItemStack s = new ItemStack(Material.GOLD_INGOT, current.getAmount());
                        e.setCurrentItem(s);
                        p.updateInventory();

                        return;
                    }
                    else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§6Diamant"))
                    {
                        gp.addPoints((PointsUtils.diamond - 1) * current.getAmount());
                        ItemStack s = new ItemStack(Material.DIAMOND, current.getAmount());
                        e.setCurrentItem(s);
                        p.updateInventory();

                        return;
                    }
                    else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§6Charbon"))
                    {
                        gp.addPoints((PointsUtils.coal - 1) * current.getAmount());
                        ItemStack s = new ItemStack(Material.COAL, current.getAmount());
                        e.setCurrentItem(s);
                        p.updateInventory();

                        return;
                    }
                    else if(current.getItemMeta().getDisplayName().equalsIgnoreCase("§2Plastron du Guerrier") && inv.getType() == InventoryType.CHEST)
                    {
                        e.setCancelled(true);
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVous ne pouvez pas faire ça");
                        p.updateInventory();

                        return;
                    }

                }
            }
        }
    }

    private void buyPowers(GamePlayer gp, Player p, ItemStack s)
    {
        if(s.getType() == Material.FIREBALL)
        {
            if(gp.getPoints() >= 350)
            {
                if(Main.getINSTANCE().blueTeam.contains(p.getUniqueId()))
                {
                    if(Main.getINSTANCE().fireResistanceBlue)
                    {
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVous avez déjà cette amélioration");
                    }
                    else
                    {
                        gp.shrinkPoints(350);
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous venez d'acheter un l'amélioration §6Résistance au Feu §2pour l'équipe §9Bleue");
                        Main.getINSTANCE().fireResistanceBlue = true;
                    }
                }
                else if(Main.getINSTANCE().greenTeam.contains(p.getUniqueId()))
                {
                    if(Main.getINSTANCE().fireResistanceGreen)
                    {
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVous avez déjà cette amélioration");
                    }
                    else
                    {
                        gp.shrinkPoints(350);
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous venez d'acheter un l'amélioration §6Résistance au Feu §2pour l'équipe §aVerte");
                        Main.getINSTANCE().fireResistanceGreen = true;
                    }
                }
                else if(Main.getINSTANCE().redTeam.contains(p.getUniqueId()))
                {
                    if(Main.getINSTANCE().fireResistanceRed)
                    {
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVous avez déjà cette amélioration");
                    }
                    else
                    {
                        gp.shrinkPoints(350);
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous venez d'acheter un l'amélioration §6Résistance au Feu §2pour l'équipe §cRouge");
                        Main.getINSTANCE().fireResistanceRed = true;
                    }
                }
            }
            else
            {
                p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVous n'avez pas assez de points pour acheter cette amélioration");
            }
        }
        else if(s.getType() == Material.FEATHER)
        {
            if(gp.getPoints() >= 350)
            {
                if(Main.getINSTANCE().blueTeam.contains(p.getUniqueId()))
                {
                    if(Main.getINSTANCE().speedBlue)
                    {
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVous avez déjà cette amélioration");
                    }
                    else
                    {
                        gp.shrinkPoints(350);
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous venez d'acheter un l'amélioration §9Vitesse §2pour l'équipe §9Bleue");
                        Main.getINSTANCE().speedBlue = true;
                    }
                }
                else if(Main.getINSTANCE().greenTeam.contains(p.getUniqueId()))
                {
                    if(Main.getINSTANCE().speedGreen)
                    {
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVous avez déjà cette amélioration");
                    }
                    else
                    {
                        gp.shrinkPoints(350);
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous venez d'acheter un l'amélioration §9Vitesse §2pour l'équipe §aVerte");
                        Main.getINSTANCE().speedGreen = true;
                    }
                }
                else if(Main.getINSTANCE().redTeam.contains(p.getUniqueId()))
                {
                    if(Main.getINSTANCE().speedRed)
                    {
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVous avez déjà cette amélioration");
                    }
                    else
                    {
                        gp.shrinkPoints(350);
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous venez d'acheter un l'amélioration §9Vitesse §2pour l'équipe §cRouge");
                        Main.getINSTANCE().speedRed = true;
                    }
                }
            }
            else
            {
                p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVous n'avez pas assez de points pour acheter cette amélioration");
            }
        }
        else if(s.getType() == Material.CHAINMAIL_CHESTPLATE)
        {
            if(gp.getPoints() >= 450)
            {
                if(Main.getINSTANCE().blueTeam.contains(p.getUniqueId()))
                {
                    if(Main.getINSTANCE().resistanceBlue)
                    {
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVous avez déjà cette amélioration");
                    }
                    else
                    {
                        gp.shrinkPoints(450);
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous venez d'acheter un l'amélioration §aRésistance §2pour l'équipe §9Bleue");
                        Main.getINSTANCE().resistanceBlue = true;
                    }
                }
                else if(Main.getINSTANCE().greenTeam.contains(p.getUniqueId()))
                {
                    if(Main.getINSTANCE().resistanceGreen)
                    {
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVous avez déjà cette amélioration");
                    }
                    else
                    {
                        gp.shrinkPoints(450);
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous venez d'acheter un l'amélioration §aRésistance §2pour l'équipe §aVerte");
                        Main.getINSTANCE().resistanceGreen = true;
                    }
                }
                else if(Main.getINSTANCE().redTeam.contains(p.getUniqueId()))
                {
                    if(Main.getINSTANCE().resistanceRed)
                    {
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVous avez déjà cette amélioration");
                    }
                    else
                    {
                        gp.shrinkPoints(450);
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous venez d'acheter un l'amélioration §aRésistance §2pour l'équipe §cRouge");
                        Main.getINSTANCE().resistanceRed = true;
                    }
                }
            }
            else
            {
                p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVous n'avez pas assez de points pour acheter cette amélioration");
            }
        }
        else if(s.getType() == Material.GOLD_SWORD)
        {
            if(gp.getPoints() >= 450)
            {
                if(Main.getINSTANCE().blueTeam.contains(p.getUniqueId()))
                {
                    if(Main.getINSTANCE().strengthBlue)
                    {
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVous avez déjà cette amélioration");
                    }
                    else
                    {
                        gp.shrinkPoints(450);
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous venez d'acheter un l'amélioration §cForce §2pour l'équipe §9Bleue");
                        Main.getINSTANCE().strengthBlue = true;
                    }
                }
                else if(Main.getINSTANCE().greenTeam.contains(p.getUniqueId()))
                {
                    if(Main.getINSTANCE().strengthGreen)
                    {
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVous avez déjà cette amélioration");
                    }
                    else
                    {
                        gp.shrinkPoints(450);
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous venez d'acheter un l'amélioration §cForce §2pour l'équipe §aVerte");
                        Main.getINSTANCE().strengthGreen = true;
                    }
                }
                else if(Main.getINSTANCE().redTeam.contains(p.getUniqueId()))
                {
                    if(Main.getINSTANCE().strengthRed)
                    {
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVous avez déjà cette amélioration");
                    }
                    else
                    {
                        gp.shrinkPoints(450);
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous venez d'acheter un l'amélioration §cForce §2pour l'équipe §cRouge");
                        Main.getINSTANCE().strengthRed = true;
                    }
                }
            }
            else
            {
                p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVous n'avez pas assez de points pour acheter cette amélioration");
            }
        }
    }

    private void clearTeams(Player p)
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
