/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Display.Sprite;

import OperationHotHammer.Core.Interfaces.ISprite;

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
