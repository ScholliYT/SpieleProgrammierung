import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{
    ArrayList<Ball> baelle= new ArrayList<>();
    ArrayList<Player> playerer= new ArrayList<>();
    ArrayList<Score> scorere= new ArrayList<>();
    ArrayList<String> tableplayer= new ArrayList<>();
    int deathcounter =0;

    private  int[] score = {3,3,3,3};
    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public MyWorld()
    {    

        super(600, 600, 1); 

        addPlayer();
        addScore();

    }

    public void deletPlay(int i){
        Player p = playerer.get(i);
        if(p.getX() == 30 && p.getY() != 30){
            tableplayer.add("Der Spieler Oben");    
        }
        else if(p.getX() != 30 && p.getY() == 30 ){
         tableplayer.add("Der Spieler Links");      
        }else if(p.getX() != 570 && p.getY() == 570 ){
         tableplayer.add("Der Spieler Rechts");      
        }
        else if(p.getX() == 570 && p.getY() != 570 ){
         tableplayer.add("Der Spieler Rechts");      
        }

        playerer.set(i, null);
        removeObject(p);
        this.deathcounter++;
        if(this.deathcounter == 3)
        {
            Greenfoot.stop();
        }
        //GreenfootImage table = new GreenfootImage (600, 600);    
        //table.drawString(tableplayer.get(3) +" ist erster\n"+
        //tableplayer.get(2) +"ist zweiter \n"+ tableplayer.get(1)+ " ist dritter \n"+ tableplayer.get(3)+ " ist vierter", 300, 300);
       //Score

    }

    public int[] getScore(){
        return score;
    }

    public int getSco(int i){
        return score[i];
    }

    public  void  upDate(int[] k){
        score = k;
    }

    public void newBall(){
        boolean k = true;
        this.stopped();
        long now =System.currentTimeMillis();
        for(Ball b: baelle)
        {
            b.setLocation(300,300);

            if(k)
                k =! k;

            else
            {
                removeObject(b);

            }
        }
        while(System.currentTimeMillis()-now>1000){}
        this.started();
    }

    public void addPlayer(){
        Player plaOben = new Player(0);
        Player plaUnten = new Player(1);
        Player plaLink = new Player(3);
        Player plaRech = new Player(2);
        playerer.add(plaOben);
        playerer.add(plaRech);
        playerer.add(plaUnten);
        playerer.add(plaLink);
        Ball ballw = new Ball();
        baelle.add(ballw);

        plaOben.setRotation(0);
        plaUnten.setRotation(0);
        this.addObject(plaRech,30,300);
        this.addObject(plaLink,570,300);
        this.addObject(plaOben,300,30);
        this.addObject(plaUnten,300,570);
        this.addObject(ballw,300,300);
    }

    public ArrayList getpla(){
        return playerer;
    }

    public ArrayList getbal(){
        return baelle;
    }

    public Score getsco(int i){
        return scorere.get(i);
    }

    public void addScore(){

        GreenfootImage ol = new GreenfootImage (30, 30);    
        ol.setColor((Color) Color.BLUE);  
        ol.drawString("" + score[0], 15, 15);
        Score sol= new Score();
        sol.setImage(ol);
        scorere.add(sol);

        GreenfootImage or = new GreenfootImage (30, 30);    
        or.setColor((Color) Color.RED);
        or.drawString("" +  score[1], 15, 15);
        Score sur= new Score();
        sur.setImage(or);
        scorere.add(sur);

        GreenfootImage ul = new GreenfootImage (30, 30);    
        ul.setColor((Color) Color.GREEN);  
        ul.drawString("" +  score[2], 15, 15);
        Score sul= new Score();
        sul.setImage(ul);
        scorere.add(sul);

        GreenfootImage ur = new GreenfootImage (30, 30);    
        ur.setColor((Color) Color.YELLOW);  
        ur.drawString("" + score[3], 15, 15);
        Score sor= new Score();
        sor.setImage(ur);
        scorere.add(sor);

        this.addObject(sol,30,30);
        this.addObject(sor,570,30);
        this.addObject(sul,30,570);
        this.addObject(sur,570,570);
    }

    public void newScore(int i){
        if(i==3){

            score[3]= score[3]-1;
            Score s= getsco(3);
            GreenfootImage ur = new GreenfootImage (30, 30);    
            ur.setColor((Color) Color.YELLOW);  
            ur.drawString("" + score[3], 15, 15);

            scorere.get(3).setImage(ur);
        }
        else if(i==0){

            score[0]= score[0]-1;
            Score s= getsco(0);
            GreenfootImage ol = new GreenfootImage (30, 30);    
            ol.setColor((Color) Color.BLUE);  
            ol.drawString("" + score[0], 15, 15);

            scorere.get(0).setImage(ol);
        }
        else if(i==1){
            score[1]= score[1]-1;

            Score s= getsco(1);
            GreenfootImage or = new GreenfootImage (30, 30);    
            or.setColor((Color) Color.GREEN);  
            or.drawString("" + score[1], 15, 15);
            scorere.get(2).setImage(or);
        }
        else if(i==2){
            score[2]= score[2]-1;

            Score s= getsco(2);
            GreenfootImage or = new GreenfootImage (30, 30);    
            or.setColor((Color) Color.RED);  
            or.drawString("" + score[2], 15, 15);

            scorere.get(1).setImage(or);
        }

        if(score[i]==0) 
        {deletPlay(i);
        }
        newBall();
    }
}