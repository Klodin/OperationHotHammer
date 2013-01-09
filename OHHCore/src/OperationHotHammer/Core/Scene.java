
package OperationHotHammer.Core;

import OperationHotHammer.Core.GameObjects.Entity;
import OperationHotHammer.Core.Util.Debugging;
import OperationHotHammer.Core.Util.DepthSortedList;
import OperationHotHammer.Core.Util.EntityArrayList;
import OperationHotHammer.Core.Util.EntityList;
import OperationHotHammer.Core.Util.Partitioning.QTree;
import OperationHotHammer.Core.Interfaces.IObservee;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class Scene {

    private final EntityList objects = new EntityArrayList();
    private final EntityList entitiesToDisplay = new DepthSortedList();
    public final String name;
    private final QTree quadTree;
    public final float width;
    public final float height;
    
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
    
    public void update(float delta) {
        
        quadTree.clean();
        
        for(Entity o : objects) {
            o.update(delta);
            quadTree.insertObject(o);
        }   
        
    }
    
    public void draw(int resWidth, int resHeight, Vector3f cameraPosition, float drawRadius){
        entitiesToDisplay.clear();
        quadTree.retrieveObjects(entitiesToDisplay, cameraPosition.x, cameraPosition.y, drawRadius);
        Debugging.INSTANCE.showMessage(String.valueOf(objects.size()) + " | " + String.valueOf(entitiesToDisplay.size()));
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        for(Entity e : entitiesToDisplay) {
            e.draw(resWidth, resHeight, cameraPosition);
        }
        
        GL11.glDisable(GL11.GL_BLEND);
    }
    
}
