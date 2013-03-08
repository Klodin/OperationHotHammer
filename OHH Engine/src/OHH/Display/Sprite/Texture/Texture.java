package OHH.Display.Sprite.Texture;

import OHH.Core.Interfaces.ITexture;
import org.lwjgl.opengl.GL11;

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
    public void setHeight(int texHeight) {
        this.texHeight = texHeight;
    }

    @Override
    public void setWidth(int texWidth) {
        this.texWidth = texWidth;
    }
    
    @Override
    public int getWidth(){
        return this.texWidth;
    }
    
    @Override
    public int getHeight(){
        return this.texHeight;
    }
    
    @Override
    public int getOriginalWidth(){
        return this.originalWidth;
    }
    
    @Override
    public int getOriginalHeight(){
        return this.originalHeight;
    }
    
    @Override
    public float getOffsetX() {
        return textureOffsetX;
    }
    
    @Override
    public void setOffsetX(float offset) {
        textureOffsetX = offset;
        
        while(textureOffsetX >= getWidth())
            textureOffsetX -= getWidth();
        while(textureOffsetX <= -getWidth())
            textureOffsetX += getWidth();
    }
    
    @Override
    public float getOffsetY() {
        return textureOffsetY;
    }
    
    @Override
    public void setOffsetY(float offset) {       
        textureOffsetY = offset;
        
        while(textureOffsetY >= getHeight())
            textureOffsetY -= getHeight();
        while(textureOffsetY <= -getHeight())
            textureOffsetY += getHeight();
    }
    
    @Override
    public Texture clone() {
        Texture t = new Texture(target,textureID, originalWidth, originalHeight);
        t.texWidth = texWidth;
        t.texHeight = texHeight;
        return t;
    }
}
