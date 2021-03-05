package com.drastic.plugin.runnables;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.drastic.plugin.GameStatus;
import com.drastic.plugin.Main;
import com.drastic.plugin.listeners.PluginMessage;
import com.drastic.plugin.player.GamePlayer;
import com.drastic.plugin.scoreboard.ScoreboardManager;
import com.drastic.plugin.utils.EventUtils;
import com.drastic.plugin.utils.FloatTextUtils;
import com.drastic.plugin.utils.GuiUtil;

public class PlayerUpdateRunnable extends BukkitRunnable
{
    public int timer = 20 * 60;
    public int episode = 1;

    public void run()
    {
        if(GameStatus.isStatus(GameStatus.GAME) || GameStatus.isStatus(GameStatus.DEATHMATCH))
        {
            setFloatings();

            if(this.timer >= 1)
            {
                this.timer--;

                if(this.timer == 20 * 60 - 30 && this.episode == 1)
                {
                    for(Player p : Bukkit.getOnlinePlayers())
                    {
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§cVous êtes devenu vulnérable aux dégats");
                    }

                    Main.getINSTANCE().invincibility = false;
                }
                if(this.episode == 1 && (this.timer <= 15 || this.timer == 30) && this.timer != 0)
                {
                    for(Player p : Bukkit.getOnlinePlayers())
                    {
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§9Activation du PvP dans §6" + this.timer + " §9secondes");
                    }
                }

                if(this.timer == Main.getINSTANCE().randomTimer && this.episode == Main.getINSTANCE().randomEp)
                {
                    Random r = new Random();
                    int rInt = r.nextInt(3);

                    if(rInt == 0)
                    {
                        int offset = r.nextInt(500);
                        int xOff = ThreadLocalRandom.current().nextInt(-offset, offset);

                        int offset2 = r.nextInt(500);
                        int zOff = ThreadLocalRandom.current().nextInt(-offset2, offset2);

                        new EventUtils().sendMeteor(Bukkit.getWorld(Main.getWorldName()), xOff, zOff);
                    }
                    else if(rInt == 1)
                    {

                        int i = new Random().nextInt(3);

                        if(i == 0)
                        {
                            double x = Main.getINSTANCE().getConfig().getDouble("boat.firstLoc.X");
                            double z = Main.getINSTANCE().getConfig().getDouble("boat.firstLoc.Z");

                            new EventUtils().sendBoat(Bukkit.getWorld(Main.getWorldName()), x, z);
                        }
                        else if(i == 1)
                        {
                            double x = Main.getINSTANCE().getConfig().getDouble("boat.secondLoc.X");
                            double z = Main.getINSTANCE().getConfig().getDouble("boat.secondLoc.Z");

                            new EventUtils().sendBoat(Bukkit.getWorld(Main.getWorldName()), x, z);
                        }
                        else
                        {
                            double x = Main.getINSTANCE().getConfig().getDouble("boat.thirdLoc.X");
                            double z = Main.getINSTANCE().getConfig().getDouble("boat.thirdLoc.Z");

                            new EventUtils().sendBoat(Bukkit.getWorld(Main.getWorldName()), x, z);
                        }
                    }
                    else
                    {
                        EventUtils.randomPlayerSwitch();
                    }
                }

            }
            else
            {
                this.timer = 20 * 60;
                this.episode++;

                if(this.episode == 4)
                {
                    GameStatus.setSatus(GameStatus.DEATHMATCH);

                    for(Player p : Bukkit.getOnlinePlayers())
                    {
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§9Début de la phase §cDEATHMATCH");
                        p.playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 10f, 1f);
                        GuiUtil.spawnPnj();
                    }
                }
                else if(this.episode == 2)
                {
                    for(Player p : Bukkit.getOnlinePlayers())
                    {
                        p.sendMessage(Main.getINSTANCE().getPrefix() + "§9Le PvP est maintenant activé");
                        p.playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 10f, 1f);
                        p.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 1, 5));
                    }
                }

                for(Player p : Bukkit.getOnlinePlayers())
                {
                    p.sendMessage(Main.getINSTANCE().getPrefix() + "§9Début de l'épisode §6" + this.episode);
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10f, 1f);

                }
            }

            for(Player p : Bukkit.getOnlinePlayers())
            {
                ScoreboardManager.scoreboardGame.get(p).updateLine(6, ChatColor.WHITE + "Joueurs: " + ChatColor.YELLOW + Bukkit.getOnlinePlayers().size() + "§8/§e" + Bukkit.getMaxPlayers());

                setDisplayName(p);

                sendMessage(p);

                if(!Main.getINSTANCE().perms.containsKey(p.getUniqueId()))
                {
                    PermissionAttachment attachment = p.addAttachment(Main.getINSTANCE());
                    attachment.setPermission("iot.team", true);
                    attachment.setPermission("iot.link", true);
                    attachment.setPermission("iot.bank", true);
                    Main.getINSTANCE().perms.put(p.getUniqueId(), attachment);
                }

                if(GamePlayer.gamePlayers.containsKey(p.getName()))
                {
                    performPowers(p);
                    updatePlayerTeams(p);
                    updatePlayerUpgrades(p);

                    GamePlayer gp = GamePlayer.gamePlayers.get(p.getName());

                    ScoreboardManager.scoreboardGame.get(p).updateLine(9, "§fPoints: §9" + gp.getPoints());

                    ScoreboardManager.scoreboardGame.get(p).updateLine(2, "§6Épisode: §e" + this.episode);
                    ScoreboardManager.scoreboardGame.get(p).updateLine(3, "§6Temps: §e" + getFormatTimer());

                    ScoreboardManager.scoreboardGame.get(p).updateLine(8, ChatColor.WHITE + "Phase: " + ChatColor.GREEN + GameStatus.getName());
                }
                else
                {
                    new GamePlayer(p.getName());
                }
            }
        }
        else
        {
            for(Player p : Bukkit.getOnlinePlayers())
            {
                setDisplayName(p);

                sendMessage(p);

                ScoreboardManager.scoreboardGame.get(p).updateLine(6, ChatColor.WHITE + "Joueurs: " + ChatColor.YELLOW + Bukkit.getOnlinePlayers().size() + "§8/§e" + Bukkit.getMaxPlayers());

                if(GamePlayer.gamePlayers.containsKey(p.getName()))
                {
                    GamePlayer gp = GamePlayer.gamePlayers.get(p.getName());

                    if(!gp.isGod)
                    {
                        if(!Main.getINSTANCE().blueTeam.contains(p.getUniqueId()) && !Main.getINSTANCE().greenTeam.contains(p.getUniqueId()) && !Main.getINSTANCE().redTeam.contains(p.getUniqueId()))
                        {
                            this.openTeamInv(p);
                        }
                    }
                }
                else
                {
                    new GamePlayer(p.getName());
                }
            }

            if(GameStatus.isStatus(GameStatus.STOP))
            {
                Location l = new Location(Bukkit.getWorld(Main.getWorldName()), Main.getINSTANCE().getConfig().getDouble("egg.X") + 0.5, Main.getINSTANCE().getConfig().getDouble("egg.Y") + 2, Main.getINSTANCE().getConfig().getDouble("egg.Z") + 0.5);

                Firework firework = Bukkit.getWorld(Main.getWorldName()).spawn(l, Firework.class);
                FireworkMeta data = (FireworkMeta)firework.getFireworkMeta();
                data.addEffects(FireworkEffect.builder().withColor(Color.PURPLE).withColor(Color.WHITE).with(Type.STAR).withFlicker().build());
                data.setPower(1);
                firework.setFireworkMeta(data);
            }
        }

    }

    private void setDisplayName(Player p)
    {
        String s = "";

        GamePlayer gp = GamePlayer.gamePlayers.get(p.getName());

        if(Main.getINSTANCE().blueTeam.contains(p.getUniqueId()))
        {
            s = "§9";
        }
        else if(Main.getINSTANCE().redTeam.contains(p.getUniqueId()))
        {
            s = "§c";
        }
        else if(Main.getINSTANCE().greenTeam.contains(p.getUniqueId()))
        {
            s = "§a";
        }
        else if(gp != null && gp.isGod)
        {
            s = "§6";
        }
        else
        {
            s = "§f";
        }

        if(!p.getDisplayName().startsWith("§"))
        {
            p.setDisplayName(s + p.getDisplayName() + "§f");
        }
        else
        {
            p.setPlayerListName(s + p.getDisplayName().substring(2) + "§f");
        }

    }

    private void sendMessage(Player p)
    {
        String s = "";

        GamePlayer gp = GamePlayer.gamePlayers.get(p.getName());

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
        else if(gp != null && gp.isGod)
        {
            s = "§6Dieu";
        }
        else
        {
            s = "§cAucune";
        }

        PluginMessage.sendCustomData(p, s);
    }

    private void setFloatings()
    {
        FloatTextUtils.deleteFloating("Points:");
        Location red = new Location(Bukkit.getWorld(Main.getWorldName()), Main.getINSTANCE().redBase.teleportLoc.getX() - 0.5, Main.getINSTANCE().redBase.teleportLoc.getY() - 1, Main.getINSTANCE().redBase.teleportLoc.getZ() - 0.5);
        Location blue = new Location(Bukkit.getWorld(Main.getWorldName()), Main.getINSTANCE().blueBase.teleportLoc.getX() - 0.5, Main.getINSTANCE().blueBase.teleportLoc.getY() - 1, Main.getINSTANCE().blueBase.teleportLoc.getZ() - 0.5);
        Location green = new Location(Bukkit.getWorld(Main.getWorldName()), Main.getINSTANCE().greenBase.teleportLoc.getX() - 0.5, Main.getINSTANCE().greenBase.teleportLoc.getY() - 1, Main.getINSTANCE().greenBase.teleportLoc.getZ() - 0.5);
        FloatTextUtils.setFloatingText(red, "Points: §9" + Main.getINSTANCE().redPoints, false);
        FloatTextUtils.setFloatingText(green, "Points: §9" + Main.getINSTANCE().greenPoints, false);
        FloatTextUtils.setFloatingText(blue, "Points: §9" + Main.getINSTANCE().bluePoints, false);
    }

    /*
     * private void teleportPnjs()
     * {
     * List<Villager> villagers = new ArrayList<Villager>();
     * World world = Bukkit.getWorld("world");
     * for(Entity e : world.getEntities())
     * {
     * if(e instanceof Villager && e.getCustomName() != null && e.getCustomName().equalsIgnoreCase("§9Marchand"))
     * {
     * villagers.add((Villager)e);
     * }
     * }
     * double x = Main.getINSTANCE().getConfig().getDouble("egg.X");
     * double y = Main.getINSTANCE().getConfig().getDouble("egg.Y") - 1;
     * double z = Main.getINSTANCE().getConfig().getDouble("egg.Z");
     * if(villagers.size() == 4)
     * {
     * villagers.get(0).teleport(new Location(world, x + 3, y, z));
     * villagers.get(1).teleport(new Location(world, x - 3, y, z));
     * villagers.get(2).teleport(new Location(world, x, y, z + 3));
     * villagers.get(3).teleport(new Location(world, x, y, z - 3));
     * }
     * }
     */

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

    private void updatePlayerUpgrades(Player p)
    {
        GamePlayer gp = GamePlayer.gamePlayers.get(p.getName());

        if(Main.getINSTANCE().redTeam.contains(p.getUniqueId()))
        {
            if(Main.getINSTANCE().fireResistanceRed)
            {
                p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 4, 0));
            }
            if(Main.getINSTANCE().resistanceRed)
            {
                if(!gp.isGuerrier())
                    p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 4, 0));
            }
            if(Main.getINSTANCE().strengthRed)
            {
                if(!gp.isAssasin())
                    p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 4, 0));
            }
        }
        else if(Main.getINSTANCE().greenTeam.contains(p.getUniqueId()))
        {
            if(Main.getINSTANCE().fireResistanceGreen)
            {
                p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 4, 0));
            }
            if(Main.getINSTANCE().resistanceGreen)
            {
                if(!gp.isGuerrier())
                    p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 4, 0));
            }
            if(Main.getINSTANCE().strengthGreen)
            {
                if(!gp.isAssasin())
                    p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 4, 0));
            }
        }
        else if(Main.getINSTANCE().blueTeam.contains(p.getUniqueId()))
        {
            if(Main.getINSTANCE().fireResistanceBlue)
            {
                p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 4, 0));
            }
            if(Main.getINSTANCE().resistanceBlue)
            {
                if(!gp.isGuerrier())
                    p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 4, 0));
            }
            if(Main.getINSTANCE().strengthBlue)
            {
                if(!gp.isAssasin())
                    p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 4, 0));
            }
        }
    }

    private void updatePlayerTeams(Player p)
    {
        GamePlayer gp = GamePlayer.gamePlayers.get(p.getName());

        if(Main.getINSTANCE().blueTeam.contains(p.getUniqueId()))
        {
            gp.team = Main.getINSTANCE().blueTeam;
        }
        else if(Main.getINSTANCE().redTeam.contains(p.getUniqueId()))
        {
            gp.team = Main.getINSTANCE().redTeam;
        }
        else if(Main.getINSTANCE().greenTeam.contains(p.getUniqueId()))
        {
            gp.team = Main.getINSTANCE().greenTeam;
        }
    }

    private void performPowers(Player p)
    {
        GamePlayer gp = GamePlayer.gamePlayers.get(p.getName());

        if(gp.isArcher())
        {
            if(Main.getINSTANCE().redTeam.contains(p.getUniqueId()))
            {
                if(Main.getINSTANCE().speedRed)
                {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 4, 1));
                }
                else
                {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 4, 0));
                }
            }
            else if(Main.getINSTANCE().greenTeam.contains(p.getUniqueId()))
            {
                if(Main.getINSTANCE().speedGreen)
                {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 4, 1));
                }
                else
                {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 4, 0));
                }
            }
            else if(Main.getINSTANCE().blueTeam.contains(p.getUniqueId()))
            {
                if(Main.getINSTANCE().speedBlue)
                {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 4, 1));
                }
                else
                {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 4, 0));
                }
            }
        }
        else if(gp.isAssasin())
        {
            if(isDay())
            {
                if(Main.getINSTANCE().redTeam.contains(p.getUniqueId()))
                {
                    if(Main.getINSTANCE().speedRed)
                    {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 4, 1));
                    }
                    else
                    {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 4, 0));
                    }

                    if(!Main.getINSTANCE().strengthRed)
                    {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 4, 0));
                    }
                }
                else if(Main.getINSTANCE().greenTeam.contains(p.getUniqueId()))
                {
                    if(Main.getINSTANCE().speedGreen)
                    {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 4, 1));
                    }
                    else
                    {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 4, 0));
                    }

                    if(!Main.getINSTANCE().strengthGreen)
                    {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 4, 0));
                    }
                }
                else if(Main.getINSTANCE().blueTeam.contains(p.getUniqueId()))
                {
                    if(Main.getINSTANCE().speedBlue)
                    {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 4, 1));
                    }
                    else
                    {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 4, 0));
                    }

                    if(!Main.getINSTANCE().strengthBlue)
                    {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 4, 0));
                    }
                }
            }
            else
            {
                if(Main.getINSTANCE().redTeam.contains(p.getUniqueId()))
                {
                    if(Main.getINSTANCE().strengthRed)
                    {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 4, 1));
                    }
                    else
                    {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 4, 0));
                    }

                    if(Main.getINSTANCE().speedRed)
                    {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 4, 0));
                    }
                }
                else if(Main.getINSTANCE().greenTeam.contains(p.getUniqueId()))
                {
                    if(Main.getINSTANCE().strengthGreen)
                    {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 4, 1));
                    }
                    else
                    {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 4, 0));
                    }

                    if(Main.getINSTANCE().speedGreen)
                    {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 4, 0));
                    }
                }
                else if(Main.getINSTANCE().blueTeam.contains(p.getUniqueId()))
                {
                    if(Main.getINSTANCE().strengthBlue)
                    {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 4, 1));
                    }
                    else
                    {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 4, 0));
                    }

                    if(Main.getINSTANCE().speedBlue)
                    {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 4, 0));
                    }
                }
            }
        }
        else if(gp.isGuerrier())
        {
            if(Main.getINSTANCE().redTeam.contains(p.getUniqueId()))
            {
                if(!Main.getINSTANCE().speedRed)
                {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 4, 0));
                }

                if(Main.getINSTANCE().resistanceRed)
                {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 4, 1));
                }
                else
                {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 4, 0));
                }
            }
            else if(Main.getINSTANCE().greenTeam.contains(p.getUniqueId()))
            {
                if(!Main.getINSTANCE().speedGreen)
                {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 4, 0));
                }

                if(Main.getINSTANCE().resistanceGreen)
                {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 4, 1));
                }
                else
                {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 4, 0));
                }
            }
            else if(Main.getINSTANCE().blueTeam.contains(p.getUniqueId()))
            {
                if(!Main.getINSTANCE().speedBlue)
                {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 4, 0));
                }

                if(Main.getINSTANCE().resistanceBlue)
                {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 4, 1));
                }
                else
                {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 4, 0));
                }
            }

            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 4, 0));
        }
        else if(!gp.isGod)
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
    }

    private String getFormatTimer()
    {
        return new SimpleDateFormat("mm:ss").format(new Date(Main.getINSTANCE().playerUpdateRunnable.timer * 1000));
    }

    private boolean isDay()
    {
        long time = Bukkit.getWorld(Main.getWorldName()).getTime();
        return time < 12300 || time > 23850;
    }

}
