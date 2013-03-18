/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OHH.Core.GameObjects.Entities;

import OHH.Core.GameObjects.Boundary.Circle;
import OHH.Core.GameObjects.Entity;
import OHH.Core.Util.Debugging.Debugging;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Kaitlyn
 */
public class SimpleCreature extends Entity{
    public SimpleCreature(float x, float y, float z, int width, int height, float displayBoundaryRadius, float collisionBoundaryRadius) {
        super(new Vector3f(x,y,z), width, height, new Circle(displayBoundaryRadius), new Circle(collisionBoundaryRadius));
        Debugging.INSTANCE.showMessage("Create (Entity->SimpleCreature)");
    }

    
    @Override
    public void update(float delta) {
        super.update(delta);
        if(getSprite() != null){
            getSprite().update(delta, this);
        }
    }
    
    
    @Override
    public void handleCollision(Entity target) {
        //no nothing :D
        if(target.getType().equals("Terrain"))
            handleCollision((Terrain)target);
    }
    
    public void handleCollision(Terrain target) {
        
        target.showCollision();
        
    }
    
    @Override
    public String getType() {
        return "SimpleCreature";
    }
    
}
