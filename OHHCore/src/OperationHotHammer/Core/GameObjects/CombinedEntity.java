/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Core.GameObjects;

import OperationHotHammer.Core.Util.Debugging.Debugging;
import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Kaitlyn
 */
public class CombinedEntity extends AdvancedEntity {


    private final ArrayList<Entity> entities = new ArrayList<>();
        
    public CombinedEntity(Vector3f position){
        super(position, null);
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
            Debugging.INSTANCE.showWarning("Attempted to draw an empty entity group!");
            return;
        }

        for(Entity e : entities) {
            e.draw();
        }
    }
}
