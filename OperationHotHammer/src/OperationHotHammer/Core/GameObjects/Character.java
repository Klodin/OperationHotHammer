/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package OperationHotHammer.Core.GameObjects;

import OperationHotHammer.Core.GameObjects.Boundary.BoundaryShape;


public abstract class Character extends GameObject {
    
    public float strength;
    public float dexterity;
    public float vitality;
    public float intelligence;
    
    public Character (float x, float y, BoundaryShape colliderShape) {
        super(x, y, colliderShape);
    }
    
}
