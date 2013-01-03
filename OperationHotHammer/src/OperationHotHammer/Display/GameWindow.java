
package OperationHotHammer.Display;

import OperationHotHammer.Core.Game;
import OperationHotHammer.Core.Util.Debugging;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public enum GameWindow implements IObserver{
    INSTANCE;
    
    ArrayList<IObservee> observees = new ArrayList<>();

    @Override
    public void addObservee(IObservee rl) {
        observees.add(rl);
    }
  
    private String title;
    private int width;
    private int height;
    private int FPS = 0;
    
    
    public void initialize() {
     
        setResolution(1366,768);
        startRendering();
        Game.INSTANCE.initialize();
        addObservee(Game.INSTANCE);
        
    }
    
    public void draw(float delta){
        // clear screen
	GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	GL11.glMatrixMode(GL11.GL_MODELVIEW);
	GL11.glLoadIdentity();
                        
        for(IObservee o : observees){
            o.update(delta);
        }
        for(IObservee o : observees){
            o.render();
        }

        
        // update window contents
	Display.update();
        
        if(Display.isCloseRequested() || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            Game.INSTANCE.shutdown();
            Display.destroy();
            //callback.windowClosed();
	}
    }
    
    /**
    * Sets the display mode for fullscreen mode
    */
    private boolean setDisplayMode() {
	try {
            
            // get modes
            DisplayMode[] dm = org.lwjgl.util.Display.getAvailableDisplayModes(width, height, -1, -1, -1, -1, 60, 60);
            org.lwjgl.util.Display.setDisplayMode(dm, new String[] {
		"width=" + width,
		"height=" + height,
		"freq=" + 60,
		"bpp=" + org.lwjgl.opengl.Display.getDisplayMode().getBitsPerPixel() 
            });
            
            Display.setFullscreen(true);

            return true;
		
        } catch (Exception e) {
            
            e.printStackTrace();
            Debugging.INSTANCE.showWarning("Unable to enter fullscreen, continuing in windowed mode.");
            
	}

	return false;
    }
        
    public void setTitle(String title) {
	this.title = title;
	if(Display.isCreated()) {
            Display.setTitle(title);
	}
    }
    
    public void setFPS(int val) {
        FPS = val;
    }
    
    public void setResolution(int x, int y) {
	width = x;
	height = y;
    }
    
    public float getX() {
        return Game.INSTANCE.getX();
    }

    public float getY() {
        return Game.INSTANCE.getY();
    }

    public int getScreenWidth() {
        return width;
    }

    public int getScreenHeight() {
        return height;
    }
    
    public boolean isRunning() {
        return Game.INSTANCE.isRunning();
    }
    
    /**
    * Start the rendering process. This method will cause the display to redraw
    * as fast as possible.
    */
    public void startRendering() {
        try {                
            setDisplayMode();
            Display.create();
			
            // grab the mouse, dont want that hideous cursor when we're playing!
            Mouse.setGrabbed(true);
  
            // enable textures since we're going to use these for our sprites
            GL11.glEnable(GL11.GL_TEXTURE_2D);
			
            // disable the OpenGL depth test since we're rendering 2D graphics
            GL11.glDisable(GL11.GL_DEPTH_TEST);
			
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
			
            GL11.glOrtho(0, width, height, 0, -1, 1);
			
            //if(callback != null) {
            //    callback.initialise();
            //}
        } catch (LWJGLException le) {
            //callback.windowClosed();
	}
    }
    
    public boolean isKeyPressed(int keyCode) {
        // apparently, someone at decided not to use standard 
	// keycode, so we have to map them over:
	switch(keyCode) {
	case KeyEvent.VK_SPACE:
		keyCode = Keyboard.KEY_SPACE;
		break;
	case KeyEvent.VK_LEFT:
		keyCode = Keyboard.KEY_LEFT;
		break;
	case KeyEvent.VK_RIGHT:
		keyCode = Keyboard.KEY_RIGHT;
		break;
	}    
		
	return org.lwjgl.input.Keyboard.isKeyDown(keyCode);
}
} 
