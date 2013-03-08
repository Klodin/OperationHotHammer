/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OHH.Display.Sprite.Animation;

import OHH.Core.Interfaces.IEntity;
import OHH.Core.Interfaces.ISprite;

/**
 *
 * @author Kaitlyn
 */
public interface AnimationBehaviour {
    
    public void init(ISprite sprite, IEntity e);
    public void run(float delta, ISprite sprite, IEntity e);
    
}
