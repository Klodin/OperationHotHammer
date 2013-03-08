/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OHH.Display.Sprite.Animation;

import OHH.Core.Interfaces.ISprite;

/**
 *
 * @author Kaitlyn
 */
public class AnimationFrame {
    public float time;
    public ISprite sprite;
    
    public AnimationFrame(ISprite sprite, float time){
        this.sprite = sprite;
        this.time = time;
    }
}
