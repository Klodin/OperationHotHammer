
package OperationHotHammer.Display;

import OperationHotHammer.Core.Interfaces.IObserver;
import OperationHotHammer.Core.Interfaces.IObservee;
import OperationHotHammer.Core.Game;
import OperationHotHammer.Core.GameObjects.Entities.SimpleCreature;
import OperationHotHammer.Core.GameObjects.Entity;
import OperationHotHammer.Core.Scene;
import OperationHotHammer.Core.Util.Debugging;
import OperationHotHammer.Core.Util.Settings;
import OperationHotHammer.Display.Text.Text;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

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
        
        Debugging.INSTANCE.showMessage("Initializing: GameWindow.java");
        
        int resolutionWidth = 1366;
        int resolutionHeight = 768;
        
        setResolution(resolutionWidth,resolutionHeight);
        
        startRendering();
        
        Game.INSTANCE.initialize();
        
        
        Debugging.INSTANCE.showMessage("Creating Testing Scene");
        
        Scene scene = new Scene("Green Valley", 500, 500);
        
        Entity e;
        Sprite s;
        
        float xx = (int)(scene.getWidth()/Settings.GRID_SPACE_SIZE); //round down, most we can fit with grid spacing 
        float yy = (int)(scene.getHeight()/Settings.GRID_SPACE_SIZE); 
        float w = scene.getWidth()/xx; //adjusted grid space width, to maximize scene space
        float h = scene.getHeight()/yy; //adjusted grid space height, to maximize scene space
        
        Debugging.INSTANCE.showMessage("Populating Testing Scene");
        for(int x = 0; x < xx; x++)
        for(int y = 0; y < yy; y++) {
            s = new Sprite("OperationHotHammer/Assets/link.gif", Sprite.TEXTURE_STRETCH, true);
            e = new SimpleCreature(w*x+w/2,h*y+h/2); //center them on their respective squares
            e.attach(s);
            scene.addEntity(e);
        }        
        
        s = new Sprite("OperationHotHammer/Assets/valley2.png", Sprite.TEXTURE_TILED);
        scene.attach(new Background(s, 0.05f));
        
        s = new Sprite("OperationHotHammer/Assets/cloudsbg.png", Sprite.TEXTURE_TILED);
        scene.attach(new Background(s, 0.5f));
        
        /*
        int num = 20;
        for(int _x = 0; _x <= num*40; _x++){
        for(int _y = 0; _y <= num*0.7f; _y++) {
            
            s = new Sprite("OperationHotHammer/Assets/Terrain/grass.png");
            e = new SimpleCreature((scene.getWidth()/(num*2))*_x-scene.getWidth()/2,(scene.getHeight()/((int)num*0.7f))*_y);
            e.attach(s);
            scene.addEntity(e);
            
        }}
        */
        
        if(!Debugging.INSTANCE.hasError()) {
            Debugging.INSTANCE.showMessage("Loading Testing Scene");
            Game.INSTANCE.loadScene(scene);
        }
    }
    
    public void draw(float delta){
        
        // clear screen
	GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	GL11.glMatrixMode(GL11.GL_MODELVIEW);
	GL11.glLoadIdentity();
        
        Game.INSTANCE.draw(getScreenWidth(), getScreenHeight());
        Hud.INSTANCE.draw(getScreenWidth(), getScreenHeight());
                
        // update window contents
	Display.update();
        Display.sync(Settings.FRAME_RATE_SECONDS); // cap fps to 60fps
        
    }
    
    public void update(float delta){
        Hud.INSTANCE.set("FPS", String.valueOf(getFPS()));
        Hud.INSTANCE.set("Entities", String.valueOf(Entity.getUpdateCount()));
        Hud.INSTANCE.set("Drawn Sprites", String.valueOf(Sprite.getDrawnCount()));
        Sprite.clearCounts();
        Entity.clearCounts();
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
			
            GL11.glShadeModel(GL11.GL_SMOOTH);
            GL11.glDisable(GL11.GL_LIGHTING);
            
            GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            GL11.glClearDepth(1);
            
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            
            GL11.glViewport(0,0,width,height);
            
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
            GL11.glOrtho(0, width, height, 0, 1, -1);
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
			
            //if(callback != null) {
            //    callback.initialise();
            //}
        } catch (LWJGLException le) {
            //callback.windowClosed();
	}
    }
} 
