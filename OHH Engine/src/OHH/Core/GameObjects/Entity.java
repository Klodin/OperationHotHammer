package OHH.Core.GameObjects;

import OHH.Core.GameObjects.Boundary.Circle;
import OHH.Core.Util.Debugging.Debugging;
import OHH.Core.GameObjects.Boundary.IBoundaryShape;
import OHH.Core.Interfaces.IEntity;
import OHH.Core.Interfaces.IPosition;
import OHH.Core.Interfaces.ISprite;
import OHH.Core.Scene;
import org.lwjgl.util.vector.Vector3f;

public abstract class Entity implements IEntity, IPosition {
    
    private ISprite sprite = null;
    private IBoundaryShape collisionBoundary = null;
    private boolean isCollidable = false;
    private HorizontalState hState = HorizontalState.Still;
    private VerticalState vState = VerticalState.Still;
    private Scene currentScene = null;
    private Vector3f position = new Vector3f();
   
    public Entity() { 
        //empty constructor
    }
   
    public Entity(IBoundaryShape collisionBoundary) { 
        addCollisionBoundary(collisionBoundary);
    }
   
    public final void addCollisionBoundary(IBoundaryShape collisionBoundary) {
        this.isCollidable = true;
        this.collisionBoundary = collisionBoundary;
    }
   
    abstract protected void event_update(float delta);
    public final void update(float delta){
        event_update(delta);
    }
   
    public final ISprite getSprite(){
        return sprite;
    }
    
    public final Scene getScene(){
        return currentScene;
    }
   
    public final void setScene(Scene s) {
        currentScene = s;
    }
    
    public void draw(int resWidth, int resHeight, Vector3f cameraPosition) {
        draw(resWidth, resHeight, cameraPosition, false);  
    }
   
    
    public void draw(int resWidth, int resHeight, Vector3f cameraPosition, boolean showWireframe){
        if(sprite == null) {
            Debugging.INSTANCE.showWarning("Attempted to draw an entity when no sprite has been attached!");
            return; //exit
        }
       
        //adjust the position in relation to the camera
        Vector3f pos = new Vector3f(getPosition());
        pos.x -= cameraPosition.x - resWidth / 2;
        pos.y -= cameraPosition.y - resHeight / 2;

        if(!showWireframe) {
            sprite.draw(pos);
        } else {
            sprite.drawWireframe(pos);    
        }
    }
   
    
    public final void attach(ISprite s) {
        if(sprite != null) {
            Debugging.INSTANCE.showWarning("Attached a sprite to an entity which already had a sprite!");
        }
              
        sprite = s;
    }
   
    public final HorizontalState getHState(){
        return hState;
    }
    
    public final VerticalState getVState(){
        return vState;
    }
    
    public final IBoundaryShape getBoundary(){
        return collisionBoundary;
    }

    //////////////////////////////
    //Implementing IPosition
    
    @Override
    public final float getX() {
        return getPosition().x;
    }

    @Override
    public final float getY() {
        return getPosition().y;
    }
    
    @Override
    public final float getZ() {
        return getPosition().z;
    }

    @Override
    public final Vector3f getPosition() {
        return position;
    }
    
    @Override
    public final void setX(float x) {
        getPosition().x = x;
    }

    @Override
    public final void setY(float y) {
        getPosition().y = y;
    }
    
    @Override
    public final void setZ(float z) {
        getPosition().z = z;
    }
    //////////////////////////////
    
   
    abstract protected void event_collision(Entity otherEntity);
    
    public final void handleCollision(Entity otherEntity){
        event_collision(otherEntity);
    }
    
    public final boolean isCollidingWith(Entity target){
        
        if(!isCollidable) {
            return false;
        }
        
        boolean ret = false;
        
        if(collisionBoundary.getShape() == IBoundaryShape.CIRCLE && target.collisionBoundary.getShape() == IBoundaryShape.CIRCLE) {
            float dif = (float)Math.sqrt((target.getX() - getX()) * (target.getX() - getX()) + (target.getY() - getY()) * (target.getY() - getY()));
            ret = dif < ((Circle)collisionBoundary).radius + ((Circle)target.collisionBoundary).radius;
        }
        
        return ret;
    }
    
    abstract public String getType();
    
}
