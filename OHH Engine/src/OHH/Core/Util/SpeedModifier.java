/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OHH.Core.Util;

/**
 *
 * @author Klodin
 */
public class SpeedModifier {
    public float multMod = 1;
    public float addMod = 0;
    public SpeedModifier(){
    }
    
    /**
     * @param modifier can be '+' or '*'
     * @param speed 
     */
    public SpeedModifier(String modifier, float speed){
        switch(modifier){
            case "+":
                addMod = speed;
                break;
            case "*":
                multMod = speed;
                break;
            default:
                break;
        }
    }
    
    /**
     * Sets a change in speed that is based on the Entity's base speed
     * @param multiplier 
     */
    public void setMult(float multiplier){
        multMod = multiplier;
    }
    
    /**
     * Sets a change in speed that is unrelated to the Entity's base speed
     * @param addition 
     */
    public void setAdd(float addition){
        addMod = addition;
    }
    
    /**
     * Combines elements of speed with this speedModifier
     * @param speed 
     */
    public void combine(SpeedModifier speed){
        multMod = multMod * speed.multMod;
        addMod = addMod + speed.addMod;
    }
    
    /**
     * Multiplies the multiplier by the new Multiplier and Add
     * @param speed 
     */
    public void combine(float MultChange, float AddChange){
        multMod = multMod * MultChange;
        addMod = addMod + AddChange;
    }
    
    public float apply(float moveSpeed){
        float newSpeed = moveSpeed * multMod + addMod;
        /*if (newSpeed < Settings.MINSPEED){
            newSpeed = Settings.MINSPEED;
        }
        if (newSpeed > Settings.MAXSPEED){
            newSpeed = Settings.MAXSPEED;
        }*/
        return newSpeed;
    }
}