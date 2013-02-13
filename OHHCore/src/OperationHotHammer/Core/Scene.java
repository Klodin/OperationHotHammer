
package OperationHotHammer.Core;

import OperationHotHammer.Core.GameObjects.Entity;
import OperationHotHammer.Core.Util.DepthSortedList;
import OperationHotHammer.Core.Util.EntityArrayList;
import OperationHotHammer.Core.Util.EntityList;
import OperationHotHammer.Core.Util.Partitioning.QTree;
import org.lwjgl.util.vector.Vector3f;

public class Scene {

    private final EntityList objects = new EntityArrayList();
    private final EntityList entitiesToDisplay = new DepthSortedList();
    private final String name;
    private final QTree quadTree;
    private final float width;
    private final float height;
    private int totalEntities = 0;
    private int drawnEntities = 0;
    
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
        entitiesToDisplay.clear();
        quadTree.retrieveObjects(entitiesToDisplay, cameraPosition.x, cameraPosition.y, drawRadius);
        
        drawnEntities = entitiesToDisplay.size();
        
        for(Entity e : entitiesToDisplay) {
            e.draw(resWidth, resHeight, cameraPosition);
        }
    }
    
}
