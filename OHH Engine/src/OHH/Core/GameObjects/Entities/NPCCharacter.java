/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package OHH.Core.GameObjects.Entities;

import OHH.Core.GameObjects.Boundary.IBoundaryShape;
import org.lwjgl.util.vector.Vector3f;

public abstract class NPCCharacter/* extends AnimatedEntity */{
    
    public NPCCharacter(Vector3f p, IBoundaryShape colliderShape) { 
       // super(p, colliderShape);
    }
    
    /*
    @Override
    public void update(float delta) {
        super.update(delta);
        
        //let's assume default npc does nothing in particular on update.. override this as needed in other ones
        
    }
*/
}
