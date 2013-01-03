package OperationHotHammer.Display;

import OperationHotHammer.Core.Util.Debugging;
import java.io.IOException;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.spaceinvaders.lwjgl.Texture;
import org.newdawn.spaceinvaders.lwjgl.TextureLoader;

public class Sprite implements ISprite {
    public final Texture texture;
    
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
		
	// draw a quad textured to match the sprite
    	GL11.glBegin(GL11.GL_QUADS);
	{
            GL11.glTexCoord2f(0, 0);
	    GL11.glVertex2f(0, 0);
	    GL11.glTexCoord2f(0, texture.getHeight());
	    GL11.glVertex2f(0, texture.getImageHeight());
	    GL11.glTexCoord2f(texture.getWidth(), texture.getHeight());
	    GL11.glVertex2f(texture.getImageWidth(),texture.getImageHeight());
	    GL11.glTexCoord2f(texture.getWidth(), 0);
	    GL11.glVertex2f(texture.getImageWidth(),0);
	}
	GL11.glEnd();
		
	// restore the model view matrix to prevent contamination
	GL11.glPopMatrix();
        
    }
}
