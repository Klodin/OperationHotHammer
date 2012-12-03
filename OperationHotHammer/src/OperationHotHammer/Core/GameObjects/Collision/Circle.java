/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Core.GameObjects.Collision;

import OperationHotHammer.Core.Util.Vector;


public class Circle implements CollisionShape {
   public float radius;
   public final Vector center;
   
   public Circle(final float radius) {
      this.radius = radius;
      this.center = new Vector();
   }
   
   public void update(final Vector position) {
      center.set(position);
   }
   
   @Override
   public int getShape() {
       return CollisionShape.CIRCLE;
   }
}
