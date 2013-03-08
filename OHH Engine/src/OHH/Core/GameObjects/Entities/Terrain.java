/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OHH.Core.GameObjects.Entities;

import OHH.Core.GameObjects.Boundary.Circle;
import OHH.Core.GameObjects.Entity;
import OHH.Core.Util.Debugging.Debugging;
import org.lwjgl.util.vector.Vector3f;
import java.util.Random;

/**
 *
 * @author Kaitlyn
 */
public class Terrain extends Entity{
    private static Random randomGenerator = new Random();
    public Terrain(float x, float y,float width, float height) {
        super(new Vector3f(x,y,50),new Circle(Math.max(width,height)/2));
        Debugging.INSTANCE.showMessage("Create (Entity->Terrain)");
        /*
        startx = position.x;
        starty = position.y;
        
        sizemod = randomGenerator.nextInt(100);
        
        ranx = 1+randomGenerator.nextInt(1+(100 - sizemod)/40);
        rany = 1+randomGenerator.nextInt(1+(100 - sizemod)/40);
     
                
        starth = (int)100 + sizemod;
        startw = (int)((100 + sizemod) * 1.02f);
        
        //radius = (float)Math.sqrt(startw*startw + starth*starth);
        radius = startw;
        position.z = radius;

        ((Circle)this.collider).radius = radius;
        */
    }

    /*
    float degree = 0;
    float speed = 0.3f;
    float radius;
    float startx;
    float starty;
    int startw;
    int starth;
    float ranx;
    float rany;
    int sizemod;
    */
    
    @Override
    public void update(float delta) {
        super.update(delta);
        /*
        degree+=(speed*delta)/radius;
        startw=(int)(5+radius*Math.cos(degree)*ranx);
        starth=(int)(5+radius*Math.sin(degree)*rany);
        */
        
        if(sprite != null){
            //sprite.setWidth(startw);
            //sprite.setHeight(starth);
            sprite.update(delta, this);
        }
    }
    
}
