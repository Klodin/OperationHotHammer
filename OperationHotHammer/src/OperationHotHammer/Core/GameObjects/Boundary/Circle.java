package OperationHotHammer.Core.GameObjects.Boundary;

import OperationHotHammer.Core.Util.Vector;


public class Circle implements BoundaryShape {
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
       return BoundaryShape.CIRCLE;
   }
}
