
package OperationHotHammer.Core;

import OperationHotHammer.Core.Util.Debugging;
import org.lwjgl.util.vector.Vector3f;

public enum Game{
    INSTANCE;
    
    Scene scene;

    private boolean isRunning = false;
    
    int resWidth = 0;
    int resHeight = 0;
    float drawRadius = 0;
    
    public void initialize() {
        Debugging.INSTANCE.showMessage("Initializing the game...");

        isRunning = true;
    }
    
    public void loadScene(Scene s) {
        scene = s;
    }
    
    public Scene getScene() {
        return scene;
    }
    
    public Vector3f getPosition(){
        return scene.getPosition();
    }
    
    public void changePositionX(float amt) {
        scene.changePositionX(amt);
    }
    
    public void changePositionY(float amt) {
        scene.changePositionY(amt);
    }
    
    public void changePositionZ(float amt) {
        scene.changePositionZ(amt);
    }
    
    public void update(float delta) {
        if(isRunning)
            scene.update(delta);
    }
    
    public void draw(int resolutionWidth, int resolutionHeight) {
        if(resHeight != resolutionHeight || resWidth != resolutionWidth){
            resWidth = resolutionWidth;
            resHeight = resolutionHeight;
            drawRadius = (float)Math.sqrt(resWidth*resWidth + resHeight*resHeight)/2.0f;
        }
        if(isRunning)
            scene.draw(resWidth, resHeight, scene.getPosition(), drawRadius);
    }
    
    public boolean isRunning() {
        return isRunning;
    }
    
    public void shutdown() {
        isRunning = false;
        Debugging.INSTANCE.showMessage("Shutting down game...");
    }
    
}
