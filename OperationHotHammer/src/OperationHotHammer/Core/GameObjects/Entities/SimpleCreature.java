/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Core.GameObjects.Entities;

import OperationHotHammer.Core.GameObjects.Boundary.Circle;
import OperationHotHammer.Core.GameObjects.Entity;
import OperationHotHammer.Core.Util.Settings;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Kaitlyn
 */
public class SimpleCreature extends Entity{
    
    public SimpleCreature(float x, float y) {
        super(new Vector3f(x,y,0),new Circle(Settings.GRID_SPACE_SIZE/2));
        
        startx = position.x;
        starty = position.y;
        radius = 100;
    }

    float degree=0;
    float speed=0.5f;
    float radius;
    float startx;
    float starty;
    
    @Override
    public void update(float delta) {
        degree+=(speed*delta)/radius;
        position.x=(float)(startx+radius*Math.cos(degree));
        position.y=(float)(starty+radius*Math.sin(degree));
    }
    
}
