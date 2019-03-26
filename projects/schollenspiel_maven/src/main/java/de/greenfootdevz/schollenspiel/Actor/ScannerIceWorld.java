package de.greenfootdevz.schollenspiel.Actor;

import de.greenfootdevz.schollenspiel.World.IceWorld;
import greenfoot.*;
import java.util.*;
public class ScannerIceWorld extends Actor
{
    public ScannerIceWorld()
    {
       
    }

    public void act()
    {
        List player = this.getWorld().getObjects(Player.class);
        if(player.size() == 0)
            this.stoppen();

        int i = 0;    
        for(Player p: this.getWorld().getObjects(Player.class))
        {
            if(!p.isMovable())
                i++;
        }

        if((i == player.size()))
            this.stoppen();
    }

    private void stoppen()
    {
       ((IceWorld) this.getWorld()).ausgeben(((IceWorld) this.getWorld()).getWinners());
    }

    
}
