package OperationHotHammer.Core.GameObjects;

import OperationHotHammer.Core.GameObjects.Boundary.IBoundaryShape;
import OperationHotHammer.Display.IDisplayable;
import OperationHotHammer.Display.ISprite;

public abstract class Entity implements IDisplayable {
   public final static int LAYER_MIDDLE = 0;
    
   public ISprite sprite = null;
   public final IBoundaryShape collider;
   
   public float weight = 999999999;
   
   public float x;
   public float y;
   public float z;
   
   public Entity(float xx, float yy, float zz, IBoundaryShape colliderShape) {
      collider = colliderShape;
      x = xx;
      y = yy;
      z = zz;
   }
   
   public abstract void update(float delta);
   
   @Override
   public void draw() {
       if(sprite != null) {
           sprite.draw(x,y,z);
       }
   }
   
   @Override
   public void attach(ISprite s) {
       sprite = s;
   }
}