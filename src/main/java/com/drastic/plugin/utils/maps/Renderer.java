package com.drastic.plugin.utils.maps;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapCursorCollection;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class Renderer extends MapRenderer
{
    public MapCursorCollection col;
    public MapView.Scale scale;

    public Renderer(MapCursorCollection colIn)
    {
        this.col = colIn;
    }
    
    @Override
    public void render(MapView map, MapCanvas canvas, Player p)
    {
        for(int i = 0; i < canvas.getCursors().size() - 1; i++)
        {
            canvas.getCursors().removeCursor(canvas.getCursors().getCursor(i));
        }

        canvas.setCursors(this.col);
        map.setScale(this.scale);
    }
    
    public static void renderMap(MapView m, MapCursorCollection col, MapView.Scale scale)
    {
        for(MapRenderer r : m.getRenderers())
        {
            m.removeRenderer(r);
        }
        
        m.setScale(scale);
        m.addRenderer(new Renderer(col));
    }
}

