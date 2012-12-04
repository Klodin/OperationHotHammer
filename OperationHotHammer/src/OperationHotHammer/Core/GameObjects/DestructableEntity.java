/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Core.GameObjects;

import OperationHotHammer.Core.GameObjects.StatusEffects.StatusEffects;
import OperationHotHammer.Core.GameObjects.Boundary.BoundaryShape;

/**
 *
 * @author Kaitlyn
 */
public abstract class DestructableEntity extends Entity {

    public float health = 100;
    public final StatusEffects statusEffects = new StatusEffects();
    
    public DestructableEntity(float x, float y, BoundaryShape colliderShape) {
        super(x, y, colliderShape);
    }
    
    @Override
    public void update(float delta) {
        statusEffects.update(delta);
    }
    
    public abstract void damage(float amount);
    
}
