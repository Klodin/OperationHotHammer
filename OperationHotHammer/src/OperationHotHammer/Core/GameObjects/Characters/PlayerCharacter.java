/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package OperationHotHammer.Core.GameObjects.Characters;

import OperationHotHammer.Core.GameObjects.Boundary.Circle;
import OperationHotHammer.Core.Util.Settings;
import OperationHotHammer.Core.GameObjects.DestructableEntity;
import OperationHotHammer.Core.GameObjects.DestructableEntity;
import OperationHotHammer.Core.GameObjects.Entity;
import OperationHotHammer.Core.GameObjects.Entity;
import OperationHotHammer.Core.GameObjects.LivingEntity;

public class PlayerCharacter extends LivingEntity {
    
    public PlayerCharacter(float x, float y) { 
        super(x, y, new Circle(Settings.GRID_SPACE_SIZE/2));
    }
    
    public float getAttackDamage() { 
        return 10;
    }
    
    public float getMovementSpeed() { 
        return Settings.GRID_SPACE_SIZE/4;
    }
    
    @Override
    public void update(float delta) {
        super.update(delta);
        
        //get / handle user inputs.. update position and movement as needed.
        
    }
    
    @Override
    public void attack(Entity e) {
        if(e instanceof DestructableEntity) {
            ((DestructableEntity)e).damage(getAttackDamage());
        }
    }
    
    
    
    @Override
    public void damage(float amount) {
        if(!dead) {
            //damage received

            //notify user somehow

            //subtract armor bonus from damage

            health -= amount;
            if(health<=0) {
                health = 0;
                dead = true;
            }
        }
    }

}
