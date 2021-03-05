package com.drastic.plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.drastic.plugin.Main;

public class CommandForceStart implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args)
    {
        if(Main.getINSTANCE().lobbyRunnable.start)
        {
            Main.getINSTANCE().lobbyRunnable.timer = 6;
            return true;
        }
        return false;
    }

}
