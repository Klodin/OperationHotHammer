
package OperationHotHammer.Display;

import OperationHotHammer.Core.Interfaces.IObserver;
import OperationHotHammer.Core.Interfaces.IObservee;
import OperationHotHammer.Core.Game;
import OperationHotHammer.Core.GameObjects.Entities.SimpleCreature;
import OperationHotHammer.Core.GameObjects.Entity;
import OperationHotHammer.Core.Scene;
import OperationHotHammer.Core.Util.Debugging.Debugging;
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
    
    private int minWidth = 100;
    private int minHeight = 100;
    
    private int windowedWidth = 800;
    private int windowedHeight = 800;
    
    private int fullscreenWidth = 1920;
    private int fullscreenHeight = 1080;
    
    private int FPS = 0;
    private int prevFPS = 0;
    private boolean fullscreen = false;

    
    public void initialize() {
        
        Debugging.INSTANCE.showMessage("Initializing (GameWindow)");
        
        startRendering();
        
        Game.INSTANCE.initialize();
             
        Scene scene = new Scene("Testing Scene", 500, 500);
        
        Entity e;
        Sprite s;
        
        float xx = (int)(scene.getWidth()/Settings.GRID_SPACE_SIZE); //round down, most we can fit with grid spacing 
        float yy = (int)(scene.getHeight()/Settings.GRID_SPACE_SIZE); 
        float w = scene.getWidth()/xx; //adjusted grid space width, to maximize scene space
        float h = scene.getHeight()/yy; //adjusted grid space height, to maximize scene space
        
        Debugging.INSTANCE.beginGroup("Initializing (Scene '" + scene.getName() + "')");
        Debugging.INSTANCE.beginGroup("Populating Entites");
        for(int x = 0; x < xx; x++)
        for(int y = 0; y < yy; y++) {
            s = new Sprite("OperationHotHammer/Assets/link.gif", Sprite.TEXTURE_STRETCH, true);
            e = new SimpleCreature(w*x+w/2,h*y+h/2); //center them on their respective squares
            e.attach(s);
            scene.addEntity(e);
        }
        Debugging.INSTANCE.finishGroup();
        
        Debugging.INSTANCE.showMessage("Setting Background");
        s = new Sprite("OperationHotHammer/Assets/valley2.png", Sprite.TEXTURE_TILED);
        scene.attach(new Background(s, 0.05f));
        
        s = new Sprite("OperationHotHammer/Assets/cloudsbg.png", Sprite.TEXTURE_TILED);
        scene.attach(new Background(s, 0.5f));
        
        Debugging.INSTANCE.finishGroup();
        
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
            Game.INSTANCE.loadScene(scene);
        }
    }
    
    public void draw(){
        // clear screen
	GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	GL11.glMatrixMode(GL11.GL_MODELVIEW);
	GL11.glLoadIdentity();
        
        //Cry "Havoc!" and let slip the dogs of war,
        Game.INSTANCE.draw(getWidth(), getHeight());
        Hud.INSTANCE.draw(getWidth(), getHeight());
                
        // update window contents
	Display.update();
        
        // cap fps to 60fps
        Display.sync(Settings.FRAME_RATE_SECONDS);
        
    }
    
    public void update(float delta){
        Game.INSTANCE.update(delta);
        
        Hud.INSTANCE.set("FPS", String.valueOf(getFPS()));
        Hud.INSTANCE.set("Entities", String.valueOf(Entity.getUpdateCount()));
        Hud.INSTANCE.set("Drawn Sprites", String.valueOf(Sprite.getDrawnCount()));
        
        if(Display.wasResized() && !fullscreen) {
            setResolution(Display.getWidth(),Display.getHeight());
            initializeGlDisplay();
        }
        
        Hud.INSTANCE.set("Dimensions", String.valueOf(Display.getWidth()) + "x" + String.valueOf(Display.getHeight()));
        
        Sprite.clearCounts();
        Entity.clearCounts();
    }
    
    /**
     * Set the display mode to be used 
     * 
     * @param width The width of the display required
     * @param height The height of the display required
     * @param fullscreen True if we want fullscreen mode
     */
    public void setDisplayMode(int width, int height, boolean fullscreen) {

        // return if requested DisplayMode is already set
        if ((Display.getDisplayMode().getWidth() == width) && 
            (Display.getDisplayMode().getHeight() == height) && 
            (Display.isFullscreen() == fullscreen)) {
                return;
        }

        try {
            DisplayMode targetDisplayMode = null;

            if (fullscreen) {
                DisplayMode[] modes = Display.getAvailableDisplayModes();
                int freq = 0;

                for (int i=0;i<modes.length;i++) {
                    DisplayMode current = modes[i];

                    if ((current.getWidth() == width) && (current.getHeight() == height)) {
                        if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
                            if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
                                targetDisplayMode = current;
                                freq = targetDisplayMode.getFrequency();
                            }
                        }

                        // if we've found a match for bpp and frequence against the 
                        // original display mode then it's probably best to go for this one
                        // since it's most likely compatible with the monitor
                        if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) &&
                            (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
                                targetDisplayMode = current;
                                break;
                        }
                    }
                }
            } else {
                targetDisplayMode = new DisplayMode(width,height);
            }

            if (targetDisplayMode == null) {
                Debugging.INSTANCE.showWarning("Failed to find value mode: "+width+"x"+height+" fs="+fullscreen);
                return;
            }

            Display.setDisplayMode(targetDisplayMode);
            Display.setFullscreen(fullscreen);
            
            if(!Display.isCreated())
                Display.create();

        } catch (LWJGLException e) {
            if(Display.isCreated())
                Debugging.INSTANCE.showWarning("Unable to setup mode "+width+"x"+height+" fullscreen="+fullscreen + e);
            else
                Debugging.INSTANCE.showError("Unable to setup mode "+width+"x"+height+" fullscreen="+fullscreen + e);
        }
    }
        
    
    public void toggleFullscreen() {
        setDisplayMode(!fullscreen);
    }
    
    public void setDisplayMode(boolean fullscreen) {
        this.fullscreen = fullscreen;  
        
        //if windowed I gotta do this first *shrug*
        if(!this.fullscreen)
            Display.setResizable(allowResizing());
        
        
        setDisplayMode(getWidth(), getHeight(), fullscreen);
        
        //something went wrong D:
        if(this.fullscreen && !Display.isFullscreen()) {
            this.fullscreen = !this.fullscreen; //whatever windowed mode is cooler anyways
            Display.setResizable(allowResizing()); //resizing is always better
        }
        
        //hide the mouse if fullscreen
        Mouse.setGrabbed(!allowMouse());

        //we need vsync on in fullscreen or visual tearing is seen
        Display.setVSyncEnabled(allowVSync()); 
        
        //setup opengl display settings, this is here mostly to make sure the width and height are set properly
        initializeGlDisplay();
    }
        
    public boolean allowMouse(){
        return !fullscreen;
    }
    
    public boolean allowVSync(){
        return fullscreen;
    }
    
    public boolean allowResizing(){
        return !fullscreen;
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
    
    public void setResolution(int width, int height) {
	if(fullscreen){
            fullscreenWidth = width;
            fullscreenHeight = height;
        }else{
            windowedWidth = width;
            windowedHeight = height;
        }
    }
    
    public int getWidth() {
        return Math.max(fullscreen?fullscreenWidth:windowedWidth, minWidth);
    }

    public int getHeight() {
        return Math.max(fullscreen?fullscreenHeight:windowedHeight, minHeight);
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
        setTitle("Awesome Secret Game");
            
        setDisplayMode(fullscreen = false);
  
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
    }
    
    public void initializeGlDisplay(){
        GL11.glViewport(0,0,getWidth(),getHeight());
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, getWidth(),getHeight(), 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);    
    }
} 
