package com.drastic.plugin.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.drastic.plugin.GameStatus;
import com.drastic.plugin.Main;
import com.drastic.plugin.player.GamePlayer;
import com.drastic.plugin.scoreboard.ScoreboardManager;
import com.drastic.plugin.utils.PointsUtils;

public class PlayerBlockListener implements Listener
{
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e)
    {
        Player p = e.getPlayer();

        if(GameStatus.isStatus(GameStatus.GAME) || GameStatus.isStatus(GameStatus.DEATHMATCH))
        {
            GamePlayer gp = GamePlayer.gamePlayers.get(p.getName());
            if(!gp.isGod)
            {
                if(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock() != null)
                {
                    if(!canBreak(p, e.getClickedBlock().getLocation()))
                    {
                        if(e.getClickedBlock().getType() == Material.ACACIA_DOOR || e.getClickedBlock().getType() == Material.BIRCH_DOOR || e.getClickedBlock().getType() == Material.DARK_OAK_DOOR || e.getClickedBlock().getType() == Material.IRON_DOOR || e.getClickedBlock().getType() == Material.JUNGLE_DOOR || e.getClickedBlock().getType() == Material.SPRUCE_DOOR || e.getClickedBlock().getType() == Material.WOOD_DOOR || e.getClickedBlock().getType() == Material.WOODEN_DOOR)
                        {
                            e.setCancelled(true);
                            p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVous ne pouvez pas intéragir avec ce type de bloc ici");

                            return;
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e)
    {
        Player p = e.getPlayer();

        GamePlayer gp = GamePlayer.gamePlayers.get(p.getName());

        if(GameStatus.isStatus(GameStatus.GAME) || GameStatus.isStatus(GameStatus.DEATHMATCH))
        {
            if(e.getBlock().getType() != Material.FURNACE && e.getBlock().getType() != Material.WORKBENCH && e.getBlock().getType() != Material.TORCH && e.getBlock().getType() != Material.DRAGON_EGG)
            {
                if(!gp.isGod)
                {
                    if(!canPlace(p, e.getBlock().getLocation()))
                    {
                        if(!isPortal(p, e.getBlockAgainst().getLocation(), e.getBlockPlaced()))
                        {
                            if(p.getItemInHand().getType() != Material.EYE_OF_ENDER)
                            {
                                e.setCancelled(true);
                                p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVous ne pouvez pas poser de blocs en dehors de votre base");
                                return;
                            }
                        }
                        else
                        {
                            p.getInventory().addItem(new ItemStack(Material.EYE_OF_ENDER));
                        }
                    }
                }
            }
            else
            {
                if(!gp.isGod && e.getBlock().getType() == Material.DRAGON_EGG)
                {
                    Location l = new Location(Bukkit.getWorld(Main.getWorldName()), Main.getINSTANCE().getConfig().getDouble("egg.X"), Main.getINSTANCE().getConfig().getDouble("egg.Y"), Main.getINSTANCE().getConfig().getDouble("egg.Z"));

                    if(l.getX() == e.getBlockPlaced().getX() && l.getY() == e.getBlockPlaced().getY() && l.getZ() == e.getBlockPlaced().getZ())
                    {
                        String s = "";

                        if(Main.getINSTANCE().blueTeam.contains(p.getUniqueId()))
                        {
                            s = "§9Bleue";
                        }
                        else if(Main.getINSTANCE().redTeam.contains(p.getUniqueId()))
                        {
                            s = "§cRouge";
                        }
                        else if(Main.getINSTANCE().greenTeam.contains(p.getUniqueId()))
                        {
                            s = "§aVerte";
                        }

                        Location l2 = new Location(Bukkit.getWorld(Main.getWorldName()), Main.getINSTANCE().getConfig().getDouble("egg.X"), Main.getINSTANCE().getConfig().getDouble("egg.Y") - 1, Main.getINSTANCE().getConfig().getDouble("egg.Z"));

                        stopGame(p, s, l2);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e)
    {
        Player p = e.getPlayer();

        GamePlayer gp = GamePlayer.gamePlayers.get(p.getName());

        if(GameStatus.isStatus(GameStatus.GAME) || GameStatus.isStatus(GameStatus.DEATHMATCH))
        {
            if(!gp.isGod)
            {
                if(!canBreak(p, e.getBlock().getLocation()))
                {
                    e.setCancelled(true);
                    p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVous ne pouvez pas casser de blocs dans les bases ennemies");

                    return;
                }
                else
                {
                    Block b = e.getBlock();

                    if(p.getGameMode() != GameMode.CREATIVE)
                    {
                        if(b.getType() == Material.DIAMOND_ORE)
                        {
                            gp.addPoints(PointsUtils.diamond);
                        }
                        else if(b.getType() == Material.GOLD_ORE)
                        {
                            e.setCancelled(true);
                            gp.addPoints(PointsUtils.gold);
                            b.setType(Material.AIR);

                            ExperienceOrb orb = p.getWorld().spawn(b.getLocation(), ExperienceOrb.class);
                            orb.setExperience(10);

                            p.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.GOLD_INGOT));
                        }
                        else if(b.getType() == Material.IRON_ORE)
                        {
                            e.setCancelled(true);
                            gp.addPoints(PointsUtils.iron);
                            b.setType(Material.AIR);

                            ExperienceOrb orb = p.getWorld().spawn(b.getLocation(), ExperienceOrb.class);
                            orb.setExperience(7);

                            b.setType(Material.AIR);

                            p.getWorld().dropItemNaturally(b.getLocation(), new ItemStack(Material.IRON_INGOT));
                        }
                        else if(b.getType() == Material.COAL_ORE)
                        {
                            gp.addPoints(PointsUtils.coal);
                        }
                        else if(b.getType() == Material.REDSTONE_ORE)
                        {
                            gp.addPoints(PointsUtils.redstone);
                        }
                        else if(b.getType() == Material.LAPIS_ORE)
                        {
                            gp.addPoints(PointsUtils.lapis);
                        }
                        else if(b.getType() == Material.EMERALD_ORE)
                        {
                            gp.addPoints(PointsUtils.emerald);
                        }
                        else if(b.getType() == Material.MOB_SPAWNER)
                        {
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
        else
        {
            if(gp.isGod)
            {
                if(p.getInventory().getItemInMainHand().getType() == Material.IRON_AXE)
                {
                    e.setCancelled(true);
                    if(gp.loc1 == null)
                    {
                        Location tempLoc = e.getBlock().getLocation();
                        tempLoc.setY(0);
                        gp.loc1 = tempLoc;
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous venez de choisir le 1er point aux coordonnées X: " + tempLoc.getBlockX() + " Z: " + tempLoc.getZ());
                    }
                    else if(gp.loc1 != null && gp.loc2 == null)
                    {
                        Location tempLoc = e.getBlock().getLocation();
                        tempLoc.setY(255);
                        gp.loc2 = tempLoc;
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous venez de choisir le 2eme point aux coordonnées X: " + tempLoc.getBlockX() + " Z: " + tempLoc.getZ());
                    }
                    else if(gp.loc1 != null && gp.loc2 != null && gp.loc3 == null)
                    {
                        Location tempLoc = e.getBlock().getLocation();
                        gp.loc3 = tempLoc;
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous venez de choisir le 3eme point aux coordonnées X: " + tempLoc.getBlockX() + " Z: " + tempLoc.getZ() + " Y: " + tempLoc.getBlockY());
                    }
                    else if(gp.loc1 != null && gp.loc2 != null && gp.loc3 != null)
                    {
                        Location tempLoc = e.getBlock().getLocation();
                        tempLoc.setY(0);
                        gp.loc1 = tempLoc;
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§2Vous venez de choisir le 1er point aux coordonnées X: " + tempLoc.getBlockX() + " Z: " + tempLoc.getZ());

                        gp.loc2 = null;
                        gp.loc3 = null;
                    }
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void stopGame(Player p, String s, Location b)
    {
        GameStatus.setSatus(GameStatus.STOP);
        for(Player player : Bukkit.getOnlinePlayers())
        {
            ScoreboardManager.scoreboardGame.get(player).updateLine(2, "§6Partie terminée");
            ScoreboardManager.scoreboardGame.get(p).updateLine(9, ChatColor.WHITE + "Phase: " + ChatColor.GREEN + GameStatus.getName());
            ScoreboardManager.scoreboardGame.get(p).updateLine(8, "");
            ScoreboardManager.scoreboardGame.get(player).updateLine(3, "§6Gagnant: §9Équipe " + s);
            player.setGameMode(GameMode.SPECTATOR);
            p.sendTitle("§6Victoire de l'équipe " + s, "");
            
            p.teleport(b.clone().add(0, 2, 0));
        }
        
        System.out.println(b.getBlock().getType());
        
        if(s.equalsIgnoreCase("§cRouge"))
        {
            b.getBlock().setData((byte)14);
        }
        else if(s.equalsIgnoreCase("§aVerte"))
        {
            b.getBlock().setData((byte)13);
        }
        else if(s.equalsIgnoreCase("§9Bleue"))
        {
            b.getBlock().setData((byte)11);
        }

        Bukkit.broadcastMessage(Main.getINSTANCE().getPrefix() + "§6Victoire de l'équipe " + s);
    }

    @SuppressWarnings("deprecation")
    private boolean isPortal(Player p, Location loc, Block b)
    {
        if(Main.getINSTANCE().redTeam.contains(p.getUniqueId()))
        {
            if(loc.getBlock().getType() == Material.CONCRETE)
            {
                if(loc.getBlock().getData() == (byte)14)
                {
                    if(b.getType() == Material.ENDER_PORTAL_FRAME)
                    {
                        return true;
                    }
                }
            }
        }
        else if(Main.getINSTANCE().greenTeam.contains(p.getUniqueId()))
        {
            if(loc.getBlock().getType() == Material.CONCRETE)
            {
                if(loc.getBlock().getData() == (byte)13)
                {
                    if(b.getType() == Material.ENDER_PORTAL_FRAME)
                    {
                        return true;
                    }
                }
            }
        }
        else if(Main.getINSTANCE().blueTeam.contains(p.getUniqueId()))
        {
            if(loc.getBlock().getType() == Material.CONCRETE)
            {
                if(loc.getBlock().getData() == (byte)11)
                {
                    if(b.getType() == Material.ENDER_PORTAL_FRAME)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean canBreak(Player p, Location loc)
    {
        if(Main.getINSTANCE().redTeam.contains(p.getUniqueId()))
        {
            if(isInBlueBase(loc) || isInGreenBase(loc))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else if(Main.getINSTANCE().greenTeam.contains(p.getUniqueId()))
        {
            if(isInBlueBase(loc) || isInRedBase(loc))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else if(Main.getINSTANCE().blueTeam.contains(p.getUniqueId()))
        {
            if(isInGreenBase(loc) || isInRedBase(loc))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return true;
        }
    }

    private static boolean canPlace(Player p, Location loc)
    {
        if(Main.getINSTANCE().redTeam.contains(p.getUniqueId()))
        {
            if(!isInRedBase(loc))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else if(Main.getINSTANCE().greenTeam.contains(p.getUniqueId()))
        {
            if(!isInGreenBase(loc))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else if(Main.getINSTANCE().blueTeam.contains(p.getUniqueId()))
        {
            if(!isInBlueBase(loc))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return true;
        }
    }

    private static boolean isInRedBase(Location p)
    {
        return Main.getINSTANCE().redBase.isInArea(p);
    }

    private static boolean isInBlueBase(Location p)
    {
        return Main.getINSTANCE().blueBase.isInArea(p);
    }

    private static boolean isInGreenBase(Location p)
    {
        return Main.getINSTANCE().greenBase.isInArea(p);
    }
}
