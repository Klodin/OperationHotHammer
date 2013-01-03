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

    @Override
    public void update(float delta) {
        position.y = GameWindow.INSTANCE.getFPS()+200;
    }
    
}
