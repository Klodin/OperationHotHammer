/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package OperationHotHammer.Core.GameObjects;

import OperationHotHammer.Core.GameObjects.Boundary.Circle;
import OperationHotHammer.Core.Util.Settings;

public class PlayerCharacter extends Character {
    
    public PlayerCharacter(float x, float y) { 
        super(x, y, new Circle(Settings.GRID_SPACE_SIZE/2));
    }
    
    public void update() {
        
        //get / handle user inputs.. update position and movement as needed.
        
    }

}
