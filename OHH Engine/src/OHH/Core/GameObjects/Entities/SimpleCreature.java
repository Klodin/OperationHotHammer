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
    public SimpleCreature(float collisionBoundaryRadius) {
        super(new Circle(collisionBoundaryRadius));
        Debugging.INSTANCE.showMessage("Create (Entity->SimpleCreature)");
    }

    
    @Override
    protected void event_update(float delta) {
        if(getSprite() != null){
            getSprite().update(delta, this);
        }
    }

    @Override
    protected void event_collision(Entity otherEntity) {
        //nothing
    }

    @Override
    public String getType() {
        return "SimpleCreature";
    }

    
    
}
