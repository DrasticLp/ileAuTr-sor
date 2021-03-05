package com.drastic.plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.drastic.plugin.Main;
import com.drastic.plugin.runnables.LobbyRunnable;

public class CommandStart implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args)
    {
        if(Bukkit.getOnlinePlayers().size() >= 1 && !Main.getINSTANCE().lobbyRunnable.start && Main.getINSTANCE().areBasesSetup())
        {

            Main.getINSTANCE().lobbyRunnable = new LobbyRunnable();

            Main.getINSTANCE().lobbyRunnable.runTaskTimer(Main.getINSTANCE(), 0l, 20l);
            Main.getINSTANCE().lobbyRunnable.start = true;
            return true;
        }
        else
        {
            if(!Main.getINSTANCE().areBasesSetup())
            {
                sender.sendMessage("§cLes bases de équipes n'ont pas été créées");
            }
            return false;
        }
    }

}
