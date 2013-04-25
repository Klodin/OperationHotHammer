package OHH.Display.Sprite;

import OHH.Core.GameObjects.Boundary.Circle;
import OHH.Core.GameObjects.Boundary.IBoundaryShape;
import OHH.Core.Interfaces.IEntity;
import OHH.Core.Interfaces.ISprite;
import OHH.Core.Interfaces.ITexture;
import OHH.Core.Util.Color4f;
import OHH.Core.Util.Debugging.Debugging;
import OHH.Display.Sprite.Animation.SpriteBehaviour;
import OHH.Display.Sprite.Texture.Texture;
import OHH.Display.Sprite.Texture.TextureLoader;
import java.io.IOException;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class Sprite implements ISprite {
    
    private final Circle displayBoundary = new Circle(0);
    
    private Texture texture;
    private int drawStyle;
    
    private int adjustedWidth = 0;
    private int adjustedHeight = 0;
    private int actualWidth = 0;
    private int actualHeight = 0;
    
    private SpriteBehaviour runnable = null;
    
    private Color4f color = new Color4f(1f, 1f, 1f, 1f);
    
    public Sprite(String resource) {
        init(resource, ITexture.TILED, null);
    }
    
    public Sprite(String resource, int drawStyle) {
        init(resource, drawStyle, null);
    }
       
    public Sprite(String resource, SpriteBehaviour runnable) {
        init(resource, ITexture.TILED, runnable);
    }
    
    public Sprite(String resource, int drawStyle, SpriteBehaviour runnable) {
        init(resource, drawStyle, runnable);
    }
    
    private void init(String resource, int drawStyle, SpriteBehaviour runnable){
        Texture temp = null;
        this.drawStyle = drawStyle;
        this.runnable = runnable;
        
        try{
            temp = TextureLoader.INSTANCE.getTexture(resource);
        }catch(IOException e){
            Debugging.INSTANCE.showWarning("A problem occured when loading a texture! (" + resource + ")");            
        }
        this.texture = temp;
        
        if(texture != null) {
            adjustedWidth = actualWidth = texture.getWidth();
            adjustedHeight = actualHeight = texture.getHeight();
            updateDisplayBoundary();
        }
    }
    
    @Override
    public void setWidth(int w) {
        if(w == actualWidth)
            return;
        
        adjustedWidth = actualWidth = w;
        
        if(isMaintainingAspectMin() || isMaintainingAspectMax()) {
            float scale = Math.min((float)actualWidth/(float)texture.getOriginalWidth(), (float)actualHeight/(float)texture.getOriginalHeight());
            adjustedWidth = (int)((float)texture.getOriginalWidth() * scale);
            adjustedHeight = (int)((float)texture.getOriginalHeight() * scale);
        }
        
        updateDisplayBoundary();
    }
    
    @Override
    public void setHeight(int h) {
        if(h == actualHeight)
            return;
        
        adjustedHeight = actualHeight = h;
        
        if(isMaintainingAspectMin() || isMaintainingAspectMax()) {
            float scale = Math.min((float)actualWidth/(float)texture.getOriginalWidth(), (float)actualHeight/(float)texture.getOriginalHeight());
            adjustedWidth = (int)((float)texture.getOriginalWidth() * scale);
            adjustedHeight = (int)((float)texture.getOriginalHeight() * scale);
        }
        
        updateDisplayBoundary();
    }
    
    @Override
    public Sprite resize(int w, int h){
        setWidth(w);
        setHeight(h);
        return this;
    }
    
    private void updateDisplayBoundary(){
        int max = Math.max(adjustedWidth, adjustedHeight);
        displayBoundary.radius = (float)Math.sqrt(max*max*2)/2f;
    }
    
    @Override
    public IBoundaryShape getBoundary(){
        return displayBoundary;
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
        //run it
        if(runnable != null){
            runnable.run(delta, this);
        }
    }
    
    public boolean isTiled(){
        return (drawStyle & ITexture.TILED) != 0;
    }
    
    public boolean isStretched(){
        return (drawStyle & ITexture.STRETCH) != 0;
    }
    
    public boolean isCentered(){
        return (drawStyle & ITexture.CENTERED) != 0;
    }
    
    public boolean isMaintainingAspectMin(){
        return (drawStyle & ITexture.MAINTAIN_ASPECT_MAX) != 0;
    }
    
    public boolean isMaintainingAspectMax(){
        return (drawStyle & ITexture.MAINTAIN_ASPECT_MIN) != 0;
    }
    
    @Override
    public void draw(Vector3f position) {
        if(texture == null) {
            Debugging.INSTANCE.showWarning("Attempted to draw a texture that is NULL!");
            return;
        }
        
        drawQuad(position, true);
    }
    
    public void drawQuad(Vector3f position, boolean bindTexture) {
        // store the current model matrix
	GL11.glPushMatrix();
		
	// bind to the appropriate texture for this sprite
	if(bindTexture) texture.bind();
        if(bindTexture) color.bind();
    
	// translate to the right location and prepare to draw
        if(isTiled()) {
            GL11.glTranslatef(position.x-getWidth()/2, position.y-getHeight()/2, 0);	
        } else { 
            GL11.glTranslatef(position.x-adjustedWidth/2, position.y-adjustedHeight/2, 0);		
        }

	// draw a quad textured to match the sprite
    	GL11.glBegin(GL11.GL_QUADS);
	{
            
            float heightRatio = 1f; //1f would match the texture to the quad's dimensions
            float widthRatio = 1f;  //1f would match the texture to the quad's dimensions
            
            if(isTiled()) {
                heightRatio = (float)getHeight() / (float)texture.getHeight();
                widthRatio = (float)getWidth() / (float)texture.getWidth();
            }
            
            if(isTiled() && (texture.getOffsetX() != 0 || texture.getOffsetY() != 0)) {
                //an offset has been set: so render this texture within the quad's dimensions, +2 extra texture width +2 extra height.  Then adjust by the offset amounts
                    
                //offset amounts to adjust by
                int x_offset = (int)texture.getOffsetX();
                int y_offset = (int)texture.getOffsetY();

                //min amount texture must be rendered shifted off screen (1 texture width & height)
                int x_min = -texture.getWidth();
                int y_min = -texture.getHeight();

                //multipliers: amount of texture repeats required to completly fill the quad (rounded up)
                int x_mul = (int)Math.ceil((float)getWidth()/(float)texture.getWidth())+2;
                int y_mul = (int)Math.ceil((float)getHeight()/(float)texture.getHeight())+2;
                    
                int x_size = texture.getWidth()*x_mul;
                int y_size = texture.getHeight()*y_mul;

                heightRatio = (float)y_size / (float)texture.getHeight();
                widthRatio = (float)x_size / (float)texture.getWidth();
                    
                GL11.glTexCoord2f(0, 0);
                GL11.glVertex2f(x_min + x_offset, y_min + y_offset);
                GL11.glTexCoord2f(0, heightRatio);
                GL11.glVertex2f(x_min + x_offset, y_min + y_size + y_offset);
                GL11.glTexCoord2f(widthRatio, heightRatio);
                GL11.glVertex2f(x_min + x_size + x_offset, y_min + y_size + y_offset);
                GL11.glTexCoord2f(widthRatio, 0);
                GL11.glVertex2f(x_min + x_size + x_offset, y_min + y_offset);
                    
            }else if(isStretched() || isTiled()){
                
                GL11.glTexCoord2f(0, 0);
                GL11.glVertex2f(0, 0);
                GL11.glTexCoord2f(0, heightRatio);
                GL11.glVertex2f(0, adjustedHeight);
                GL11.glTexCoord2f(widthRatio, heightRatio);
                GL11.glVertex2f(adjustedWidth,adjustedHeight);
                GL11.glTexCoord2f(widthRatio, 0);
                GL11.glVertex2f(adjustedWidth,0);
                    
            }else{ //centered
                
                float xdif = ((float)actualWidth - (float)getTexture().getWidth()) / 2f;
                float ydif = ((float)actualHeight - (float)getTexture().getHeight()) / 2f;
                
                GL11.glTexCoord2f(0, 0);
                GL11.glVertex2f(xdif, ydif);
                GL11.glTexCoord2f(0, heightRatio);
                GL11.glVertex2f(xdif, ydif + getTexture().getHeight());
                GL11.glTexCoord2f(widthRatio, heightRatio);
                GL11.glVertex2f(xdif + getTexture().getWidth(), ydif + getTexture().getHeight());
                GL11.glTexCoord2f(widthRatio, 0);
                GL11.glVertex2f(xdif + getTexture().getWidth(), ydif);
            }
	}
	GL11.glEnd();
		
	// restore the model view matrix to prevent contamination
	GL11.glPopMatrix();
        
    }
    
    @Override
    public void drawWireframe(Vector3f position){
        
        GL11.glPolygonMode( GL11.GL_FRONT, GL11.GL_LINE );
        
        //clear any texture bindings
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        
        /*
         * I draw the quads here instead of using the draw function since
         * the draw function does a lot of adjustments for texture.
         * This is the set quad dimensions shown visually.
         */
        
        //show the quad
        GL11.glColor3f(0.3f, 0f, 1f);
        GL11.glLineWidth(1.0f);
        
        GL11.glPushMatrix();
        GL11.glTranslatef(position.x-(float)getWidth()/2f, position.y-(float)getHeight()/2f, 0);
        
        GL11.glBegin(GL11.GL_QUADS);
	{
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex2f(0, 0);
            GL11.glTexCoord2f(0, 1);
            GL11.glVertex2f(0, getHeight());
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex2f(getWidth(), getHeight());
            GL11.glTexCoord2f(1, 0);
            GL11.glVertex2f(getWidth(), 0);
        }
        GL11.glEnd();
        
        GL11.glPopMatrix();
        
        /*
        //dashed line
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT); 
        GL11.glLineStipple(1, (short)0x1C47);
        GL11.glEnable(GL11.GL_LINE_STIPPLE);
        
        //show the textures
        //the original draw function accuratly shows the positions of textures
        GL11.glColor3f(0.4f, 0.2f, 1f);
        drawQuad(position, false);
        
        //remove dashed line
        GL11.glPopAttrib();
        * */
        
        GL11.glPolygonMode( GL11.GL_FRONT, GL11.GL_FILL );
        
        
    }
    
    @Override
    public void drawFilled(Vector3f position, Color4f colour){

        //clear any texture bindings
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        
        /*
         * I draw the quads here instead of using the draw function since
         * the draw function does a lot of adjustments for texture.
         * This is the set quad dimensions shown visually.
         */
        
        //show the quad
        colour.bind();
        
        GL11.glPushMatrix();
        GL11.glTranslatef(position.x-(float)getWidth()/2f, position.y-(float)getHeight()/2f, 0);
        
        GL11.glBegin(GL11.GL_QUADS);
	{
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex2f(0, 0);
            GL11.glTexCoord2f(0, 1);
            GL11.glVertex2f(0, getHeight());
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex2f(getWidth(), getHeight());
            GL11.glTexCoord2f(1, 0);
            GL11.glVertex2f(getWidth(), 0);
        }
        GL11.glEnd();
        
        GL11.glPopMatrix();

    }
}
