/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Display;

import OperationHotHammer.Core.Interfaces.IForeground;
import OperationHotHammer.Core.Interfaces.ISprite;

/**
 *
 * @author Kaitlyn
 */
public class Foreground implements IForeground {    
    private ISprite sprite;
    private int type;
    private float parallexRatio;
    
    public Foreground(ISprite sprite, float parallex){
        this.sprite = sprite;
        this.type = IForeground.PAN;
        this.parallexRatio = parallex;
        
        if(getParallexRatio() == 0f)
            setType(IForeground.FIXED);
    }
    
    public Foreground(ISprite sprite){
        this.sprite = sprite;
        this.type = IForeground.FIXED;
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
