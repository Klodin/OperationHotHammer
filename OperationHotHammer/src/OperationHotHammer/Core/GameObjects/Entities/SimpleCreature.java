/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Core.GameObjects.Entities;

import OperationHotHammer.Core.GameObjects.Boundary.Circle;
import OperationHotHammer.Core.GameObjects.Entity;
import OperationHotHammer.Core.Util.Settings;
import org.lwjgl.util.vector.Vector3f;
import java.util.Random;

/**
 *
 * @author Kaitlyn
 */
public class SimpleCreature extends Entity{
    private static Random randomGenerator = new Random();
    public SimpleCreature(float x, float y) {
        super(new Vector3f(x,y,50),new Circle(Settings.GRID_SPACE_SIZE/2));
        
        startx = position.x;
        starty = position.y;
        
        sizemod = randomGenerator.nextInt(100);
        
        ranx = 1+randomGenerator.nextInt(1+sizemod/20);
        rany = 1+randomGenerator.nextInt(1+sizemod/20);
        
    }

    float degree = 0;
    float speed = 0.3f;
    float radius = 90;
    float startx;
    float starty;
    float ranx;
    float rany;
    int sizemod;
    
    @Override
    public void update(float delta) {
        degree+=(speed*delta)/radius;
        position.x=(float)(startx+radius*Math.cos(degree)*ranx);
        position.y=(float)(starty+radius*Math.sin(degree)*rany);
        position.z=(position.x/20) + sizemod;
        sprite.setWidth((int)(position.x/20) + sizemod);
        sprite.setHeight((int)(position.x/20) + sizemod);
    }
    
}
