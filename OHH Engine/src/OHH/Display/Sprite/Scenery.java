/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OHH.Display.Sprite;

import OHH.Core.Interfaces.IScenery;
import OHH.Core.Interfaces.ISprite;

/**
 *
 * @author Kaitlyn
 */
public class Scenery implements IScenery {    
    private ISprite sprite;
    private int type;
    private float parallex; //how fast the texture moves with the camera-> 1f is 1:1 ratio, 0.5F is 1:2.. etc
    
    public Scenery(ISprite sprite, int type, float parallex){
        this.sprite = sprite;
        this.type = type;
        this.parallex = parallex;
    }
    
    public Scenery(ISprite sprite, int type){
        this.sprite = sprite;
        this.type = type;
        this.parallex = 1f;
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
    public float getParallex() {
        return parallex;
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
    public void setParallex(float parallexRatio) {
        this.parallex = parallexRatio;
    }
}
