
package OperationHotHammer.Core;

import OperationHotHammer.Core.GameObjects.Entities.SimpleCreature;
import OperationHotHammer.Core.GameObjects.Entity;
import OperationHotHammer.Core.Util.Debugging;
import OperationHotHammer.Core.Util.Settings;
import OperationHotHammer.Display.GameWindow;
import OperationHotHammer.Display.IObservee;
import OperationHotHammer.Display.Sprite;

public enum Game implements IObservee{
    INSTANCE;
    
    Scene scene;
    private float x;
    private float y;
    private boolean isRunning = false;
    
    public void initialize() {
        Debugging.INSTANCE.showMessage("Initializing the game...");
        
        scene = new Scene("Green Valley", GameWindow.INSTANCE.getScreenWidth(), GameWindow.INSTANCE.getScreenHeight());
        
        x = scene.width/2;
        y = scene.height/2;   
        
        Entity e = new SimpleCreature(x,y);
        Sprite s = new Sprite("OperationHotHammer/Assets/Terrain/grass.png");
        s.setWidth(200);
        s.setHeight(200);
        e.attach(s);
        scene.addEntity(e);
        
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
    
    public void shutdown() {
        isRunning = false;
        Debugging.INSTANCE.showMessage("Shutting down game...");
    }
    
}
