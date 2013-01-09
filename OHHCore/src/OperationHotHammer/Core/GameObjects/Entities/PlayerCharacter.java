/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package OperationHotHammer.Core.GameObjects.Entities;

import OperationHotHammer.Core.GameObjects.Boundary.Circle;
import OperationHotHammer.Core.Util.Settings;
import OperationHotHammer.Core.GameObjects.AdvancedEntity;
import OperationHotHammer.Core.GameObjects.Entity;
import OperationHotHammer.Core.GameObjects.AnimatedEntity;
import org.lwjgl.util.vector.Vector3f;

public class PlayerCharacter extends AnimatedEntity {
    
    public float strength;
    public float dexterity;
    public float vitality;
    public float intelligence;
    
    public PlayerCharacter(float x, float y) { 
        super(new Vector3f(x, y, Entity.LAYER_MIDDLE), new Circle(Settings.PLAYER_SIZE));
    }
    
    public float getAttackDamage() { 
        return 10;
    }
    
    public float getMovementSpeed() { 
        return Settings.GRID_SPACE_SIZE/2;
    }
    
    @Override
    public void update(float delta) {
        super.update(delta);
        
        //get / handle user inputs.. update position and movement as needed.
        
    }
    
    @Override
    public void attack(Entity e) {
        if(e instanceof AdvancedEntity) {
            ((AdvancedEntity)e).damage(getAttackDamage());
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
