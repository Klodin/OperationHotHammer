
package OperationHotHammer.Core;

import OperationHotHammer.Core.Util.Debugging;
import OperationHotHammer.Core.Util.Settings;
import OperationHotHammer.Display.IDisplayable;
import OperationHotHammer.Display.IObservee;
import OperationHotHammer.Display.IObserver;

public enum Game implements IObservee{
    INSTANCE;
    
    Scene scene;
    private float x;
    private float y;
    private boolean isRunning = false;
    
    public void initialize() {
        Debugging.INSTANCE.showMessage("Initializing the game...");
        
        scene = new Scene("Green Valley", Settings.GRID_SPACE_SIZE * 50, Settings.GRID_SPACE_SIZE * 50);
        
        x = scene.width/2;
        y = scene.height/2;   
        
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
    public void render() {
        if(isRunning)
            scene.render();
    }
    
    public boolean isRunning() {
        return isRunning;
    }
    
}
