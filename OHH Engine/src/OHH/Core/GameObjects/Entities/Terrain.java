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
    public Terrain(float x, float y, int width, int height, float radius) {
        super(new Vector3f(x,y,50), width, height, new Circle(radius));
        Debugging.INSTANCE.showMessage("Create (Entity->Terrain)");
    }
    
    @Override
    public void update(float delta) {
        super.update(delta);
        
        if(getSprite() != null){
            getSprite().update(delta, this);
        }
    }
    
}
