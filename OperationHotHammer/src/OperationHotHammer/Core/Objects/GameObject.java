/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Core.Objects;

import OperationHotHammer.Core.Collision.Shapes.CollisionShape;
import OperationHotHammer.Core.Collision.CollisionFactory;


public class GameObject {
   // the collider
   public final CollisionShape collider;
   
   // other GameObject specific data
   
   public GameObject(final float radius) {
      collider = CollisionFactory.INSTANCE.get(CollisionShape.CIRCLE, radius);
   }
}