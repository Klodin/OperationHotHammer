/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Display.Sprite.Animation;

import OperationHotHammer.Core.Interfaces.IEntity;
import OperationHotHammer.Core.Interfaces.ISprite;
import OperationHotHammer.Core.Interfaces.ITexture;

/**
 *
 * @author Kaitlyn
 */
public class TextureScrollBehaviour implements AnimationBehaviour{
    private float xSpeed = 0;
    private float ySpeed = 0;
    
    public TextureScrollBehaviour(float xSpeed, float ySpeed){
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }
    
    @Override
    public void init(ISprite sprite, IEntity entity){
        AnimatedSprite s = ((AnimatedSprite)sprite);
        AnimationFrame animationFrame = s.getAnimationFrame();
                
        float offsetX = 0f;
        float offsetY = 0f;
                
        animationFrame.sprite.getTexture().setOffsetX(offsetX);
        animationFrame.sprite.getTexture().setOffsetY(offsetY);
    }
    
    @Override
    public void run(float delta, ISprite sprite, IEntity entity){
        float cloudXSpeed = (xSpeed*delta);
        float cloudYSpeed = (ySpeed*delta);
                
        AnimatedSprite s = ((AnimatedSprite)sprite);
        ITexture t = s.getAnimationFrame().sprite.getTexture();
                
        float offsetX = t.getOffsetX() + cloudXSpeed;
        float offsetY = t.getOffsetY() + cloudYSpeed;
        
        t.setOffsetX(offsetX);
        t.setOffsetY(offsetY);
    }  
}
