package OHH.Core.GameObjects;

import OHH.Core.GameObjects.Boundary.Circle;
import OHH.Core.Util.Debugging.Debugging;
import OHH.Core.GameObjects.Boundary.IBoundaryShape;
import OHH.Core.Interfaces.IDisplayable;
import OHH.Core.Interfaces.IEntity;
import OHH.Core.Interfaces.IPosition;
import OHH.Core.Interfaces.ISprite;
import OHH.Core.Util.Partitioning.QTree;
import org.lwjgl.util.vector.Vector3f;

public abstract class Entity implements IDisplayable, IEntity, IPosition {

   public final static int LAYER_MIDDLE = 100;
   public static final float LAYER_MIN = 0;
   public static final float LAYER_MAX = 200;
   private ISprite sprite = null;
   public final IBoundaryShape displayBoundary;
   public final IBoundaryShape collisionBoundary;
   
   protected int width;
   protected int height;
   
   public final Vector3f position;
   protected static int drawnCount = 0;
   protected static int updateCount = 0;
   
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
   
   public Entity(Vector3f position, int width, int height, IBoundaryShape displayBoundary, IBoundaryShape collisionBoundary) {       
      this.displayBoundary = displayBoundary;
      this.collisionBoundary = collisionBoundary;
      this.position = position;
      this.width = width;
      this.height = height;
   }
   
   public void update(float delta){
       updateCount++;
   }
   
   public ISprite getSprite(){
       return sprite;
   }
   
   @Override
   public void draw(int resWidth, int resHeight, Vector3f cameraPosition) {
       draw(resWidth, resHeight, cameraPosition, false);  
   }
   
   @Override
   public void draw(int resWidth, int resHeight, Vector3f cameraPosition, boolean showWireframe){
       if(sprite == null) {
           Debugging.INSTANCE.showWarning("Attempted to draw an entity when no sprite has been attached!");
           return;
       }
       drawnCount++;
       Vector3f pos = new Vector3f(position.x-(cameraPosition.x-resWidth/2), position.y-(cameraPosition.y-resHeight/2), position.z);

       if(!showWireframe)
           sprite.draw(pos);
       else
           sprite.drawWireframe(pos);    
   }
   
   @Override
   public void attach(ISprite s) {
       if(sprite != null) {
           Debugging.INSTANCE.showWarning("Attached a sprite to an entity which already had a sprite!");
       }
              
       sprite = s;
       //if(this.display.getShape() == IBoundaryShape.CIRCLE) {
       sprite.setWidth(width);
       sprite.setHeight(height);   
       //}
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
   
    abstract public void handleCollision(Entity target);
    
    public boolean collidesWidth(Entity target){
        
        boolean ret = false;
        
        if(collisionBoundary.getShape() == IBoundaryShape.CIRCLE && target.collisionBoundary.getShape() == IBoundaryShape.CIRCLE) {
            float dif = (float)Math.sqrt((target.getX() - getX()) * (target.getX() - getX()) + (target.getY() - getY()) * (target.getY() - getY()));
            ret = dif < ((Circle)collisionBoundary).radius + ((Circle)target.collisionBoundary).radius;
        }
        
        return ret;
    }
    
    abstract public String getType();
    
}
