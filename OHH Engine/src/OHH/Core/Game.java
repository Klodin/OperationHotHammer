
package OHH.Core;

import OHH.Core.Interfaces.IPosition;
import OHH.Core.Util.Debugging.Debugging;
import org.lwjgl.util.vector.Vector3f;

public enum Game{
    INSTANCE;
    
    Scene scene;

    private boolean isRunning = false;
    
    int resWidth = 0;
    int resHeight = 0;
    float drawRadius = 0;
    
    private IPosition player;
    
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
    
    public void setPlayer(IPosition e){
        player = e;
    }
    
    public IPosition getPlayer(){
        return player;
    }
    
    public Vector3f getPosition(){
        return scene.getPosition();
    }
    
    /*
    
    public void changePositionX(float amt) {
        if(player != null) {
            player.setX(player.getX()+amt);
        } else if(scene != null) {
            scene.changeX(amt);
        }
    }
    
    public void changePositionY(float amt) {
        if(player != null) {
            player.setY(player.getY()+amt);
        } else if(scene != null) {
            scene.changeY(amt);
        }
    }
    
    public void changePositionZ(float amt) {
        scene.changeZ(amt);
    }
    * */
    
    
    public void changePosition(Vector3f amt) {
        IPosition target = player;
        
        if(target == null) {
            target = scene;
        }
 
        target.setX(target.getX()+amt.x);
        target.setY(target.getY()+amt.y);
        target.setZ(target.getZ()+amt.z);
    }
    
    public void update(float delta) {
        if(isRunning && scene != null)
            scene.update(delta);
        
        if(scene == null)
            Debugging.INSTANCE.showWarning("Attempted to update NULL scene.");
    }
    
    public void draw(int resolutionWidth, int resolutionHeight) {
        draw(resolutionWidth, resolutionHeight, false);
    }
    
    public void draw(int resolutionWidth, int resolutionHeight, boolean showWireframe) {
        if(resHeight != resolutionHeight || resWidth != resolutionWidth){
            resWidth = resolutionWidth;
            resHeight = resolutionHeight;
            //drawRadius = (float)Math.sqrt(resWidth*resWidth + resHeight*resHeight)/2.0f;
        }
        if(isRunning && scene != null)
            scene.draw(resWidth, resHeight, scene.getPosition(), showWireframe);
        
        if(scene == null)
            Debugging.INSTANCE.showWarning("Attempted to draw NULL scene.");
    }
    
    public boolean isRunning() {
        return isRunning;
    }
    
    public void shutdown() {
        isRunning = false;
        Debugging.INSTANCE.showMessage("Shutting Down");
    }
    
}
