
package OperationHotHammer.Core;

import OperationHotHammer.Core.GameObjects.Entity;
import OperationHotHammer.Core.Interfaces.IScenery;
import OperationHotHammer.Core.Interfaces.IPosition;
import OperationHotHammer.Core.Interfaces.ISprite;
import OperationHotHammer.Core.Util.Debugging.Debugging;
import OperationHotHammer.Core.Util.DepthSortedList;
import OperationHotHammer.Core.Util.EntityArrayList;
import OperationHotHammer.Core.Util.EntityList;
import OperationHotHammer.Core.Util.Partitioning.QTree;
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
    
    public void addPlayer(Entity e) {
        objects.add(e);
        Game.INSTANCE.setPlayer(e);
    }
    
    public void addEntity(Entity e) {
        objects.add(e);
    }
    
    public void addBackground(IScenery s) {
        backgrounds.add(s);
    }
    
    public void addForeground(IScenery s) {
        foregrounds.add(s);
    }
    
    public Vector3f getPosition() {
        return position;
    }
    
    public void setX(float x) {
        position.x = x;
    }

    public void setY(float y) {
        position.y = y;
    }
    
    public void setZ(float z) {
        position.z = z;
    }
    
    public float getZ() {
        return position.z;
    }
    
    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }
    
    public void changeX(float amt) {
        position.x+=amt;
    }
    
    public void changeY(float amt) {
        position.y+=amt;
    }
    
    public void changeZ(float amt) {
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
        
        for(IScenery background : backgrounds)
             background.getSprite().update(delta, null);
        
        for(IScenery foreground : foregrounds)
             foreground.getSprite().update(delta, null);
        
    }
    
    public void draw(int resWidth, int resHeight, Vector3f cameraPosition){
        drawBackgrounds(resWidth, resHeight, cameraPosition);
        
        entitiesToDisplay.clear();
        quadTree.retrieveObjects(entitiesToDisplay, cameraPosition.x, cameraPosition.y, resWidth/2, resHeight/2);
        
        for(Entity e : entitiesToDisplay) {
            e.draw(resWidth, resHeight, cameraPosition);
        }
        
        drawForegrounds(resWidth, resHeight, cameraPosition);
        
        
        prevCameraPosition.x = cameraPosition.x;
        prevCameraPosition.y = cameraPosition.y;
    }
    
    private void drawBackgrounds(int resWidth, int resHeight, Vector3f cameraPosition){
        for(IScenery background : backgrounds){
        
            ISprite sprite = background.getSprite();
            
            int w = resWidth;
            int h = resHeight;
         
            if(sprite != null && sprite.getWidth() != w) {
                sprite.setWidth(w);
            }
            if(sprite != null && sprite.getHeight() != h) {
                sprite.setHeight(h);
            }

            int type = background.getType();

            if(sprite != null && type == IScenery.CENTERED_TO_SCREEN)
                sprite.draw(new Vector3f(resWidth/2, resHeight/2, 0));

            if(sprite != null && type == IScenery.MOVE_WITH_CAMERA) {
                float parallex = background.getParallex();
                
                sprite.getTexture().setOffsetX(sprite.getTexture().getOffsetX() + (prevCameraPosition.x - cameraPosition.x) * parallex);
                sprite.getTexture().setOffsetY(sprite.getTexture().getOffsetY() + (prevCameraPosition.y - cameraPosition.y) * parallex);

                sprite.draw(new Vector3f(resWidth/2, resHeight/2, 0));
            }
        }   
    }
    
    private void drawForegrounds(int resWidth, int resHeight, Vector3f cameraPosition){
        for(IScenery foreground : foregrounds){
        
            ISprite sprite = foreground.getSprite();
            
            int w = resWidth;
            int h = resHeight;
         
            if(sprite != null && sprite.getWidth() != w) {
                sprite.setWidth(w);
            }
            if(sprite != null && sprite.getHeight() != h) {
                sprite.setHeight(h);
            }

            int type = foreground.getType();

            if(sprite != null && type == IScenery.CENTERED_TO_SCREEN)
                sprite.draw(new Vector3f(resWidth/2, resHeight/2, 0));

            if(sprite != null && type == IScenery.MOVE_WITH_CAMERA) {
                float parallex = foreground.getParallex();
                
                sprite.getTexture().setOffsetX(sprite.getTexture().getOffsetX() + (prevCameraPosition.x - cameraPosition.x) * parallex);
                sprite.getTexture().setOffsetY(sprite.getTexture().getOffsetY() + (prevCameraPosition.y - cameraPosition.y) * parallex);
                
                sprite.draw(new Vector3f(resWidth/2, resHeight/2, 0));
            }
        }   
    }
}
