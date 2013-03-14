/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OHH.Display.Sprite.Animation;

import OHH.Core.Interfaces.ISprite;
import OHH.Core.Interfaces.ITexture;

/**
 *
 * @author Kaitlyn
 */
public class TextureScrollBehaviour implements SpriteBehaviour{
    private float xSpeed = 0;
    private float ySpeed = 0;
    
    private boolean isInit = false;
    
    public TextureScrollBehaviour(float xSpeed, float ySpeed){
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }
    
    public void init(ISprite sprite){       
        isInit = true;
        
        float offsetX = 0f;
        float offsetY = 0f;
        
        sprite.getTexture().setOffsetX(offsetX);
        sprite.getTexture().setOffsetY(offsetY);
    }
    
    @Override
    public void run(float delta, ISprite sprite){
        
        if(sprite.getTexture() == null) return;
        
        if(!isInit)
            init(sprite);
        
        float cloudXSpeed = (xSpeed*delta);
        float cloudYSpeed = (ySpeed*delta);
        
        ITexture t = sprite.getTexture();
                
        float offsetX = t.getOffsetX() + cloudXSpeed;
        float offsetY = t.getOffsetY() + cloudYSpeed;
        
        t.setOffsetX(offsetX);
        t.setOffsetY(offsetY);
    }  
}
