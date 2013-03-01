
package OperationHotHammer.Core;

import OperationHotHammer.Core.Interfaces.IEntity;
import OperationHotHammer.Core.Util.Debugging.Debugging;
import org.lwjgl.util.vector.Vector3f;

public enum Game{
    INSTANCE;
    
    Scene scene;

    private boolean isRunning = false;
    
    int resWidth = 0;
    int resHeight = 0;
    float drawRadius = 0;
    
    IEntity player;
    
    public void initialize() {
        Debugging.INSTANCE.showMessage("Initializing (Game)");

        isRunning = true;
    }
    
    public void loadScene(Scene s) {
        Debugging.INSTANCE.showMessage("Loading Scene (Scene '" + s.getName() + "')");
        scene = s;
    }
    
    public Scene getScene() {
        return scene;
    }
    
    public void setPlayer(IEntity e){
        player = e;
    }
    
    public Vector3f getPosition(){
        return scene.getPosition();
    }
    
    public void changePositionX(float amt) {
        if(player != null) {
            player.setX(player.getX()+amt);
            scene.setX(player.getX()); 
        } else {
            scene.changeX(amt);
        }
    }
    
    public void changePositionY(float amt) {
        if(player != null) {
            player.setY(player.getY()+amt);
            scene.setY(player.getY()); 
        } else {
            scene.changeY(amt);
        }
    }
    
    public void changePositionZ(float amt) {
        scene.changeZ(amt);
    }
    
    public void update(float delta) {
        if(isRunning)
            scene.update(delta);
    }
    
    public void draw(int resolutionWidth, int resolutionHeight) {
        if(resHeight != resolutionHeight || resWidth != resolutionWidth){
            resWidth = resolutionWidth;
            resHeight = resolutionHeight;
            //drawRadius = (float)Math.sqrt(resWidth*resWidth + resHeight*resHeight)/2.0f;
        }
        if(isRunning)
            scene.draw(resWidth, resHeight, scene.getPosition());
    }
    
    public boolean isRunning() {
        return isRunning;
    }
    
    public void shutdown() {
        isRunning = false;
        Debugging.INSTANCE.showMessage("Shutting Down");
    }
    
}
