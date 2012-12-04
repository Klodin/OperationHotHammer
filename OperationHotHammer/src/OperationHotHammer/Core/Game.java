/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package OperationHotHammer.Core;

import OperationHotHammer.Core.Util.Settings;

public class Game {

    Scene scene;
    
    public Game() {
     
        scene = new Scene("Green Valley", Settings.GRID_SPACE_SIZE * 50, Settings.GRID_SPACE_SIZE * 50);
        
        
    }
    
}
