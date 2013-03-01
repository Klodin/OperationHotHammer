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
    private ISprite sprite;
    private int type;
    private float parallexRatio;
    
    public Background(ISprite sprite, float parallex){
        this.sprite = sprite;
        this.type = IBackground.PAN;
        this.parallexRatio = parallex;
        
        if(getParallexRatio() == 0f)
            setType(IBackground.FIXED);
    }
    
    public Background(ISprite sprite){
        this.sprite = sprite;
        this.type = IBackground.FIXED;
        this.parallexRatio = 0f;
    }

    @Override
    public ISprite getSprite() {
        return sprite;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public float getParallexRatio() {
        return parallexRatio;
    }

    @Override
    public void setSprite(ISprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void setParallexRatio(float parallexRatio) {
        this.parallexRatio = parallexRatio;
    }
}
