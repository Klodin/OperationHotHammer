/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package OperationHotHammer.Core;

import java.util.ArrayList;
import OperationHotHammer.Core.GameObjects.GameObject;
import OperationHotHammer.Core.Util.Partitioning.QTree;

public class Scene {

    public final ArrayList<GameObject> objects = new ArrayList<>();
    public final String name;
    public final QTree quadTree;
    public final int width;
    public final int height;
    
    public Scene(String n, int w, int h) {
     
        name = n;
        width = w;
        height = h;
        quadTree = new QTree(Math.max(w,h), 4);
        
    }
    
    public void addObject(GameObject object) {
        
        objects.add(object);
        
    }
    
    public void update(float delta) {
        
        quadTree.clean();
        
        for(GameObject o : objects) {
            o.update(delta);
            quadTree.insertObject(o);
        }   
        
    }
    
}
