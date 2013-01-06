
package OperationHotHammer.Core;

import OperationHotHammer.Core.GameObjects.Entity;
import OperationHotHammer.Core.Util.Debugging;
import OperationHotHammer.Core.Util.DepthSortedList;
import OperationHotHammer.Core.Util.EntityArrayList;
import OperationHotHammer.Core.Util.EntityList;
import OperationHotHammer.Core.Util.Partitioning.QTree;
import OperationHotHammer.Display.GameWindow;
import OperationHotHammer.Display.IObservee;

public class Scene implements IObservee {

    private final EntityList objects = new EntityArrayList();
    private final EntityList entitiesToDisplay = new DepthSortedList();
    public final String name;
    private final QTree quadTree;
    public final float width;
    public final float height;
    private float drawRadius;
    
    public Scene(String n, float w, float h) {
     
        name = n;
        width = w;
        height = h;
        quadTree = new QTree(Math.max(w,h), 6);
        drawRadius = (float)Math.sqrt(width*width + height*height)/2.0f;
        
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
        entitiesToDisplay.clear();
        quadTree.retrieveObjects(entitiesToDisplay, GameWindow.INSTANCE.getX(), GameWindow.INSTANCE.getY(), drawRadius);
        Debugging.INSTANCE.showMessage(String.valueOf(objects.size()) + " | " + String.valueOf(entitiesToDisplay.size()));
        for(Entity e : entitiesToDisplay) {
            e.draw();
        }
    }
    
}
