
package OperationHotHammer.Core.GameObjects;

import OperationHotHammer.Core.GameObjects.StatusEffects.StatusEffects;
import OperationHotHammer.Core.GameObjects.Boundary.IBoundaryShape;
import org.lwjgl.util.vector.Vector3f;


public abstract class DestructibleEntity extends Entity {

    public float health = 100;
    public final StatusEffects statusEffects = new StatusEffects();
    
    public DestructibleEntity(Vector3f p, IBoundaryShape colliderShape) {
        super(p, colliderShape);
    }
    
    @Override
    public void update(float delta) {
        statusEffects.update(delta);
    }
    
    public abstract void damage(float amount);
    
}
