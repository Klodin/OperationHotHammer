
package OperationHotHammer.Core;

import OperationHotHammer.Core.GameObjects.Boundary.Circle;
import OperationHotHammer.Core.GameObjects.Boundary.IBoundaryShape;
import OperationHotHammer.Core.GameObjects.Entity;
import OperationHotHammer.Core.Interfaces.ISprite;
import OperationHotHammer.Core.Util.Debugging;
import OperationHotHammer.Core.Util.DepthSortedList;
import OperationHotHammer.Core.Util.EntityArrayList;
import OperationHotHammer.Core.Util.EntityList;
import OperationHotHammer.Core.Util.Partitioning.QTree;
import org.lwjgl.util.vector.Vector3f;

public class Scene {

    public static int BACKGROUND_FIXED = 1;
    public static int BACKGROUND_PAN = 2;
    
    private final EntityList objects = new EntityArrayList();
    private final EntityList entitiesToDisplay = new DepthSortedList();
    private final String name;
    private final QTree quadTree;
    private final float width;
    private final float height;
    private int totalEntities = 0;
    private int drawnEntities = 0;
    private ISprite backgroundSprite;
    private int backgroundType;
    private float backgroundParallexRatio = 1f;
    
    private final Vector3f position = new Vector3f();
    
    public Scene(String n, float w, float h) {
     
        name = n;
        width = w;
        height = h;
        quadTree = new QTree(Math.max(w,h), 6);
        position.x = width/2;
        position.y = height/2;
    }
    
    public void addEntity(Entity object) {
        objects.add(object);
    }
    
    public void attach(ISprite s) {
        if(backgroundSprite != null) {
            Debugging.INSTANCE.showWarning("Attached a sprite to an scene which already had a sprite!");
        }

        backgroundSprite = s;
        backgroundType = BACKGROUND_FIXED;
    }
    
    public void attach(ISprite s, float parallexRatio) {
        
        if(parallexRatio == 0f) { //fixed position
            attach(s);
            return;   
        }
        
        if(backgroundSprite != null) {
            Debugging.INSTANCE.showWarning("Attached a sprite to an scene which already had a sprite!");
        }

        backgroundSprite = s;
        backgroundParallexRatio = parallexRatio;
        backgroundType = BACKGROUND_PAN;
    }
    
    public int getEntityCount(){
        return totalEntities;
    }
    
    public int getDrawnEntityCount(){
        return drawnEntities;
    }
    
    public Vector3f getPosition() {
        return position;
    }
    
    public void changePositionX(float amt) {
        position.x+=amt;
    }
    
    public void changePositionY(float amt) {
        position.y+=amt;
    }
    
    public void changePositionZ(float amt) {
        position.z+=amt;
    }
    
    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
    
    public void update(float delta) {
        quadTree.clean();
        
        for(Entity o : objects) {
            o.update(delta);
            quadTree.insertObject(o);
        }
        
        totalEntities = objects.size();
    }
    
    public void draw(int resWidth, int resHeight, Vector3f cameraPosition, float drawRadius){
        if(backgroundSprite != null && backgroundType == BACKGROUND_FIXED && backgroundSprite.getWidth() != resWidth) {
            backgroundSprite.setWidth(resWidth);
            backgroundSprite.setHeight(resHeight);        
        }
        
        if(backgroundSprite != null && backgroundType == BACKGROUND_FIXED)
            backgroundSprite.draw(new Vector3f(resWidth/2, resHeight/2, 0));
        
        if(backgroundSprite != null && backgroundType == BACKGROUND_PAN && backgroundSprite.getWidth() != Math.max(this.getWidth()*(2+(backgroundParallexRatio)), resWidth * (2+(backgroundParallexRatio)))) {
            backgroundSprite.setWidth((int)Math.max(this.getWidth()*(2+backgroundParallexRatio), resWidth * (2+backgroundParallexRatio)));
            backgroundSprite.setHeight((int)Math.max(this.getHeight()*(2+backgroundParallexRatio), resHeight * (2+backgroundParallexRatio)));
        }
        
        if(backgroundSprite != null && backgroundType == BACKGROUND_PAN) {
            backgroundSprite.draw(new Vector3f(
                    resWidth/2 - (cameraPosition.x-getWidth()/2)*backgroundParallexRatio,
                    resHeight/2 - (cameraPosition.y-getHeight()/2)*backgroundParallexRatio, 
                    0)
            );
        }
        
        entitiesToDisplay.clear();
        quadTree.retrieveObjects(entitiesToDisplay, cameraPosition.x, cameraPosition.y, drawRadius);
        
        drawnEntities = entitiesToDisplay.size();
        
        for(Entity e : entitiesToDisplay) {
            e.draw(resWidth, resHeight, cameraPosition);
        }
    }
    
}
