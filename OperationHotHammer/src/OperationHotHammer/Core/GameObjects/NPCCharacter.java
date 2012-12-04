/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package OperationHotHammer.Core.GameObjects;

import OperationHotHammer.Core.GameObjects.Boundary.BoundaryShape;

public abstract class NPCCharacter extends Character {
    
    public NPCCharacter(float x, float y, BoundaryShape colliderShape) { 
        super(x, y, colliderShape);
    }
    
    @Override
    public void update(float delta) {
        super.update(delta);
        
        //let's assume default npc does nothing in particular on update.. override this as needed in other ones
        
    }

}
