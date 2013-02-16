
package OperationHotHammer.Core;

import OperationHotHammer.Core.GameObjects.Entity;
import OperationHotHammer.Core.Interfaces.IBackground;
import OperationHotHammer.Core.Interfaces.ISprite;
import OperationHotHammer.Core.Util.Debugging.Debugging;
import OperationHotHammer.Core.Util.DepthSortedList;
import OperationHotHammer.Core.Util.EntityArrayList;
import OperationHotHammer.Core.Util.EntityList;
import OperationHotHammer.Core.Util.Partitioning.QTree;
import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;

public class Scene {


    
    private final EntityList objects = new EntityArrayList();
    private final EntityList entitiesToDisplay = new DepthSortedList();
    private final String name;
    private final QTree quadTree;
    private final float width;
    private final float height;
    private ArrayList<IBackground> backgrounds = new ArrayList();
    
    private final Vector3f position = new Vector3f();
    
    public Scene(String n, float w, float h) {
     
        Debugging.INSTANCE.showMessage("Create (Scene '" + n + "')");
        
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
    
    public void attach(IBackground s) {
        backgrounds.add(s);
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

    public String getName() {
        return name;
    }
    
    public void update(float delta) {
        quadTree.clean();
        
        for(Entity o : objects) {
            o.update(delta);
            quadTree.insertObject(o);
        }
    }
    
    public void draw(int resWidth, int resHeight, Vector3f cameraPosition){
        for(IBackground background : backgrounds){
        
            ISprite backgroundSprite = background.getBackgroundSprite();
            int backgroundType = background.getBackgroundType();
            float backgroundParallexRatio = background.getBackgroundParallexRatio();
            
            if(backgroundSprite != null && backgroundType == IBackground.BACKGROUND_FIXED && backgroundSprite.getWidth() != resWidth) {
                backgroundSprite.setWidth(resWidth);
                backgroundSprite.setHeight(resHeight);        
            }

            if(backgroundSprite != null && backgroundType == IBackground.BACKGROUND_FIXED)
                backgroundSprite.draw(new Vector3f(resWidth/2, resHeight/2, 0));

            if(backgroundSprite != null && backgroundType == IBackground.BACKGROUND_PAN && backgroundSprite.getWidth() != Math.max(this.getWidth()*(2+(backgroundParallexRatio)), resWidth * (2+(backgroundParallexRatio)))) {
                backgroundSprite.setWidth((int)Math.max(this.getWidth()*(2+backgroundParallexRatio), resWidth * (2+backgroundParallexRatio)));
                backgroundSprite.setHeight((int)Math.max(this.getHeight()*(2+backgroundParallexRatio), resHeight * (2+backgroundParallexRatio)));
            }

            if(backgroundSprite != null && backgroundType == IBackground.BACKGROUND_PAN) {
                backgroundSprite.draw(new Vector3f(
                        resWidth/2 - (cameraPosition.x-getWidth()/2)*backgroundParallexRatio,
                        resHeight/2 - (cameraPosition.y-getHeight()/2)*backgroundParallexRatio, 
                        0)
                );
            }
            
        }
        
        entitiesToDisplay.clear();
        quadTree.retrieveObjects(entitiesToDisplay, cameraPosition.x, cameraPosition.y, resWidth/2, resHeight/2);
        
        for(Entity e : entitiesToDisplay) {
            e.draw(resWidth, resHeight, cameraPosition);
        }
    }
    
}
