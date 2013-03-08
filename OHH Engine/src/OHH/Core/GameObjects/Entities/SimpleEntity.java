/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OHH.Core.GameObjects.Entities;

import OHH.Core.GameObjects.Boundary.Circle;
import OHH.Core.GameObjects.Entity;
import OHH.Core.Interfaces.ISprite;
import OHH.Core.Util.Debugging.Debugging;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Kaitlyn
 */
public class SimpleEntity extends Entity{
    private int width;
    private int height;
    
    public SimpleEntity(float x, float y, int width, int height, float radius) {
        super(new Vector3f(x,y,10),width, height, new Circle(radius));
        
        Debugging.INSTANCE.showMessage("Create (Entity->SimpleEntity)");
        
        this.width = width;
        this.height = height;
    }
    
    @Override
    public void attach(ISprite sprite){
        super.attach(sprite);
        
        sprite.setWidth(width);
        sprite.setHeight(height);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        
        if(getSprite() != null)
            getSprite().update(delta, this);
    }
    
}
