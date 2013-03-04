package OperationHotHammer.Display.Sprite.Texture;

import OperationHotHammer.Core.Interfaces.ITexture;
import org.lwjgl.opengl.GL11;

/**
 * A texture to be bound within LWJGL. This object is responsible for 
 * keeping track of a given OpenGL texture and for calculating the
 * texturing mapping coordinates of the full image.
 * 
 * Since textures need to be powers of 2 the actual texture may be
 * considerably bigged that the source image and hence the texture
 * mapping coordinates need to be adjusted to matchup drawing the
 * sprite against the texture.
 *
 * @author Kevin Glass
 * @author Brian Matzon
 */
public class Texture implements ITexture {
    private int target; 
    private int textureID;
    
    private final int originalWidth;
    private final int originalHeight;

    private int texWidth;
    private int texHeight;
    
    private float textureOffsetX = 0;
    private float textureOffsetY = 0;

    
    public Texture(int target,int textureID, int width, int height) {
        this.target = target;
        this.textureID = textureID;
        
        this.originalWidth = width;
        this.originalHeight = height;
        
        this.texHeight=width;
        this.texHeight=height;
    }
    
    @Override
    public void bind() {
      GL11.glBindTexture(target, textureID); 
    }
    
    @Override
    public void setTextureHeight(int texHeight) {
        this.texHeight = texHeight;
    }

    @Override
    public void setTextureWidth(int texWidth) {
        this.texWidth = texWidth;
    }
    
    @Override
    public int getTextureWidth(){
        return this.texWidth;
    }
    
    @Override
    public int getTextureHeight(){
        return this.texHeight;
    }
    
    @Override
    public int getOriginalTextureWidth(){
        return this.originalWidth;
    }
    
    @Override
    public int getOriginalTextureHeight(){
        return this.originalHeight;
    }
    
    @Override
    public float getOffsetX() {
        return textureOffsetX;
    }
    
    @Override
    public void setOffsetX(float offset) {
        textureOffsetX = offset;
        
        while(textureOffsetX >= getTextureWidth())
            textureOffsetX -= getTextureWidth();
        while(textureOffsetX <= -getTextureWidth())
            textureOffsetX += getTextureWidth();
    }
    
    @Override
    public float getOffsetY() {
        return textureOffsetY;
    }
    
    @Override
    public void setOffsetY(float offset) {       
        textureOffsetY = offset;
        
        while(textureOffsetY >= getTextureHeight())
            textureOffsetY -= getTextureHeight();
        while(textureOffsetY <= -getTextureHeight())
            textureOffsetY += getTextureHeight();
    }
    
    @Override
    public Texture clone() {
        Texture t = new Texture(target,textureID, originalWidth, originalHeight);
        t.texWidth = texWidth;
        t.texHeight = texHeight;
        return t;
    }
}
