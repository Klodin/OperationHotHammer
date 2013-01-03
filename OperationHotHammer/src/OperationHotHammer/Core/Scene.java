
package OperationHotHammer.Core;

import OperationHotHammer.Core.GameObjects.Entity;
import OperationHotHammer.Core.Util.Partitioning.QTree;
import OperationHotHammer.Display.GameWindow;
import OperationHotHammer.Display.IObservee;
import java.util.ArrayList;

public class Scene implements IObservee {

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
    
    public void addEntity(Entity object) {
        objects.add(object);
    }
    
    @Override
    public void update(float delta) {
        
        quadTree.clean();
        
        for(Entity o : objects) {
            o.update(delta);
            quadTree.insertObject(o);
        }   
        
    }
    
    @Override
    public void render(){
        ArrayList<Entity> entities = quadTree.retrieveObjects(GameWindow.INSTANCE.getX(), GameWindow.INSTANCE.getY(), Math.max(GameWindow.INSTANCE.getScreenWidth(), GameWindow.INSTANCE.getScreenHeight()));
        for(Entity e : entities) {
            e.draw();
        }
    }
    
}
