import greenfoot.*; 
import java.lang.*;
public class Player extends Actor
{
    private String[] moves; // 0 = up; 1 = down; 2 = right; 3 = left
    private int speed;
    private long distance;
    private boolean movable;
    private String name;
    public Player(String[] moves, int speed, String name)
    {
        this.speed = speed;
        this.moves = new String[4];
        this.colorSetup(5, Color.BLUE);
        this.movable = true;
        this.name = name;
        for(int i = 0; i < this.moves.length;i++)
        {
            this.moves[i] = moves[i];
        }
    }

    public void act() 
    {
        this.movable = this.isTouching(Floe.class);
        if(this.movable)
            this.move();
            
        IceWorld world = ((IceWorld) this.getWorld());
        Floe finish = world.getFinish();
        world.setAllWhite();
        
        this.distance = (long) Math.sqrt((Math.pow(this.getX() - finish.getX(), 2) + Math.pow(this.getY() - finish.getY(), 2))); 
    }   
    
    public long getDistance()
    {
        return this.distance;
    }
    
    public boolean isMovable()
    {
        return this.movable;
    }

    public void move()
    {
        if(Greenfoot.isKeyDown(this.moves[0]))
            this.setLocation(this.getX(), this.getY() - speed);
        if(Greenfoot.isKeyDown(this.moves[1]))
            this.setLocation(this.getX(), this.getY() + speed);
        if(Greenfoot.isKeyDown(this.moves[2]))
            this.setLocation(this.getX() - speed, this.getY());
        if(Greenfoot.isKeyDown(this.moves[3]))
            this.setLocation(this.getX() + speed, this.getY());
    }

    private void colorSetup(int size, Color color)
    {
        GreenfootImage image = new GreenfootImage(size, size);
        image.setColor(color);
        image.fillOval(0, 0, size-1, size-1);
        this.setImage(image);
    }

    public boolean getMovable()
    {
        return this.movable;
    }
    
    public String getName()
    {
        return this.name;
    }
}
