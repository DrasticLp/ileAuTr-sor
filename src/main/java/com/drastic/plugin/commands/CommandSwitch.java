package com.drastic.plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.drastic.plugin.utils.EventUtils;

public class CommandSwitch implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args)
    {
        EventUtils.randomPlayerSwitch();
        return true;
    }

}
