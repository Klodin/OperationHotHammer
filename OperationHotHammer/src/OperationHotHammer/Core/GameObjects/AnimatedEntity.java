
package OperationHotHammer.Core.GameObjects;

import OperationHotHammer.Core.GameObjects.Boundary.IBoundaryShape;
import org.lwjgl.util.vector.Vector3f;


public abstract class AnimatedEntity extends AdvancedEntity {
    

    
    public boolean dead = false;
    
    public AnimatedEntity (Vector3f p, IBoundaryShape colliderShape) {
        super(p, colliderShape);
    }
    
    public abstract void attack(Entity e);
    
}
