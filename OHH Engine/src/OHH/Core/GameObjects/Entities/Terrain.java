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
    public String label = "";
    private boolean isCollidingWithPlayer;
    private static Color4f filledColour = new Color4f(2f, 1.0f, 0.4f, 0.2f);
    private static final int GRIDSIZE = 32;
    private static final float RADIUS = (float)Math.sqrt((float)GRIDSIZE*(float)GRIDSIZE) / 2;
    
    public Terrain(float x, float y) {
        super(new Vector3f(x*GRIDSIZE,y*GRIDSIZE,Settings.ENTITY_Z_GROUND), GRIDSIZE, GRIDSIZE, new Circle(RADIUS), new Circle(RADIUS));
        Debugging.INSTANCE.showMessage("Create (Entity->Terrain)");
    }
    
    public Terrain() {
        super(new Vector3f(0,0,Settings.ENTITY_Z_GROUND), 0, 0, new Circle(0), new Circle(0));
        Debugging.INSTANCE.showMessage("Create (Entity->Terrain)");
    }
    
    public void setXY(float x, float y){
        ((Circle) this.displayBoundary).radius = RADIUS;
        ((Circle) this.collisionBoundary).radius = RADIUS;
        this.position.x = x*GRIDSIZE;
        this.position.y = y*GRIDSIZE;
        this.width = GRIDSIZE;
        this.height = GRIDSIZE;
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
    
    public String showCollision(){
        isCollidingWithPlayer = true;
        return label;
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
       
       if(!showWireframe && isCollidingWithPlayer)
           getSprite().drawFilled(pos, filledColour);
    }
}
