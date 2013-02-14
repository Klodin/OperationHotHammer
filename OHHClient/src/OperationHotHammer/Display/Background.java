/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Display;

import OperationHotHammer.Core.Interfaces.IBackground;
import OperationHotHammer.Core.Interfaces.ISprite;

/**
 *
 * @author Kaitlyn
 */
public class Background implements IBackground {    
    private ISprite backgroundSprite;
    private int backgroundType;
    private float backgroundParallexRatio;
    
    public Background(ISprite sprite, float parallex){
        backgroundSprite = sprite;
        backgroundType = IBackground.BACKGROUND_PAN;
        backgroundParallexRatio = parallex;
        
        if(getBackgroundParallexRatio() == 0f)
            setBackgroundType(IBackground.BACKGROUND_FIXED);
    }
    
    public Background(ISprite sprite){
        backgroundSprite = sprite;
        backgroundType = IBackground.BACKGROUND_FIXED;
        backgroundParallexRatio = 0f;
    }

    @Override
    public ISprite getBackgroundSprite() {
        return backgroundSprite;
    }

    @Override
    public int getBackgroundType() {
        return backgroundType;
    }

    @Override
    public float getBackgroundParallexRatio() {
        return backgroundParallexRatio;
    }

    @Override
    public void setBackgroundSprite(ISprite backgroundSprite) {
        this.backgroundSprite = backgroundSprite;
    }

    @Override
    public void setBackgroundType(int backgroundType) {
        this.backgroundType = backgroundType;
    }

    @Override
    public void setBackgroundParallexRatio(float backgroundParallexRatio) {
        this.backgroundParallexRatio = backgroundParallexRatio;
    }
}
