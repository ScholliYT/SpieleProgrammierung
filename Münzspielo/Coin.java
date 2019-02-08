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
    private boolean geklickt;
    private GreenfootImage[] bild=new GreenfootImage[6];
    /**
     * Act - do whatever the coin wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Coin(int size, Color color, World w, int values){
        bild[0]=new GreenfootImage("muenzeEinsGelb.png");
        bild[1]=new GreenfootImage("muenzeZweiGelb.png");
        bild[2]=new GreenfootImage("muenzeDreiGelb.png");
        bild[3]=new GreenfootImage("muenzeEinsRot.png");
        bild[4]=new GreenfootImage("muenzeZweiRot.png");
        bild[5]=new GreenfootImage("muenzeDreiRot.png");
        
        this.geklickt=false;
        
        this.size=size;
        setImage(bild[0]);
        
        this.color=color;
        this.w=w;
        value=values;
        switch(value){
            case 2:           
            setImage(bild[1]);

            break;
            case 3:
            setImage(bild[2]);
            break;
        }
        image=getImage();
    }
    
    private boolean getGeklickt(){
    return geklickt;
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
                    if(!getGeklickt()){
                        switch(value){
                            
                            case 2:           
                            setImage(bild[4]);

                            break;
                            case 3:
                            setImage(bild[5]);
                            break;
                            default:
                            setImage(bild[3]);
                            break;
                        }
                        image=getImage();
                        geklickt=true;
                    }else{
                        switch(value){
                            case 2:           
                            setImage(bild[1]);
                            break;
                            case 3:
                            setImage(bild[2]);
                            break;
                            default:
                            setImage(bild[0]);
                            break;
                        }
                        image=getImage();
                        geklickt=false;
                    }
                   
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
            //System.out.println(c);
            if(c.getGeklickt()){
 
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
