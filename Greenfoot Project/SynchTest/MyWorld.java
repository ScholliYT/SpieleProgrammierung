import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)
import java.util.*;

public class MyWorld extends World{
    
    public MyWorld(){    
        super(600, 400, 1);
       // ServerLoginFrame frame = new ServerLoginFrame();
       // frame.setVisible(true);
        
       // while(ServerConnection.getConnection() == null){
          //  System.out.println("waiting...");
       // }
        
         ClientMovedActor actor = new ClientMovedActor();
        addObject(actor, 0, 0);
        //ServerConnection.getConnection().start();
      //  ServerConnection.getConnection().synchActor(actor);
        
        //frame.setVisible(false);
        //frame.dispose();
    }
    
    public void act123(){
        SynchronizedTickData data = ServerConnection.getConnection().getMostRecentData();
        if(data == null) return;
        ArrayList<ActorState> states = data.getAllActors();
        ServerConnection con = ServerConnection.getConnection();
        int count = 0;
        for(ActorState state: states){
            System.out.println("1 Actor!");
           if(!con.inMyIdRange(state.getId())){
               Actor a = con.getActorFromId(state.getId(), state);
               a.setLocation(state.getX(), state.getY());
               a.setRotation(state.getRotation());
               if(getObjects(Actor.class).size() != 2) addObject(a, state.getX(), state.getY());
            }
        }
        
    }
    
}