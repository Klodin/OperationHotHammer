/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Core.Collision;

import OperationHotHammer.Core.Collision.Shapes.CollisionShape;
import OperationHotHammer.Core.Collision.Shapes.Circle;


public enum CollisionFactory {
    INSTANCE;
    
    public CollisionShape get(final int shape, final float ... values){
        CollisionShape collisionShape;
        
        switch(shape){
            case CollisionShape.CIRCLE:
            default:
               collisionShape = new Circle(values[0]);   
               break;
        }
        
        return collisionShape;        
    }
}
