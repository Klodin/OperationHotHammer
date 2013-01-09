
package OperationHotHammer.Core;

import OperationHotHammer.Core.GameObjects.Entities.SimpleCreature;
import OperationHotHammer.Core.GameObjects.Entity;
import OperationHotHammer.Core.Util.Debugging;
import OperationHotHammer.Core.Util.Settings;
import OperationHotHammer.Core.Interfaces.IObservee;

public enum Game implements IObservee{
    INSTANCE;
    
    Scene scene;
    private float x;
    private float y;
    private boolean isRunning = false;
    
    public void initialize() {
        Debugging.INSTANCE.showMessage("Initializing the game...");
        
        scene = new Scene("Green Valley", 1000, 1000);
        
        x = scene.width/2;
        y = scene.height/2;   
        
        Entity e;
        //Sprite s;
        
        int num = 20;
        for(int _x = 0; _x <= num*40; _x++){
        for(int _y = 0; _y <= num*0.7f; _y++) {
            
            //s = new Sprite("OperationHotHammer/Assets/smile.png");
            e = new SimpleCreature((scene.width/(num*2))*_x-scene.width/2,(scene.height/((int)num*0.7f))*_y);
            //e.attach(s);
            scene.addEntity(e);
            
        }}
        
        isRunning = true;
    }
    
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
    
    @Override
    public void update(float delta) {
        if(isRunning)
            scene.update(delta);
    }
    
    @Override
    public void draw(float x, float y, float radius) {
        if(isRunning)
            scene.draw(x, y, radius);
    }
    
    public boolean isRunning() {
        return isRunning;
    }
    
    public void shutdown() {
        isRunning = false;
        Debugging.INSTANCE.showMessage("Shutting down game...");
    }
    
}
