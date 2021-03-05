package com.drastic.plugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import com.drastic.plugin.listeners.entity.EntityInteractListener;
import com.drastic.plugin.listeners.player.PlayerBlockListener;
import com.drastic.plugin.listeners.player.PlayerChatListener;
import com.drastic.plugin.listeners.player.PlayerDamageListener;
import com.drastic.plugin.listeners.player.PlayerGuiListener;
import com.drastic.plugin.listeners.player.PlayerItemListener;
import com.drastic.plugin.listeners.player.PlayerJoinListener;
import com.drastic.plugin.listeners.player.PlayerMoveListener;
import com.drastic.plugin.listeners.player.PlayerRespawnListener;
import com.drastic.plugin.listeners.player.PlayerTeleportListener;

public class ListenerManager
{
    public Plugin plugin;
    public PluginManager pm;

    public ListenerManager(Plugin p)
    {
        this.plugin = p;
        this.pm = Bukkit.getPluginManager();
    }

    public void registerListeners()
    {
        pm.registerEvents(new PlayerJoinListener(), this.plugin);
        pm.registerEvents(new PlayerMoveListener(), this.plugin);
        pm.registerEvents(new PlayerBlockListener(), this.plugin);
        pm.registerEvents(new PlayerItemListener(), this.plugin);
        pm.registerEvents(new PlayerGuiListener(), this.plugin);
        pm.registerEvents(new PlayerRespawnListener(), this.plugin);
        pm.registerEvents(new PlayerChatListener(), this.plugin);
        pm.registerEvents(new PlayerDamageListener(), this.plugin);

        pm.registerEvents(new EntityInteractListener(), this.plugin);
        pm.registerEvents(new PlayerTeleportListener(), this.plugin);
   }
}