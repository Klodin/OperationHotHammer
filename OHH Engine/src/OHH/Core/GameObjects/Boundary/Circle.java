package OHH.Core.GameObjects.Boundary;

import org.lwjgl.util.vector.Vector3f;


public class Circle implements IBoundaryShape {
   public float radius;
   
   public Circle(final float radius) {
      this.radius = radius;
   }
   
   @Override
   public int getShape() {
       return IBoundaryShape.CIRCLE;
   }
}
