
package OperationHotHammer.Core;

import OperationHotHammer.Core.GameObjects.Entity;
import OperationHotHammer.Core.Interfaces.IBackground;
import OperationHotHammer.Core.Interfaces.IForeground;
import OperationHotHammer.Core.Interfaces.ISprite;
import OperationHotHammer.Core.Util.Debugging.Debugging;
import OperationHotHammer.Core.Util.DepthSortedList;
import OperationHotHammer.Core.Util.EntityArrayList;
import OperationHotHammer.Core.Util.EntityList;
import OperationHotHammer.Core.Util.Partitioning.QTree;
import OperationHotHammer.Core.Util.Settings;
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
    private ArrayList<IForeground> foregrounds = new ArrayList();
    
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
    
    public void attach(IBackground s) {
        backgrounds.add(s);
    }
    
    public void attach(IForeground s) {
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
        
        for(IBackground background : backgrounds)
             background.getSprite().update(delta, null);
        
        for(IForeground foreground : foregrounds)
             foreground.getSprite().update(delta, null);
        
        /* camera easing.. */
        
        float playerX = Game.INSTANCE.getPlayer().getX();
        float playerY = Game.INSTANCE.getPlayer().getY();

        //current dif between player position and camera
        float difX = Math.abs(getX() - playerX);
        float difY = Math.abs(getY() - playerY);
        
        //get a percentage of difference to move on this iteration
        float adjX = difX * Settings.CAMERA_EASE_TO_POSITION;
        float adjY = difY * Settings.CAMERA_EASE_TO_POSITION;
        
        //find the direction (positive or negative)
        float modX = getX() - playerX > 0? -1 : 1;
        float modY = getY() - playerY > 0? -1 : 1;
        
        //if the percentage is less then our lower limit cap off at the limit
        if(adjX < Settings.CAMERA_EASE_LOWER_LIMIT)
            adjX = Settings.CAMERA_EASE_LOWER_LIMIT;
        if(adjY < Settings.CAMERA_EASE_LOWER_LIMIT)
            adjY = Settings.CAMERA_EASE_LOWER_LIMIT;
        
        //if we are over the difference remaining, just move the remaining difference instead
        if(adjX > difX)
            adjX = difX;
        if(adjY > difY)
            adjY = difY;
        
        //lets update the scene position now
        if(adjX != 0)
            setX(getX() + modX * adjX);
        else
            setX(playerX);
        
        if(adjY != 0)
            setY(getY() + modY * adjY);
        else
            setY(playerY);
    }
    
    public void draw(int resWidth, int resHeight, Vector3f cameraPosition){
        drawBackgrounds(resWidth, resHeight, cameraPosition);
        
        entitiesToDisplay.clear();
        quadTree.retrieveObjects(entitiesToDisplay, cameraPosition.x, cameraPosition.y, resWidth/2, resHeight/2);
        
        for(Entity e : entitiesToDisplay) {
            e.draw(resWidth, resHeight, cameraPosition);
        }
        
        drawForegrounds(resWidth, resHeight, cameraPosition);
    }
    
    private void drawBackgrounds(int resWidth, int resHeight, Vector3f cameraPosition){
        for(IBackground background : backgrounds){
        
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

            if(sprite != null && type == IBackground.FIXED)
                sprite.draw(new Vector3f(resWidth/2, resHeight/2, 0));

            if(sprite != null && type == IBackground.PAN) {
                float parallex = background.getParallexRatio();
                
                sprite.getTexture().setOffsetX(-cameraPosition.x * parallex);
                sprite.getTexture().setOffsetY(-cameraPosition.y * parallex);
                
                sprite.draw(new Vector3f(resWidth/2, resHeight/2, 0));
            }
        }   
    }
    
    private void drawForegrounds(int resWidth, int resHeight, Vector3f cameraPosition){
        for(IForeground foreground : foregrounds){
        
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

            if(sprite != null && type == IForeground.FIXED)
                sprite.draw(new Vector3f(resWidth/2, resHeight/2, 0));

            if(sprite != null && type == IForeground.PAN) {
                float parallex = foreground.getParallexRatio();
                
                sprite.getTexture().setOffsetX(-cameraPosition.x * parallex);
                sprite.getTexture().setOffsetY(-cameraPosition.y * parallex);
                
                sprite.draw(new Vector3f(resWidth/2, resHeight/2, 0));
            }
        }   
    }
}
