/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Core.GameObjects.Entities;

import OperationHotHammer.Core.GameObjects.Boundary.Circle;
import OperationHotHammer.Core.GameObjects.Entity;
import OperationHotHammer.Core.Interfaces.ISprite;
import OperationHotHammer.Core.Util.Debugging.Debugging;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Kaitlyn
 */
public class SimpleEntity extends Entity{
    private int width;
    private int height;
    
    public SimpleEntity(float x, float y, int width, int height) {
        super(new Vector3f(x,y,10),new Circle(Math.max(width,height)));
        
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
        
        if(sprite != null)
            sprite.update(delta, this);
    }
    
}
