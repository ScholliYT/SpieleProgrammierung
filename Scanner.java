import greenfoot.*;
import java.util.*;
public class Scanner extends Actor
{
    private ArrayList<Player> players;
    public Scanner(ArrayList<Player> players)
    {
        this.players = new ArrayList<>();
        for(Player p: players)
        {
            this.players.add(p);
        }
    }

    public void act()
    {
        boolean continueGame = true;
        if(this.getWorld().getObjects(Player.class).size() == 0)
            continueGame = false;

        int i = 0;    
        for(Player p: this.players)
        {
            if(!p.isMovable())
                i++;
        }
        continueGame = !(i == this.players.size());

        if(!continueGame)
            this.stoppen();
    }

    private void stoppen()
    {
        Greenfoot.stop();
    }

    // public boolean isWinner()
    // {
    // if(this.floes[this.length-1][this.hight-1].isTouching())
    // return true;

    // List players = this.getObjects(Player.class);
    // if(players.size() == 0)
    // return true;

    // System.out.println(players.size());

    // for(Player player: this.getObjects(Player.class)) 
    // {
    // if(!player.getMovable())
    // return true;
    // }

    // return false;
    // }
}
