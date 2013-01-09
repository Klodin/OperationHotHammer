package OperationHotHammer.Core.GameObjects.Boundary;

import org.lwjgl.util.vector.Vector3f;


public class Circle implements IBoundaryShape {
   public float radius;
   public final Vector3f center;
   
   public Circle(final float radius) {
      this.radius = radius;
      this.center = new Vector3f();
   }
   
   public void update(final Vector3f position) {
      center.set(position);
   }
   
   @Override
   public int getShape() {
       return IBoundaryShape.CIRCLE;
   }
}
