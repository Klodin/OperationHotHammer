/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package OHH.Core.GameObjects.StatusEffects;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class StatusEffects {

    private final float[] effectTimes = new float[10];
    private final IStatusEffectsVars[] effectVars = new IStatusEffectsVars[10];
    
    public final static int INVINCIBLE     = 0;
    public final static int SLEEP          = 1;
    public final static int STONE          = 2;
    public final static int POISEN         = 3;
    public final static int SILENCED       = 4;
    public final static int CONFUSED       = 5;
    public final static int FROZEN         = 6;
    public final static int BURNED         = 7;
    public final static int SLOW           = 8;
    public final static int FEAR           = 9;
    public final static int TRANSFORMATION = 10;
    
    public StatusEffects() {
        
    }
    
    public void add(int effectType, float time) {
        effectTimes[effectType] = Math.max(effectTimes[effectType], time);
    }
    
    public void add(int effectType, float time, IStatusEffectsVars vars) {
        effectVars[effectType] = vars;
    }
    
    public void update(float delta) {
        //TODO: count the times back down to 0 based on delta
        //      remove any effectvars for effects that finished
    }
    
    public boolean hasEffect(int effectType){
        return effectTimes[effectType] != 0;
    }
    
    public IStatusEffectsVars getVars(int effectType){
        return effectVars[effectType];
    }
    
    public float getTime(int effectType){
        return effectTimes[effectType];
    }
}
