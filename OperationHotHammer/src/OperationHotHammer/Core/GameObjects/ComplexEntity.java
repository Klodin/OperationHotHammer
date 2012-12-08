/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Core.GameObjects;

import OperationHotHammer.Core.Debugging.Debugging;
import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Kaitlyn
 */
public class ComplexEntity extends Entity {
    private final ArrayList<Entity> entities = new ArrayList<>();
        
    public ComplexEntity(Vector3f position){
        super(position);
    }
    
    @Override
    public void update(float delta) {
        for(Entity e : entities) {
            e.update(delta);
        }
    }
    
    @Override
    public void draw() {
        if(entities.isEmpty()) {
            Debugging.INSTANCE.showWarning("Attempted to draw an empty complex entity!");
            return;
        }

        for(Entity e : entities) {
            e.draw();
        }
    }
}
