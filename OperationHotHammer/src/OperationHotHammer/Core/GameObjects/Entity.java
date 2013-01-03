package OperationHotHammer.Core.GameObjects;

import OperationHotHammer.Core.Util.Debugging;
import OperationHotHammer.Core.GameObjects.Boundary.IBoundaryShape;
import OperationHotHammer.Display.IDisplayable;
import OperationHotHammer.Display.ISprite;
import org.lwjgl.util.vector.Vector3f;

public abstract class Entity implements IDisplayable {
   public final static int LAYER_MIDDLE = 100;
   public static final float LAYER_MIN = 0;
   public static final float LAYER_MAX = 200;
   public ISprite sprite = null;
   public final IBoundaryShape collider;
   
   public final Vector3f position;
   
   
   public Entity(Vector3f p, IBoundaryShape colliderShape) {
      collider = colliderShape;
      position = p;
   }
   
   public Entity(Vector3f p) {
       collider = null;
       position = p;
   }
   
   public abstract void update(float delta);
   
   @Override
   public void draw() {
       if(sprite == null) {
           Debugging.INSTANCE.showWarning("Attempted to draw an entity when no sprite has been attached!");
           return;
       }
       
       sprite.draw(position);
   }
   
   @Override
   public void attach(ISprite s) {
       if(sprite != null) {
           Debugging.INSTANCE.showWarning("Attached a sprite to an entity which already had a sprite!");
       }
              
       sprite = s;
   }
}
