package com.drastic.plugin.listeners;

import org.bukkit.entity.Player;

import com.drastic.plugin.Main;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class PluginMessage
{
    public static void sendCustomData(Player p, String data)
    {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("TablistUpdate");
        out.writeUTF(data);

        p.sendPluginMessage(Main.getINSTANCE(), "iot", out.toByteArray());
    }
}
