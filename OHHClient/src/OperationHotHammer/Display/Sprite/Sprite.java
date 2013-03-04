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
    }
    
    @Override
    public ISprite setWidth(int w) {
        texture.setDrawWidth(w);
        return this;
    }
    
    @Override
    public ISprite setHeight(int h) {
        texture.setDrawHeight(h);
        return this;
    }
    
    @Override
    public int getWidth() {
        return (int)texture.getDrawWidth();
    }
    
    @Override
    public int getHeight() {
        return (int)texture.getDrawHeight();
    }
    
    @Override
    public Texture getTexture(){
        return texture;
    }
    
    @Override
    public void update(float delta, IEntity e) {
        updateCount++;
    }
    
    @Override
    public void draw(Vector3f position) {
        
        drawnCount++;
        
        // store the current model matrix
	GL11.glPushMatrix();
		
	// bind to the appropriate texture for this sprite
	texture.bind();
    
	// translate to the right location and prepare to draw
	GL11.glTranslatef(position.x-texture.getDrawWidth()/2, position.y-texture.getDrawHeight()/2, 0);		
    	GL11.glColor3f(1f, 1f, 1f);

	// draw a quad textured to match the sprite
    	GL11.glBegin(GL11.GL_QUADS);
	{
            if((drawStyle & ITexture.TILED) != 0) {
                if(texture.getOffsetX() != 0 || texture.getOffsetY() != 0) {
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

                    float ratioY = (float)y_size / (float)texture.getTextureHeight();
                    float ratioX = (float)x_size / (float)texture.getTextureWidth();
                    
                    GL11.glTexCoord2f(0, 0);
                    GL11.glVertex2f(x_min + x_offset, y_min + y_offset);
                    GL11.glTexCoord2f(0, ratioY);
                    GL11.glVertex2f(x_min + x_offset, y_min + y_size + y_offset);
                    GL11.glTexCoord2f(ratioX, ratioY);
                    GL11.glVertex2f(x_min + x_size + x_offset, y_min + y_size + y_offset);
                    GL11.glTexCoord2f(ratioX, 0);
                    GL11.glVertex2f(x_min + x_size + x_offset, y_min + y_offset);
                }else{
                    //repeat the texture it within the quad's dimensions..
                    GL11.glTexCoord2f(0, 0);
                    GL11.glVertex2f(0, 0);
                    GL11.glTexCoord2f(0, texture.getHeightRatio());
                    GL11.glVertex2f(0, texture.getDrawHeight());
                    GL11.glTexCoord2f(texture.getWidthRatio(), texture.getHeightRatio());
                    GL11.glVertex2f(texture.getDrawWidth(),texture.getDrawHeight());
                    GL11.glTexCoord2f(texture.getWidthRatio(), 0);
                    GL11.glVertex2f(texture.getDrawWidth(),0);
                }
            }else if((drawStyle & ITexture.STRETCH) != 0) {
                //stretch the texture to fill the quad without repeating
                
                GL11.glTexCoord2f(0, 0);
                GL11.glVertex2f(0, 0);
                GL11.glTexCoord2f(0, 1.0f);
                GL11.glVertex2f(0, texture.getDrawHeight(drawStyle));
                GL11.glTexCoord2f(1.0f,1.0f);
                GL11.glVertex2f(texture.getDrawWidth(drawStyle),texture.getDrawHeight(drawStyle));
                GL11.glTexCoord2f(1.0f, 0);
                GL11.glVertex2f(texture.getDrawWidth(drawStyle),0);
            }
	}
	GL11.glEnd();
		
	// restore the model view matrix to prevent contamination
	GL11.glPopMatrix();
        
    }
}
