package OperationHotHammer.Display.Sprite;

import OperationHotHammer.Core.Interfaces.IEntity;
import OperationHotHammer.Core.Interfaces.ISprite;
import OperationHotHammer.Core.Interfaces.ITexture;
import OperationHotHammer.Core.Util.Debugging.Debugging;
import OperationHotHammer.Display.GameWindow;
import OperationHotHammer.Display.Hud;
import OperationHotHammer.Display.Sprite.Texture.Texture;
import OperationHotHammer.Display.Sprite.Texture.TextureLoader;
import java.io.IOException;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class Sprite implements ISprite {
    private final Texture texture;
    private final int drawStyle;
    
    private int adjustedWidth = 0;
    private int adjustedHeight = 0;
    private int actualWidth = 0;
    private int actualHeight = 0;
    
    private static int drawnCount = 0;
    private static int updateCount = 0;
    
    public static int getDrawnCount(){
        return drawnCount;
    }
    
    public static int getUpdateCount(){
        return updateCount;
    }
    
    public static void clearCounts(){
        drawnCount = 0;
        updateCount = 0;
    }
    
    public Sprite(String resource) {
        Texture temp = null;
        drawStyle = ITexture.TILED;
        
        try{
            temp = TextureLoader.INSTANCE.getTexture(resource);
        }catch(IOException e){
            Debugging.INSTANCE.showError("A problem occured when loading a texture! (" + resource + ")");            
        }
        
        texture = temp;
        
        adjustedWidth = texture.getTextureWidth();
        adjustedHeight = texture.getTextureHeight();
    }
    
    public Sprite(String resource, int drawStyle) {
        Texture temp = null;
        this.drawStyle = drawStyle;
        
        try{
            temp = TextureLoader.INSTANCE.getTexture(resource);
        }catch(IOException e){
            Debugging.INSTANCE.showError("A problem occured when loading a texture! (" + resource + ")");            
        }
        texture = temp;
        
        this.adjustedWidth = this.actualWidth = texture.getTextureWidth();
        this.adjustedHeight = this.actualHeight = texture.getTextureHeight();
    }
    
    @Override
    public void setWidth(int w) {
        this.adjustedWidth = this.actualWidth = w;
        
        if(isMaintainingAspectMin() || isMaintainingAspectMax()) {
            float scale = Math.min((float)this.actualWidth/(float)texture.getOriginalTextureWidth(), (float)this.actualHeight/(float)texture.getOriginalTextureHeight());
            this.adjustedWidth = (int)((float)texture.getOriginalTextureWidth() * scale);
            this.adjustedHeight = (int)((float)texture.getOriginalTextureHeight() * scale);
        }
    }
    
    @Override
    public void setHeight(int h) {
        this.adjustedHeight = this.actualHeight = h;
        
        if(isMaintainingAspectMin() || isMaintainingAspectMax()) {
            float scale = Math.min((float)this.actualWidth/(float)texture.getOriginalTextureWidth(), (float)this.actualHeight/(float)texture.getOriginalTextureHeight());
            this.adjustedWidth = (int)((float)texture.getOriginalTextureWidth() * scale);
            this.adjustedHeight = (int)((float)texture.getOriginalTextureHeight() * scale);
        }
    }
    
    @Override
    public int getWidth() {
        return actualWidth;
    }
    
    @Override
    public int getHeight() {
        return actualHeight;
    }
    
    @Override
    public Texture getTexture(){
        return texture;
    }
    
    @Override
    public void update(float delta, IEntity e) {
        updateCount++;
    }
    
    public boolean isTiled(){
        return (drawStyle & ITexture.TILED) != 0;
    }
    
    public boolean isStetched(){
        return (drawStyle & ITexture.STRETCH) != 0;
    }
    
    public boolean isMaintainingAspectMin(){
        return (drawStyle & ITexture.MAINTAIN_ASPECT_MAX) != 0;
    }
    
    public boolean isMaintainingAspectMax(){
        return (drawStyle & ITexture.MAINTAIN_ASPECT_MIN) != 0;
    }
    
    @Override
    public void draw(Vector3f position) {
        
        drawnCount++;
        
        // store the current model matrix
	GL11.glPushMatrix();
		
	// bind to the appropriate texture for this sprite
	texture.bind();
    
	// translate to the right location and prepare to draw
        if(isTiled())
            GL11.glTranslatef(position.x-getWidth()/2, position.y-getHeight()/2, 0);		
        else
            GL11.glTranslatef(position.x-this.adjustedWidth/2, position.y-this.adjustedHeight/2, 0);		
        
    	GL11.glColor3f(1f, 1f, 1f);

	// draw a quad textured to match the sprite
    	GL11.glBegin(GL11.GL_QUADS);
	{
            
            float heightRatio = 1f; //1f would match the texture to the quad's dimensions
            float widthRatio = 1f;  //1f would match the texture to the quad's dimensions
            
            if(isTiled()) {
                heightRatio = (float)getHeight() / (float)texture.getTextureHeight();
                widthRatio = (float)getWidth() / (float)texture.getTextureWidth();
            }
            
            if(isTiled() && (texture.getOffsetX() != 0 || texture.getOffsetY() != 0)) {
                //an offset has been set: so render this texture within the quad's dimensions, +2 extra texture width +2 extra height.  Then adjust by the offset amounts
                    
                //offset amounts to adjust by
                int x_offset = (int)texture.getOffsetX();
                int y_offset = (int)texture.getOffsetY();

                //min amount texture must be rendered shifted off screen (1 texture width & height)
                int x_min = -texture.getTextureWidth();
                int y_min = -texture.getTextureHeight();

                //multipliers: amount of texture repeats required to completly fill the quad (rounded up)
                int x_mul = (int)Math.ceil((float)getWidth()/(float)texture.getTextureWidth())+2;
                int y_mul = (int)Math.ceil((float)getHeight()/(float)texture.getTextureHeight())+2;
                    
                int x_size = texture.getTextureWidth()*x_mul;
                int y_size = texture.getTextureHeight()*y_mul;

                heightRatio = (float)y_size / (float)texture.getTextureHeight();
                widthRatio = (float)x_size / (float)texture.getTextureWidth();
                    
                GL11.glTexCoord2f(0, 0);
                GL11.glVertex2f(x_min + x_offset, y_min + y_offset);
                GL11.glTexCoord2f(0, heightRatio);
                GL11.glVertex2f(x_min + x_offset, y_min + y_size + y_offset);
                GL11.glTexCoord2f(widthRatio, heightRatio);
                GL11.glVertex2f(x_min + x_size + x_offset, y_min + y_size + y_offset);
                GL11.glTexCoord2f(widthRatio, 0);
                GL11.glVertex2f(x_min + x_size + x_offset, y_min + y_offset);
                    
            }else{
                GL11.glTexCoord2f(0, 0);
                GL11.glVertex2f(0, 0);
                GL11.glTexCoord2f(0, heightRatio);
                GL11.glVertex2f(0, adjustedHeight);
                GL11.glTexCoord2f(widthRatio, heightRatio);
                GL11.glVertex2f(adjustedWidth,adjustedHeight);
                GL11.glTexCoord2f(widthRatio, 0);
                GL11.glVertex2f(adjustedWidth,0);
                    
            }
	}
	GL11.glEnd();
		
	// restore the model view matrix to prevent contamination
	GL11.glPopMatrix();
        
    }
}
