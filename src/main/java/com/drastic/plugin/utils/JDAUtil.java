package com.drastic.plugin.utils;

import javax.security.auth.login.LoginException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.drastic.plugin.Main;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;

public class JDAUtil
{
    public static final String token = "ODEyMDcwMzgxMDIwMTE5MDUz.YC7Zeg._ulsxhrXV84k1y-bXukNhfnqowU";

    private static JDA jda;

    public static void startBot()
    {
        try
        {
            jda = JDABuilder.createDefault(token).build();
        }
        catch(LoginException e)
        {
            e.printStackTrace();
        }
    }

    public static void stopBot()
    {
        jda.shutdown();
    }

    public static void linkAccount(Player p, String name)
    {
        Main.getINSTANCE().getConfig().set("token." + p.getUniqueId().toString(), name);
    }

    public static void linkPlayers()
    {
        for(Player p : Bukkit.getOnlinePlayers())
        {
            String configName = "token." + p.getUniqueId().toString();

            if(!Main.getINSTANCE().getConfig().getString(configName).isEmpty())
            {
                String tag = Main.getINSTANCE().getConfig().getString(configName);

                try
                {
                    String[] args = tag.split("#");
                    Guild g = jda.getGuildById(Main.getINSTANCE().getConfig().getString("serverId"));

                    User u = jda.getUserByTag(args[0], args[1]);

                    Member m = g.getMember(u);

                    VoiceChannel c = g.getVoiceChannelById(Main.getINSTANCE().getConfig().getString("lobbyChannelId"));

                    if(c.getMembers().contains(m))
                    {
                        VoiceChannel c2 = null;

                        boolean mute = true;

                        if(Main.getINSTANCE().redTeam.contains(p.getUniqueId()))
                        {
                            c2 = g.getVoiceChannelById(Main.getINSTANCE().getConfig().getString("redChannelId"));
                        }
                        else if(Main.getINSTANCE().greenTeam.contains(p.getUniqueId()))
                        {
                            c2 = g.getVoiceChannelById(Main.getINSTANCE().getConfig().getString("greenChannelId"));
                        }
                        else if(Main.getINSTANCE().blueTeam.contains(p.getUniqueId()))
                        {
                            c2 = g.getVoiceChannelById(Main.getINSTANCE().getConfig().getString("blueChannelId"));
                        }
                        else
                        {
                            c2 = g.getVoiceChannelById(Main.getINSTANCE().getConfig().getString("godChannelId"));
                            mute = false;
                        }

                        g.moveVoiceMember(m, c2).queue();
                        g.mute(m, mute).queue();
                    }
                }
                catch(Exception e)
                {

                }
            }
        }
    }

    public static JDA getJDA()
    {
        return jda;
    }
}
