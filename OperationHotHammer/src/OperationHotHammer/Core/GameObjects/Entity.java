package OperationHotHammer.Core.GameObjects;

import OperationHotHammer.Core.GameObjects.Boundary.IBoundaryShape;
import OperationHotHammer.Display.IDisplayable;
import OperationHotHammer.Display.ISprite;
import org.lwjgl.util.vector.Vector3f;

public abstract class Entity implements IDisplayable {
   public final static int LAYER_MIDDLE = 0;
    
   public ISprite sprite = null;
   public final IBoundaryShape collider;
   
   public float weight = 999999999;
   
   public final Vector3f position;
   
   public Entity(Vector3f p, IBoundaryShape colliderShape) {
      collider = colliderShape;
      position = p;
   }
   
   public abstract void update(float delta);
   
   @Override
   public void draw() {
       if(sprite != null) {
           sprite.draw(position);
       }
   }
   
   @Override
   public void attach(ISprite s) {
       sprite = s;
   }
}