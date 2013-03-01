package OperationHotHammer.Display;

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
    
    private int height;
    private int width;

    private int texWidth;
    private int texHeight;

    private float widthRatio;
    private float heightRatio;
    
    private float textureOffsetX = 0;
    private float textureOffsetY = 0;

    
    public Texture(int target,int textureID, int width, int height) {
        this.target = target;
        this.textureID = textureID;
        
        this.originalWidth = width;
        this.originalHeight = height;
        
        this.width=width;
        this.height=height;
        
        this.texHeight=width;
        this.texHeight=height;
        
        setSizes();
    }
    
    @Override
    public void bind() {
      GL11.glBindTexture(target, textureID); 
    }
    
    @Override
    public int getDrawHeight() {
        return height;
    }
    
    @Override
    public int getDrawHeight(int drawStyle) {
        int h = height;
        if((drawStyle & ITexture.MAINTAIN_ASPECT_MAX) != 0 || (drawStyle & ITexture.MAINTAIN_ASPECT_MIN) != 0){
            //if((drawStyle & ITexture.MAINTAIN_ASPECT_MAX) != 0) {
//TODO:
            //}else{
                if(texHeight < texWidth)
                    h = (int)(((float)texHeight/(float)texWidth)*(float)width);
           // }
        }
        return h;
    }
    
    @Override
    public int getDrawWidth() {
        return width;
    }
    
    @Override
    public int getDrawWidth(int drawStyle) {
        int w = width;
        if((drawStyle & ITexture.MAINTAIN_ASPECT_MAX) != 0 || (drawStyle & ITexture.MAINTAIN_ASPECT_MIN) != 0){
            //if((drawStyle & ITexture.MAINTAIN_ASPECT_MAX) != 0) {
//TODO: 
            //}else{
                if(texWidth < texHeight)
                    w = (int)(((float)texWidth/(float)texHeight)*(float)height);
            //}
        }
            
        return w;
    }

    @Override
    public void setDrawWidth(int width) {
        this.width = width;
        setSizes();
    }
    
    @Override
    public void setDrawHeight(int height) {
        this.height = height;
        setSizes();
    }
    
    @Override
    public float getHeightRatio() {
        return heightRatio;
    }

    @Override
    public float getWidthRatio() {
        return widthRatio;
    }
    
    @Override
    public void setTextureHeight(int texHeight) {
        this.texHeight = texHeight;
        setSizes();
    }

    @Override
    public void setTextureWidth(int texWidth) {
        this.texWidth = texWidth;
        setSizes();
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
        
        if(textureOffsetX >= getTextureWidth())
            textureOffsetX -= getTextureWidth();
        if(textureOffsetX <= -getTextureWidth())
            textureOffsetX += getTextureWidth();
    }
    
    @Override
    public float getOffsetY() {
        return textureOffsetY;
    }
    
    @Override
    public void setOffsetY(float offset) {       
        textureOffsetY = offset;
        
        if(textureOffsetY >= getTextureHeight())
            textureOffsetY -= getTextureHeight();
        if(textureOffsetY <= -getTextureHeight())
            textureOffsetY += getTextureHeight();
    }
    

    private void setSizes() {
        if (texHeight != 0) {
            heightRatio = ((float) height)/texHeight;
        }

        if (texWidth != 0) {
            widthRatio = ((float) width)/texWidth;
        }
    }
    
    @Override
    public Texture clone() {
        Texture t = new Texture(target,textureID, originalWidth, originalHeight);
        t.height = height;
        t.width = width;
        t.texWidth = texWidth;
        t.texHeight = texHeight;
        t.widthRatio = widthRatio;
        t.heightRatio = heightRatio;
        return t;
    }
}
