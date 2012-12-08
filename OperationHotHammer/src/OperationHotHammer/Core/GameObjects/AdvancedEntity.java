
package OperationHotHammer.Core.GameObjects;

import OperationHotHammer.Core.GameObjects.StatusEffects.StatusEffects;
import OperationHotHammer.Core.GameObjects.Boundary.IBoundaryShape;
import org.lwjgl.util.vector.Vector3f;


public abstract class AdvancedEntity extends Entity implements iDestructible, iPhysics {

    public float health = 100;
    public float maxHealth = 100;
    private boolean destroyed = false;
    
    public float weight = 999999999;
    
    public final StatusEffects statusEffects = new StatusEffects();
    
    public AdvancedEntity(Vector3f p, IBoundaryShape colliderShape) {
        super(p, colliderShape);
    }
    
    @Override
    public void update(float delta) {
        statusEffects.update(delta);
    }
    
    @Override
    public void addDamage(float amount, int element) {
        health-=amount;
    }
    
    @Override
    public void setHeading(Vector3f vec) {
        //stuff
    }
    
    @Override
    public void setHeading(Vector3f vec, float duration){
        //stuff
    }
    
    //implementation of iDestructible
    @Override 
    public boolean isDestroyed()          { return destroyed; }
    @Override 
    public void    destroy()              { destroyed = true; }
    @Override 
    public void    undestroy()            { destroyed = false; }
    @Override 
    public float   getHealth()            { return health; }
    @Override 
    public float   getMaxHealth()         { return maxHealth; }
    @Override 
    public void    setHealth(float hp)    { health = hp; }
    @Override 
    public void    modifyHealth(float hp) {
        if(health+hp < 0){
            health = 0;
        }else if(health+hp > maxHealth){ 
            health = maxHealth;
        }else{
            health+=hp;
        }
    }
    
}
