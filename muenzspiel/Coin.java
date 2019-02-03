import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class coin here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Coin extends Actor
{
    private MouseInfo m;
    private GreenfootImage image;
    private Color zustand;
    private World w;
    private int time=30;
    private long startTime=System.currentTimeMillis();
    private long aktuelleZeit=0;
    private int size;
    private int score=0;
    private Color spielerfarbe;
    private Color color;
    private int value;
    /**
     * Act - do whatever the coin wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Coin(int size, Color color, World w, int values){
        
        this.size=size;
        image = new GreenfootImage (size, size);
        image.setColor (color);
        image.fillOval (0, 0, size-1, size-1);
        setImage (image);
        this.color=color;
        this.w=w;
        value=values;
        // switch(values){
        // case 2:
        // setImage("coin2.mpw");
        // break;
        // case 3:
        // setImage("coin3.mpw");
        // break;
        //}
    }

    private int getValue(){
    return value;
    }
    

    private void showScore(){
        aktuelleZeit=time-((System.currentTimeMillis()-startTime)/1000);
        this.getWorld().showText("Verbleibende Zeit: "+aktuelleZeit, 150,this.getWorld().getHeight()-20 );
    }

    public int getSize(){
        return size;
    }

    public void act() 
    {
        m = Greenfoot.getMouseInfo();
        showScore();
        if(m!=null){

            

            if (Greenfoot.mouseClicked(null)) {  
                m = Greenfoot.getMouseInfo();

                if(m.getActor()==this){
                    if(getColor()==Color.WHITE){
                        setColor(color);
                    }
                    setColor(Color.WHITE);
                }        
            }
        }

        if (aktuelleZeit<=0){
            auswertung();
            Greenfoot.stop();
        }       

    }

    public Color getColor(){
        return color;
    }

    public void setColor(Color color){
        image.setColor (Color.WHITE);
        image.fillOval (0, 0, getSize()-1, getSize()-1);//Player.getColor();
        setImage(image);

        zustand=Color.WHITE;
    }

    public boolean isTouching(){
        return this.isTouching(Coin.class);
    }

    public Color getZustand() {
        return zustand;
    }

    private int auswertung(){
        
        for (Coin c:w.getObjects(Coin.class)){
            if(c.getZustand()==Color.WHITE){
                score+=c.getValue();
            }
        }
        
        showEndscreen(score);
        return score;
    }
    
    private void showEndscreen(int score){
        this.getWorld().showText("Congratulations your Score is "+score, this.getWorld().getWidth()/2,this.getWorld().getHeight()/2 );
    }
}
