/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Core.GameObjects;

import OperationHotHammer.Core.GameObjects.StatusEffects.StatusEffects;
import OperationHotHammer.Core.GameObjects.Boundary.IBoundaryShape;

/**
 *
 * @author Kaitlyn
 */
public abstract class DestructibleEntity extends Entity {

    public float health = 100;
    public final StatusEffects statusEffects = new StatusEffects();
    
    public DestructibleEntity(float x, float y, float z, IBoundaryShape colliderShape) {
        super(x, y, z, colliderShape);
    }
    
    @Override
    public void update(float delta) {
        statusEffects.update(delta);
    }
    
    public abstract void damage(float amount);
    
}
