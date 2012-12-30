/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Core.GameObjects.Entities;

import OperationHotHammer.Core.GameObjects.Boundary.IBoundaryShape;
import OperationHotHammer.Core.GameObjects.Entity;

/**
 * @author Raymond
 */
public class Terrain extends Entity{
    public float speedModifier = 0;
    public void setSpeedModifier(float newSpeedModifier){
        speedModifier = newSpeedModifier;
    }
}
