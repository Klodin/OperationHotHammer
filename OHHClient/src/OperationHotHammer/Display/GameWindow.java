
package OperationHotHammer.Display;

import OperationHotHammer.Core.Interfaces.IObserver;
import OperationHotHammer.Core.Interfaces.IObservee;
import OperationHotHammer.Core.Game;
import OperationHotHammer.Core.GameObjects.Entities.SimpleCreature;
import OperationHotHammer.Core.GameObjects.Entity;
import OperationHotHammer.Core.Scene;
import OperationHotHammer.Core.Util.Debugging;
import OperationHotHammer.Core.Util.Settings;
import java.util.ArrayList;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public enum GameWindow implements IObserver{
    INSTANCE;
    
    ArrayList<IObservee> observees = new ArrayList<>();  
    private String title;
    private int width;
    private int height;
    private int FPS = 0;
    private int prevFPS = 0;
    private boolean fullscreen = false;
    
    public void initialize() {
        
        int resolutionWidth = 1366;
        int resolutionHeight = 768;
        
        setResolution(resolutionWidth,resolutionHeight);
        
        startRendering();
        
        Game.INSTANCE.initialize();
        
        Scene scene = new Scene("Green Valley", 3000, 2000);
        Entity e;
        Sprite s;
        
        int num = 20;
        for(int _x = 0; _x <= num*40; _x++){
        for(int _y = 0; _y <= num*0.7f; _y++) {
            
            s = new Sprite("OperationHotHammer/Assets/smile.png");
            e = new SimpleCreature((scene.width/(num*2))*_x-scene.width/2,(scene.height/((int)num*0.7f))*_y);
            e.attach(s);
            scene.addEntity(e);
            
        }}
        
        Game.INSTANCE.loadScene(scene);
    }
    
    public void draw(float delta){
        
        // clear screen
	GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	GL11.glMatrixMode(GL11.GL_MODELVIEW);
	GL11.glLoadIdentity();
                        
        //for(IObservee o : observees){
        //    o.draw(Game.INSTANCE.draw, drawRadius);
        //}
        
        Game.INSTANCE.draw(getScreenWidth(), getScreenHeight());
        
        // update window contents
	Display.update();
        Display.sync(Settings.FRAME_RATE_SECONDS); // cap fps to 60fps
        
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
            
            

            return true;
		
        } catch (Exception e) {
            
            e.printStackTrace();
            Debugging.INSTANCE.showWarning("Unable to enter fullscreen, continuing in windowed mode.");
            
	}

	return false;
    }
        
    
    public void switchMode() {
        fullscreen = !fullscreen;
        try {
            Display.setFullscreen(fullscreen);
            Mouse.setGrabbed(fullscreen);
            Display.setVSyncEnabled(fullscreen);
        }
        catch(Exception e) {
            
            e.printStackTrace();
            Debugging.INSTANCE.showWarning("Unable to enter fullscreen, continuing in windowed mode.");
            
        }
    }
        
    public void setTitle(String title) {
	this.title = title;
	if(Display.isCreated()) {
            Display.setTitle(title);
	}
    }
    
    public void setFPS(int val) {
        prevFPS = FPS;
        FPS = val;
    }
    
    public int getFPS() {
        return (int)(((float)FPS)*0.9 + ((float)prevFPS)*0.1);
    }
    
    public void setResolution(int x, int y) {
	width = x;
	height = y;
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
    
    @Override
    public void addObservee(IObservee rl) {
        observees.add(rl);
    }
    
    /**
    * Start the rendering process. This method will cause the display to redraw
    * as fast as possible.
    */
    public void startRendering() {
        try {                
            setDisplayMode();
            Display.create();
            setTitle("Awesome Secret Game");
            
            // grab the mouse, dont want that hideous cursor when we're playing!
            Mouse.setGrabbed(fullscreen);
            Display.setFullscreen(fullscreen);
  
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
} 
