
package OHH.Core;

import OHH.Core.GameObjects.Boundary.Circle;
import OHH.Core.GameObjects.Boundary.IBoundaryShape;
import OHH.Core.GameObjects.Entity;
import OHH.Core.Interfaces.IScenery;
import OHH.Core.Interfaces.IPosition;
import OHH.Core.Interfaces.ISprite;
import OHH.Core.Util.Debugging.Debugging;
import OHH.Core.Util.DepthSortedList;
import OHH.Core.Util.EntityArrayList;
import OHH.Core.Util.EntityList;
import OHH.Core.Util.Partitioning.QTree;
import OHH.Core.Util.Settings;
import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;

public class Scene implements IPosition {

    private final EntityList objects = new EntityArrayList();
    private final EntityList entitiesToDisplay = new DepthSortedList();
    private final String name;
    private final QTree quadTree;
    private final float width;
    private final float height;
    private ArrayList<IScenery> backgrounds = new ArrayList();
    private ArrayList<IScenery> foregrounds = new ArrayList();
    
    private Vector3f prevCameraPosition = new Vector3f();
    
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
    
    public void load(){
        Game.INSTANCE.loadScene(this);
    }
    
    public void addPlayer(Entity e, float x, float y, float z) {
        addEntity(e,x,y,z);
        Game.INSTANCE.setPlayer(e);
    }
    
    public void addEntity(Entity e, float x, float y, float z) {
        objects.add(e);
        e.setX(x);
        e.setY(y);
        e.setZ(z);
        e.setScene(this);
    }
    
    public void addBackground(IScenery s) {
        backgrounds.add(s);
    }
    
    public void addForeground(IScenery s) {
        foregrounds.add(s);
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
            quadTree.insertObject(o, o.getSprite().getBoundary());
        }
        
        for(Entity o : objects) {
            if(o.getZ() == Settings.ENTITY_Z_CREATURES && o.getBoundary().getShape() == IBoundaryShape.CIRCLE) {
                entitiesToDisplay.clear();
                quadTree.retrieveObjects(entitiesToDisplay, o.getX(), o.getY(), (int)((Circle)o.getBoundary()).radius, (int)((Circle)o.getBoundary()).radius);    
                for(Entity e : entitiesToDisplay) {
                    if(o.isCollidingWith(e)) {
                        o.handleCollision(e);
                        e.handleCollision(o);
                    }
                }
            }
        }
        
        for(IScenery background : backgrounds)
             background.getSprite().update(delta, null);
        
        for(IScenery foreground : foregrounds)
             foreground.getSprite().update(delta, null);
        
    }
    
    public void draw(int resWidth, int resHeight, Vector3f cameraPosition){
        draw(resWidth, resHeight, cameraPosition, false);
    }
    
    public void draw(int resWidth, int resHeight, Vector3f cameraPosition, boolean showWireframe){
        
        draw(backgrounds, resWidth, resHeight, cameraPosition); 
        
        entitiesToDisplay.clear();
        quadTree.retrieveObjects(entitiesToDisplay, cameraPosition.x, cameraPosition.y, resWidth/2, resHeight/2);
        
        for(Entity e : entitiesToDisplay) {
            e.draw(resWidth, resHeight, cameraPosition);
        }
        
        if(showWireframe) {
            for(Entity e : entitiesToDisplay) {
                e.draw(resWidth, resHeight, cameraPosition, true);
            } 
        }
        
        draw(foregrounds, resWidth, resHeight, cameraPosition);       
        
        prevCameraPosition.x = cameraPosition.x;
        prevCameraPosition.y = cameraPosition.y;
    }
    
    private void draw(ArrayList<IScenery> scenery, int resWidth, int resHeight, Vector3f cameraPosition){
        for(IScenery s : scenery){
        
            ISprite sprite = s.getSprite();
            
            int w = resWidth;
            int h = resHeight;
         
            if(sprite != null && sprite.getWidth() != w) {
                sprite.setWidth(w);
            }
            if(sprite != null && sprite.getHeight() != h) {
                sprite.setHeight(h);
            }

            int type = s.getType();

            if(sprite != null && type == IScenery.CENTERED_TO_SCREEN){
                sprite.draw(new Vector3f(resWidth/2, resHeight/2, 0));
            }

            if(sprite != null && type == IScenery.MOVE_WITH_CAMERA) {
                float parallex = s.getParallex();
                
                sprite.getTexture().setOffsetX(sprite.getTexture().getOffsetX() + (prevCameraPosition.x - cameraPosition.x) * parallex);
                sprite.getTexture().setOffsetY(sprite.getTexture().getOffsetY() + (prevCameraPosition.y - cameraPosition.y) * parallex);

                sprite.draw(new Vector3f(resWidth/2, resHeight/2, 0));
            }
        }   
    }
}
