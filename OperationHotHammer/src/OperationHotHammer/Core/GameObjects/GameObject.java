/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Core.GameObjects;

import OperationHotHammer.Core.GameObjects.Collision.CollisionShape;
import OperationHotHammer.Core.GameObjects.Collision.CollisionFactory;


public class GameObject {
   // the collider
   public final CollisionShape collider;
   
   // other GameObject specific data
   
   public GameObject(final float radius) {
      collider = CollisionFactory.INSTANCE.get(CollisionShape.CIRCLE, radius);
   }
}