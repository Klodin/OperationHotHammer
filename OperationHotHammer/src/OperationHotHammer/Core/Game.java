

package OperationHotHammer.Core;

import OperationHotHammer.Core.Util.Settings;
import OperationHotHammer.Display.IDisplayable;
import OperationHotHammer.Display.IListener;
import OperationHotHammer.Display.IObserver;

public class Game implements IListener{

    Scene scene;
    private float x;
    private float y;
    private boolean on = true;
    
    public Game() {
        super();
        
        scene = new Scene("Green Valley", Settings.GRID_SPACE_SIZE * 50, Settings.GRID_SPACE_SIZE * 50);
        
        x = scene.width/2;
        y = scene.height/2;
        
    }
    
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
    
    @Override
    public void update(float delta) {
        scene.update(delta);
    }
    
    @Override
    public void render() {
        scene.render();
    }
    
    public boolean isRunning() {
        return on;
    }
    
}
