package com.drastic.plugin.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class ConstrucTabComplete implements TabCompleter
{    
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
    {
        if(sender instanceof Player)
        {
            // Player p = (Player)sender;

            if(command.getName().equalsIgnoreCase("setregion"))
            {
                List<String> list = new ArrayList<String>();

                if(args.length == 1)
                {
                    list.add("red");
                    list.add("blue");
                    list.add("green");
                }

                else
                {
                    list.add("~");
                }

                return list;
            }
            else if(command.getName().equalsIgnoreCase("setteam"))
            {
                List<String> list = new ArrayList<String>();

                if(args.length == 2)
                {
                    list.add("red");
                    list.add("blue");
                    list.add("green");
                }

                else
                {
                    for(Player p : Bukkit.getOnlinePlayers())
                    {
                        list.add(p.getName());
                    }
                }
                return list;
            }
            else if(command.getName().equalsIgnoreCase("points"))
            {
                List<String> list = new ArrayList<String>();

                if(args.length == 1)
                {
                    list.add("add");
                    list.add("remove");
                }

                else
                {
                    list.add("red");
                    list.add("green");
                    list.add("blue");

                    for(Player p : Bukkit.getOnlinePlayers())
                    {
                        list.add(p.getName());
                    }
                }

                return list;
            }
        }
        return null;
    }
}
