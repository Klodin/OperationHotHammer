
package OperationHotHammer.Display;

import OperationHotHammer.Core.Game;
import OperationHotHammer.Core.Util.Settings;
import java.util.ArrayList;

public enum Display implements IObserver{
    INSTANCE;
    
    ArrayList<IObservee> observees = new ArrayList<>();

    @Override
    public void addObservee(IObservee rl) {
        observees.add(rl);
    }
  
    public void initialize() {
     
        Game.INSTANCE.initialize();
        addObservee(Game.INSTANCE);
        
    }
    
    public void render(float delta){
        for(IObservee o : observees){
            o.update(delta);
        }
        for(IObservee o : observees){
            o.render();
        }
    }
    
    public float getX() {
        return Game.INSTANCE.getX();
    }

    public float getY() {
        return Game.INSTANCE.getY();
    }

    public int getScreenWidth() {
        return 300;
    }

    public int getScreenHeight() {
        return 300;
    }
    
    public boolean isRunning() {
        return Game.INSTANCE.isRunning();
    }
} 
