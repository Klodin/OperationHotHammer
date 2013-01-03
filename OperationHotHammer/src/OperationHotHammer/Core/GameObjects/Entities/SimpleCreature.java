/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Core.GameObjects.Entities;

import OperationHotHammer.Core.GameObjects.Boundary.Circle;
import OperationHotHammer.Core.GameObjects.Entity;
import OperationHotHammer.Core.Util.Settings;
import OperationHotHammer.Display.GameWindow;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Kaitlyn
 */
public class SimpleCreature extends Entity{
    
    public SimpleCreature(float x, float y) {
        super(new Vector3f(x,y,0),new Circle(Settings.GRID_SPACE_SIZE/2));
    }

    float direction = 1.5f;
    
    @Override
    public void update(float delta) {
        if(position.y > 400 && direction > 0) 
            direction *= -1f;
        if(position.y < 200 && direction < 0) 
            direction *= -1f;
        position.y += direction;
    }
    
}
