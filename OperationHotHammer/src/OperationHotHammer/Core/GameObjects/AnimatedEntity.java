
package OperationHotHammer.Core.GameObjects;

import OperationHotHammer.Core.GameObjects.Boundary.IBoundaryShape;
import org.lwjgl.util.vector.Vector3f;


public abstract class AnimatedEntity extends DestructibleEntity {
    
    public float strength;
    public float dexterity;
    public float vitality;
    public float intelligence;
    
    public boolean dead = false;
    
    public AnimatedEntity (Vector3f p, IBoundaryShape colliderShape) {
        super(p, colliderShape);
    }
    
    public void moveUp(float amount) {
        position.y -= amount;
    }
    
    public void moveDown(float amount){ 
        position.y += amount;
    }
    
    public void moveLeft(float amount){
        position.x -= amount;
    }
    
    public void moveRight(float amount){
        position.x += amount;
    }

    
    public abstract void attack(Entity e);
    
}
