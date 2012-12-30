package OperationHotHammer.Display;

import OperationHotHammer.Core.Game;
import OperationHotHammer.Core.Util.Settings;
import java.util.ArrayList;

public enum Display implements IObserver{
    INSTANCE;
    
    ArrayList<IObservee> observees;
    private Game game;
    
    private long lastLoopTime = System.nanoTime();
    
    private Display() {
        game = new Game();
        addObservee(game);
    }    
  
    @Override
    public void addObservee(IObservee rl) {
        observees.add(rl);
    }
  
    public void run(){
       
        while(game.isRunning()) {
            long time = System.nanoTime();
            float delta = ((time - lastLoopTime) / 1000000L);

            if(delta < Settings.FRAME_RATE_MILLISECONDS) {
                try{
                    Thread.sleep(100);//sleep for 100 ms
                }catch(InterruptedException e){
                
                }
                continue;
            }

            lastLoopTime = time;

            for(IObservee o : observees){
                o.update(delta);
            }
            for(IObservee o : observees){
                o.render();
            }
        }
    }
    
    public float getX() {
        return game.getX();
    }

    public float getY() {
        return game.getY();
    }

    public int getScreenWidth() {
        return 300;
    }

    public int getScreenHeight() {
        return 300;
    }
} 
