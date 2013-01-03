/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Core.GameObjects.Entities;

import OperationHotHammer.Core.GameObjects.Entity;
import org.lwjgl.util.vector.Vector3f;

/**
 * @author Raymond
 */
public class Terrain extends Entity{
    public Terrain(float x, float y, float z){
        super(new Vector3f(x,y,z));
    }
    public float speedModifier = 0;
    public void setSpeedModifier(float newSpeedModifier){
        speedModifier = newSpeedModifier;
    }
    
    @Override
    public void update(float delta){
        //This space is intentionally left blank
    }
}
