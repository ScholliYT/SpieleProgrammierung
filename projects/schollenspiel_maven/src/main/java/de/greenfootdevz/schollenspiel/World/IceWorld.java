package de.greenfootdevz.schollenspiel.World;

import de.greenfootdevz.schollenspiel.Actor.Floe;
import de.greenfootdevz.schollenspiel.Actor.Player;
import greenfoot.*;
import java.util.*;
public class IceWorld extends World
{
    private Floe[][] floes;
    private int length, hight;

    public IceWorld()
    {
        super(1000, 800, 1);
        this.setDataFields(10,8);
    }

    public void setDataFields(int length, int hight)
    {
        this.length = Math.abs(length);
        this.hight = Math.abs(hight);
        this.floes = new Floe[this.length][this.hight];
        setUp();
    }

    private void setUp()
    {
        this.floes();
        this.paths();
        this.findPath();

        String[] tmp = {"UP", "DOWN", "LEFT", "RIGHT"};
        Player player = new Player(tmp, 2);
        this.addObject(player, 90, 90);
    }
    
    public boolean isWinner()
    {
        if(this.floes[this.length-1][this.hight-1].isTouching())
            return true;
            
        for(Player player: this.getObjects(Player.class)) 
        {
            if(!player.getMovable())
                return true;
        }
        
        return false;
    }
    
    public Floe getFinish()
    {
        return this.floes[this.length-1][this.hight-1];
    }

    public void setAllWhite()
    {
        int i = 0;
        int j = 0;
        while(j < this.length)
        {
            i = 0;
            while(i < this.hight)
            {
                if(!(j == this.length-1 && i == this.hight-1)) // läuft nicht :(
                    this.floes[j][i].colorSetup(Color.WHITE);
                i++;
            }
            j++;
        }
    }

    private void floes()
    {
        int i = 0;
        int distance = 88;
        while(i < this.hight)
        {
            int j = 0;
            while(j < this.length)
            {
                Floe tmp = new Floe(false, 90);
                this.floes[j][i] = tmp;
                this.addObject(tmp, distance *(j+1), distance * (i+1));
                j++;
            }
            i++;
        }
        this.floes[this.length-1][this.hight-1].colorSetup(Color.GREEN);
        this.floes[this.length-1][this.hight-1].setSate(true);
    }

    private void paths()
    {
        int i = 0;
        int distance = 130;
        Random random = new Random();
        while(i < this.hight)
        {
            int j = 0;
            while(j < this.length-1)
            {
                Floe floe = new Floe(true);
                floe.paths(15, 20, Color.BLACK);
                this.addObject(floe, distance + 88*j, (distance-40) * (i+1) + random.nextInt(4)-2);
                j++;
            }
            i++;
        }

        i = 0;
        while(i < this.hight-1)
        {
            int j = 0;
            while(j < this.length)
            {
                Floe floe = new Floe(true);
                floe.paths(15, 20, Color.BLACK);
                this.addObject(floe, (distance-40 + random.nextInt(4)-3) * (j+1), distance + 88*i);
                j++;
            }
            i++;
        }
    }

    public void findPath()
    {
        int i = 0;
        int j = 0;
        Random random = new Random();
        while(j != this.length-1 || i != this.hight-1)
        {
            Floe current = this.floes[j][i];
            current.setSate(true);
            current.colorSetup(Color.GREEN);
            if(random.nextInt(3) < 1 && j < this.length-1 )
                j++;

            current = this.floes[j][i];
            current.setSate(true);
            current.colorSetup(Color.GREEN);

            if(random.nextInt(4) > 1)
                if(random.nextInt(3) > 0 && i < this.hight-1)
                    i++;
                else if(random.nextInt(3) > 0 && i > 0 && this.isPossibleDown(i, j))
                    i--;
        }
    }

    private boolean isPossibleDown(int i, int j)
    {
        if(j > 0 && j < this.length-1 && j < this.hight-1)
            return this.floes[i-1][j-1] == null && this.floes[i-1][j+1] == null;

        return false;
    }
}
