package com.drastic.plugin.commands;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.drastic.plugin.Main;

public class CommandGetRandom implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args)
    {
        sender.sendMessage(Main.getINSTANCE().getPrefix() + "§2Un événement aléatoire se produira à l'épisode §9" + Main.getINSTANCE().randomEp + " §2à §9" + new SimpleDateFormat("mm:ss").format(new Date(Main.getINSTANCE().randomTimer * 1000)));
        return true;
    }

}
