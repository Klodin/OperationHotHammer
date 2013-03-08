package OHH.Core.GameObjects;

import OHH.Core.GameObjects.Boundary.Circle;
import OHH.Core.Util.Debugging.Debugging;
import OHH.Core.GameObjects.Boundary.IBoundaryShape;
import OHH.Core.Interfaces.IDisplayable;
import OHH.Core.Interfaces.IEntity;
import OHH.Core.Interfaces.IPosition;
import OHH.Core.Interfaces.ISprite;
import org.lwjgl.util.vector.Vector3f;

public abstract class Entity implements IDisplayable, IEntity, IPosition {

   public final static int LAYER_MIDDLE = 100;
   public static final float LAYER_MIN = 0;
   public static final float LAYER_MAX = 200;
   public ISprite sprite = null;
   public final IBoundaryShape collider;
   
   public final Vector3f position;
   private static int drawnCount = 0;
   private static int updateCount = 0;
   
   public static int getDrawnCount(){
       return drawnCount;
   }
    
   public static int getUpdateCount(){
       return updateCount;
   }
   
   public static void clearCounts(){
       drawnCount = 0;
       updateCount = 0;
   }
   
   public Entity(Vector3f p, IBoundaryShape colliderShape) {       
      collider = colliderShape;
      position = p;
   }
   
   public Entity(Vector3f p) {
       collider = null;
       position = p;
   }
   
   public void update(float delta){
       updateCount++;
   }
   
   @Override
   public void draw(int resWidth, int resHeight, Vector3f cameraPosition){
       if(sprite == null) {
           Debugging.INSTANCE.showWarning("Attempted to draw an entity when no sprite has been attached!");
           return;
       }
       drawnCount++;
       Vector3f pos = new Vector3f(position.x-(cameraPosition.x-resWidth/2), position.y-(cameraPosition.y-resHeight/2), position.z);
       sprite.draw(pos);
   }
   
   @Override
   public void attach(ISprite s) {
       if(sprite != null) {
           Debugging.INSTANCE.showWarning("Attached a sprite to an entity which already had a sprite!");
       }
              
       sprite = s;
       if(this.collider.getShape() == IBoundaryShape.CIRCLE) {
           sprite.setWidth((int)((Circle)this.collider).radius*2);
           sprite.setHeight((int)((Circle)this.collider).radius*2);   
       }
   }
   
   @Override
   public String getState(){
       return ""; //TODO: actual states
   }

    @Override
    public float getX() {
        return position.x;
    }

    @Override
    public float getY() {
        return position.y;
    }
    
    @Override
    public float getZ() {
        return position.z;
    }

    @Override
    public Vector3f getPosition() {
        return position;
    }
    
    @Override
    public void setX(float x) {
        position.x = x;
    }

    @Override
    public void setY(float y) {
        position.y = y;
    }
    
    @Override
    public void setZ(float z) {
        position.z = z;
    }
   
   
}
