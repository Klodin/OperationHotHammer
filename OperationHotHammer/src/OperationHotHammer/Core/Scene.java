
package OperationHotHammer.Core;

import OperationHotHammer.Core.GameObjects.Entity;
import OperationHotHammer.Core.Util.Partitioning.QTree;
import java.util.ArrayList;

public class Scene {

    public final ArrayList<Entity> objects = new ArrayList<>();
    public final String name;
    public final QTree quadTree;
    public final float width;
    public final float height;
    
    public Scene(String n, float w, float h) {
     
        name = n;
        width = w;
        height = h;
        quadTree = new QTree(Math.max(w,h), 6);
        
    }
    
    public void addEntiity(Entity object) {
        
        if(object.position.x > width) {
            object.position.x = width;
        } else if(object.position.x < 0) {
            object.position.x = 0;
        }
        
        if(object.position.y > height) {
            object.position.y = height;
        } else if(object.position.y < 0) {
            object.position.y = 0;
        }
        
        objects.add(object);
        
    }
    
    public void update(float delta) {
        
        quadTree.clean();
        
        for(Entity o : objects) {
            o.update(delta);
            quadTree.insertObject(o);
        }   
        
    }
    
    public void draw(){
        
    }
    
}
