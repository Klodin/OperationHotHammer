/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
        quadTree = new QTree(Math.max(w,h), 4);
        
    }
    
    public void addObject(Entity object) {
        
        if(object.x > width) {
            object.x = width;
        } else if(object.x < 0) {
            object.x = 0;
        }
        
        if(object.y > height) {
            object.y = height;
        } else if(object.y < 0) {
            object.y = 0;
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
    
}
