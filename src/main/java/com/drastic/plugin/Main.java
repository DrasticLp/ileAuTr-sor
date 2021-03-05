package com.drastic.plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import com.drastic.plugin.commands.CommandBank;
import com.drastic.plugin.commands.CommandBoat;
import com.drastic.plugin.commands.CommandForceStart;
import com.drastic.plugin.commands.CommandGetRandom;
import com.drastic.plugin.commands.CommandLink;
import com.drastic.plugin.commands.CommandMap;
import com.drastic.plugin.commands.CommandMeteor;
import com.drastic.plugin.commands.CommandPoints;
import com.drastic.plugin.commands.CommandSetEpisode;
import com.drastic.plugin.commands.CommandSetRandom;
import com.drastic.plugin.commands.CommandSetRegion;
import com.drastic.plugin.commands.CommandSetTeam;
import com.drastic.plugin.commands.CommandStart;
import com.drastic.plugin.commands.CommandSwitch;
import com.drastic.plugin.commands.CommandTeam;
import com.drastic.plugin.commands.CommandVanish;
import com.drastic.plugin.listeners.ListenerManager;
import com.drastic.plugin.runnables.LobbyRunnable;
import com.drastic.plugin.runnables.PlayerUpdateRunnable;
import com.drastic.plugin.utils.BasesUtil;
import com.drastic.plugin.utils.ConstrucTabComplete;
import com.drastic.plugin.utils.JDAUtil;
import com.drastic.plugin.utils.RegionManager;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin
{
    private static Main INSTANCE;
    public LobbyRunnable lobbyRunnable;
    public PlayerUpdateRunnable playerUpdateRunnable;

    public RegionManager redBase, blueBase, greenBase;

    public HashMap<UUID, PermissionAttachment> perms = new HashMap<UUID, PermissionAttachment>();

    public List<Player> invisible_list = new ArrayList<Player>();

    public List<UUID> redTeam = new ArrayList<UUID>(), blueTeam = new ArrayList<UUID>(), greenTeam = new ArrayList<UUID>();
    public int redPoints = 0, bluePoints = 0, greenPoints = 0;
    public boolean invincibility = true;

    public int randomEp;
    public int randomTimer;

    public boolean resistanceBlue, resistanceGreen, resistanceRed, strengthBlue, strengthGreen, strengthRed, speedBlue, speedGreen, speedRed, fireResistanceBlue, fireResistanceRed, fireResistanceGreen;

    @Override
    public void onLoad()
    {
        INSTANCE = this;
    }

    @Override
    public void onEnable()
    {
        checkIfBungee();
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "iot");

        lobbyRunnable = new LobbyRunnable();
        playerUpdateRunnable = new PlayerUpdateRunnable();
        playerUpdateRunnable.runTaskTimer(Main.getINSTANCE(), 0l, 20l);

        setCommands();
        loadConfigManager();
        initializeUpgrades();

        // GenStructure.generateSpawn();

        new ListenerManager(this).registerListeners();;
        GameStatus.setSatus(GameStatus.ATTENTE);

        JDAUtil.startBot();

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Plugin 'Ile au trésor' loaded succesfully");
    }

    private void loadConfigManager()
    {
        saveDefaultConfig();
        setDefaultBases();
    }

    private void setCommands()
    {
        addCommand("setregion", new CommandSetRegion());
        addCommand("start", new CommandStart());
        addCommand("setteam", new CommandSetTeam());
        addCommand("fs", new CommandForceStart());
        addCommand("team", new CommandTeam());
        addCommand("meteor", new CommandMeteor());
        addCommand("boat", new CommandBoat());
        addCommand("settime", new CommandSetEpisode());
        addCommand("switch", new CommandSwitch());
        addCommand("vanish", new CommandVanish());
        addCommand("points", new CommandPoints());
        addCommand("map", new CommandMap());
        addCommand("bank", new CommandBank());
        addCommand("setrandom", new CommandSetRandom());
        addCommand("getrandom", new CommandGetRandom());
        addCommand("link", new CommandLink());
    }

    private void initializeUpgrades()
    {
        this.fireResistanceBlue = false;
        this.fireResistanceGreen = false;
        this.fireResistanceRed = false;

        this.speedBlue = false;
        this.speedGreen = false;
        this.speedRed = false;

        this.strengthBlue = false;
        this.strengthGreen = false;
        this.strengthRed = false;

        this.resistanceBlue = false;
        this.resistanceGreen = false;
        this.resistanceRed = false;
    }

    private void setDefaultBases()
    {
        double[] redBaseLoc1 = new double[2];
        double[] redBaseLoc2 = new double[2];
        double[] redBaseLoc3 = new double[3];
        redBaseLoc1[0] = getConfig().getDouble("redBase.firstCorner.X");
        redBaseLoc1[1] = getConfig().getDouble("redBase.firstCorner.Z");

        redBaseLoc2[0] = getConfig().getDouble("redBase.secondCorner.X");
        redBaseLoc2[1] = getConfig().getDouble("redBase.secondCorner.Z");

        redBaseLoc3[0] = getConfig().getDouble("redBase.spawn.X");
        redBaseLoc3[1] = getConfig().getDouble("redBase.spawn.Y");
        redBaseLoc3[2] = getConfig().getDouble("redBase.spawn.Z");

        double[] greenBaseLoc1 = new double[2];
        double[] greenBaseLoc2 = new double[2];
        double[] greenBaseLoc3 = new double[3];
        greenBaseLoc1[0] = getConfig().getDouble("greenBase.firstCorner.X");
        greenBaseLoc1[1] = getConfig().getDouble("greenBase.firstCorner.Z");

        greenBaseLoc2[0] = getConfig().getDouble("greenBase.secondCorner.X");
        greenBaseLoc2[1] = getConfig().getDouble("greenBase.secondCorner.Z");

        greenBaseLoc3[0] = getConfig().getDouble("greenBase.spawn.X");
        greenBaseLoc3[1] = getConfig().getDouble("greenBase.spawn.Y");
        greenBaseLoc3[2] = getConfig().getDouble("greenBase.spawn.Z");

        double[] blueBaseLoc1 = new double[2];
        double[] blueBaseLoc2 = new double[2];
        double[] blueBaseLoc3 = new double[3];
        blueBaseLoc1[0] = getConfig().getDouble("blueBase.firstCorner.X");
        blueBaseLoc1[1] = getConfig().getDouble("blueBase.firstCorner.Z");

        blueBaseLoc2[0] = getConfig().getDouble("blueBase.secondCorner.X");
        blueBaseLoc2[1] = getConfig().getDouble("blueBase.secondCorner.Z");

        blueBaseLoc3[0] = getConfig().getDouble("blueBase.spawn.X");
        blueBaseLoc3[1] = getConfig().getDouble("blueBase.spawn.Y");
        blueBaseLoc3[2] = getConfig().getDouble("blueBase.spawn.Z");

        BasesUtil.setBlueBaseByDefault(blueBaseLoc1, blueBaseLoc2, blueBaseLoc3);
        BasesUtil.setGreenBaseByDefault(greenBaseLoc1, greenBaseLoc2, greenBaseLoc3);
        BasesUtil.setRedBaseByDefault(redBaseLoc1, redBaseLoc2, redBaseLoc3);
    }

    @Override
    public void onDisable()
    {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Plugin 'Ile au trésor' unloaded succesfully");
    }

    public String getPrefix()
    {
        return "§6[§eIle au trésor§6] §2";
    }

    public static Main getINSTANCE()
    {
        return INSTANCE;
    }

    private void addCommand(String name, CommandExecutor e)
    {
        getCommand(name).setExecutor(e);
        getCommand(name).setTabCompleter(new ConstrucTabComplete());
    }

    public static String getWorldName()
    {
        return getINSTANCE().getConfig().getString("worldName");
    }

    public boolean areBasesSetup()
    {
        return blueBase != null & redBase != null && greenBase != null;
    }

    private void checkIfBungee()
    {
        if(!getServer().spigot().getConfig().getConfigurationSection("settings").getBoolean("bungeecord"))
        {
            getLogger().severe("This server is not BungeeCord.");
            getLogger().severe("If the server is already hooked to BungeeCord, please enable it into your spigot.yml aswell.");
            getLogger().severe("Plugin disabled!");
            getServer().getPluginManager().disablePlugin(this);
        }
    }
}
