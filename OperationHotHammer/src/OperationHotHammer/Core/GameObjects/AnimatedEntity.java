/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package OperationHotHammer.Core.GameObjects;

import OperationHotHammer.Core.GameObjects.Boundary.IBoundaryShape;


public abstract class AnimatedEntity extends DestructibleEntity {
    
    public float strength;
    public float dexterity;
    public float vitality;
    public float intelligence;
    
    public boolean dead = false;
    
    public AnimatedEntity (float x, float y,float z, IBoundaryShape colliderShape) {
        super(x, y, z, colliderShape);
    }
    
    public void moveUp(float amount) {
        this.y -= amount;
    }
    
    public void moveDown(float amount){ 
        this.y += amount;
    }
    
    public void moveLeft(float amount){
        this.x -= amount;
    }
    
    public void moveRight(float amount){
        this.x += amount;
    }

    
    public abstract void attack(Entity e);
    
}
