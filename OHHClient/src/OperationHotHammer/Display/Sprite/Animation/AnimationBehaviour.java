/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Display.Sprite.Animation;

import OperationHotHammer.Core.Interfaces.IEntity;
import OperationHotHammer.Core.Interfaces.ISprite;

/**
 *
 * @author Kaitlyn
 */
public interface AnimationBehaviour {
    
    public void init(ISprite sprite, IEntity e);
    public void run(float delta, ISprite sprite, IEntity e);
    
}
