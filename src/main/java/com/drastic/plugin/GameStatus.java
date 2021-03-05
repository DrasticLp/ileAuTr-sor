package com.drastic.plugin;

public enum GameStatus
{
    ATTENTE,
    GAME,
    DEATHMATCH,
    STOP;

    private static GameStatus currentStatus;

    public static boolean isStatus(GameStatus status)
    {
        return currentStatus == status;
    }

    public static GameStatus getStatus()
    {
        return currentStatus;
    }

    public static void setSatus(GameStatus status)
    {
        GameStatus.currentStatus = status;
    }

    public static String getName()
    {
        if(GameStatus.currentStatus == GAME)
        {
            return "Préparation";
        }
        else if(GameStatus.currentStatus == DEATHMATCH)
        {
            return "Match à Mort";
        }
        else if(GameStatus.currentStatus == STOP)
        {
            return "Fin de Partie";
        }
        else
            return "Attente";

    }
}
