
package OHH.Core.GameObjects;

import OHH.Core.GameObjects.Boundary.IBoundaryShape;
import OHH.Core.GameObjects.StatusEffects.StatusEffects;
import org.lwjgl.util.vector.Vector3f;


public abstract class AdvancedEntity extends Entity {

    private  float health;
    private float maxHealth;
    private boolean destroyed;
    protected boolean destructable;
    
    public float weight = 999999999;
    
    public final StatusEffects statusEffects = new StatusEffects();
    
    public AdvancedEntity(Vector3f position, int width, int height, IBoundaryShape displayBoundary, IBoundaryShape collisionBoundary) {
        super(position, width, height, displayBoundary, collisionBoundary);
        
        health = maxHealth = 100;
        destroyed = false;
    }
    
    @Override
    public void update(float delta) {
        statusEffects.update(delta);
    }
    
    //implementation of iPhysics
    public void setHeading(Vector3f vec) {
        //stuff
    }
    
    public void setHeading(Vector3f vec, float duration){
        //stuff
    }
    
    public void addDamage(float amount, int element) { 
        health -= amount; 
        
        if(health <= 0)
            destroy();
    }
     
    public boolean isDestroyed() { 
        return destroyed; 
    }
    
    public void destroy() { 
        destroyed = true;
    }
    
    public void undestroy() { 
        destroyed = false; 
    }
    
    public float getHealth() { 
        return health; 
    }

    public float getMaxHealth() { 
        return maxHealth; 
    }
    
    public void setHealth(float hp) { 
        health = hp; 
    }

    public void setMaxHealth(float hp) {
        maxHealth = hp;
    }

    public void modifyHealth(float hp) {
        if(health+hp < 0){
            health = 0;
            destroy();
            
        }else if(health+hp > maxHealth){ 
            health = maxHealth;
        }else{
            health+=hp;
        }
    }

    public boolean isDestructable() {
        return destructable;
    }
    
}
