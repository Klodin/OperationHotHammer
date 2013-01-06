package OperationHotHammer.Display;

import OperationHotHammer.Core.Util.Debugging;
import java.io.IOException;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class Sprite implements ISprite {
    public final static int TILED = 1;
    public final static int STRETCH = 2;
    
    public final Texture texture;
    private int drawStyle = STRETCH;
    
    public Sprite(String resource) {
        Texture temp = null;
        try{
            temp = TextureLoader.INSTANCE.getTexture(resource);
        }catch(IOException e){
            Debugging.INSTANCE.showError("A problem occured when loading a texture! (" + resource + ")");            
        }
        texture = temp;
    }
    
    public void setWidth(int w) {
        texture.setWidth(w);
    }
    
    public void setHeight(int h) {
        texture.setHeight(h);
    }
    
    @Override
    public void draw(Vector3f position) {
        
        // store the current model matrix
	GL11.glPushMatrix();
		
	// bind to the appropriate texture for this sprite
	texture.bind();
    
	// translate to the right location and prepare to draw
	GL11.glTranslatef(position.x-texture.getImageWidth()/2, position.y-texture.getImageHeight()/2, 0);		
    	GL11.glColor3f(1,1,1);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

	// draw a quad textured to match the sprite
    	GL11.glBegin(GL11.GL_QUADS);
	{
            if(drawStyle == TILED) {
                GL11.glTexCoord2f(0, 0);
                GL11.glVertex2f(0, 0);
                GL11.glTexCoord2f(0, texture.getHeight());
                GL11.glVertex2f(0, texture.getImageHeight());
                GL11.glTexCoord2f(texture.getWidth(), texture.getHeight());
                GL11.glVertex2f(texture.getImageWidth(),texture.getImageHeight());
                GL11.glTexCoord2f(texture.getWidth(), 0);
                GL11.glVertex2f(texture.getImageWidth(),0);
            }else if(drawStyle == STRETCH) {
                GL11.glTexCoord2f(0, 0);
                GL11.glVertex2f(0, 0);
                GL11.glTexCoord2f(0, 1.0f);
                GL11.glVertex2f(0, texture.getImageHeight());
                GL11.glTexCoord2f(1.0f,1.0f);
                GL11.glVertex2f(texture.getImageWidth(),texture.getImageHeight());
                GL11.glTexCoord2f(1.0f, 0);
                GL11.glVertex2f(texture.getImageWidth(),0);
            }
	}
	GL11.glEnd();
        
        GL11.glDisable(GL11.GL_BLEND);
		
	// restore the model view matrix to prevent contamination
	GL11.glPopMatrix();
        
    }
}
