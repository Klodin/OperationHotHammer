/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package OperationHotHammer.Core.GameObjects.Characters;

import OperationHotHammer.Core.GameObjects.Boundary.IBoundaryShape;
import OperationHotHammer.Core.GameObjects.AnimatedEntity;

public abstract class NPCCharacter extends AnimatedEntity {
    
    public NPCCharacter(float x, float y, float z, IBoundaryShape colliderShape) { 
        super(x, y, z, colliderShape);
    }
    
    @Override
    public void update(float delta) {
        super.update(delta);
        
        //let's assume default npc does nothing in particular on update.. override this as needed in other ones
        
    }

}
