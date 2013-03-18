/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OHH.Core.GameObjects.Entities;

import OHH.Core.Game;
import OHH.Core.GameObjects.Boundary.Circle;
import OHH.Core.GameObjects.Entity;
import OHH.Core.Util.Color4f;
import OHH.Core.Util.Debugging.Debugging;
import OHH.Core.Util.Settings;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Kaitlyn
 */
public class Terrain extends Entity{
    private boolean isCollidingWithPlayer;
    private static Color4f filledColour = new Color4f(2f, 1.0f, 0.4f, 0.2f);
    
    public Terrain(float x, float y, int width, int height, float radius) {
        super(new Vector3f(x,y,Settings.ENTITY_Z_GROUND), width, height, new Circle(radius), new Circle(radius));
        Debugging.INSTANCE.showMessage("Create (Entity->Terrain)");
    }
    
    @Override
    public void update(float delta) {
        super.update(delta);
        
        if(getSprite() != null){
            getSprite().update(delta, this);
        }
        
        if(isCollidingWithPlayer)
            if(!this.collidesWidth((Entity)Game.INSTANCE.getPlayer()))
                isCollidingWithPlayer = false;
    }

    @Override
    public void handleCollision(Entity target) {
        //do nothing! :D
    }
    
    @Override
    public String getType() {
        return "Terrain";
    }
    
    public void showCollision(){
        isCollidingWithPlayer = true;
    }
    
    @Override
    public void draw(int resWidth, int resHeight, Vector3f cameraPosition, boolean showWireframe){
       if(getSprite() == null) {
           Debugging.INSTANCE.showWarning("Attempted to draw an entity when no sprite has been attached!");
           return;
       }
       drawnCount++;
       Vector3f pos = new Vector3f(position.x-(cameraPosition.x-resWidth/2), position.y-(cameraPosition.y-resHeight/2), position.z);

       if(!showWireframe)
           getSprite().draw(pos);
       
       if(showWireframe && !isCollidingWithPlayer)
           getSprite().drawWireframe(pos);
       
       if(isCollidingWithPlayer)
           getSprite().drawFilled(pos, filledColour);
    }
}
