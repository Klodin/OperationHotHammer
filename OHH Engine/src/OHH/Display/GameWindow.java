
package OHH.Display;

import OHH.Display.HUD.Hud;
import OHH.Display.Sprite.Sprite;
import OHH.Core.Game;
import OHH.Core.GameObjects.Entities.Terrain;
import OHH.Core.GameObjects.Entities.SimpleEntity;
import OHH.Core.GameObjects.Entity;
import OHH.Core.Interfaces.IScenery;
import OHH.Core.Interfaces.ITexture;
import OHH.Core.Scene;
import OHH.Core.Util.Debugging.Debugging;
import OHH.Core.Util.Settings;
import OHH.Display.Sprite.Animation.AnimatedSprite;
import OHH.Display.Sprite.Animation.TextureScrollBehaviour;
import OHH.Display.Sprite.Scenery;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public enum GameWindow{
    INSTANCE;
    
    private String title;
    
    private int minWidth = 100;
    private int minHeight = 100;
    
    private int windowedWidth = 800;
    private int windowedHeight = 800;
    
    private int fullscreenWidth = 0;
    private int fullscreenHeight = 0;

    private boolean fullscreen = false;
    private boolean debugging = false;
    
    public void initialize() {
        
        Debugging.INSTANCE.showMessage("Initializing (GameWindow)");
        
        fullscreenWidth = Display.getDesktopDisplayMode().getWidth();
        fullscreenHeight = Display.getDesktopDisplayMode().getHeight();
        
        windowedWidth = Math.min(windowedWidth, fullscreenWidth-100);
        windowedHeight = Math.min(windowedHeight, fullscreenHeight-100);
                
        //Initialize the game
        Game.INSTANCE.initialize();
        
        //Begin rendering the screen
        startRendering();

    }
    
    public void draw(){
        // Clear the colour buffer.  This clear the current screen
	GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        
        // Prepare to draw
	GL11.glMatrixMode(GL11.GL_MODELVIEW);
	GL11.glLoadIdentity();
        
        // Cry "Havoc!" and let slip the dogs of war
        Game.INSTANCE.draw(getWidth(), getHeight(), debugging);
        Hud.INSTANCE.draw(getWidth(), getHeight(), debugging);
                
        // Update window contents, moves the content we rendered in a buffer off screen into the active video memory so we can see it
	Display.update();
        
        // Cap fps to 60fps, this must be called at each iteraton and tries to limit the frame rate as best as it can to 60
        Display.sync(Settings.FRAME_RATE_PERSECOND);
    }
    
    public void update(float delta){
        // update the game!  this allows entities to update / move around
        Game.INSTANCE.update(delta);
        Camera.INSTANCE.update(delta);
        
        if(Game.INSTANCE.getPlayer() != null) {
            Hud.INSTANCE.showDebugging("Player X", String.valueOf((int)Game.INSTANCE.getPlayer().getX()));
            Hud.INSTANCE.showDebugging("Player Y", String.valueOf((int)Game.INSTANCE.getPlayer().getY()));
        }
        
        if(Game.INSTANCE.getScene() != null) {
            Hud.INSTANCE.showDebugging("Scene X", String.valueOf((int)Game.INSTANCE.getScene().getX()));
            Hud.INSTANCE.showDebugging("Scene Y", String.valueOf((int)Game.INSTANCE.getScene().getY()));
        }
        
        // update the displayed values in out hud, mostly for debugging
        //Hud.INSTANCE.set("Entities", String.valueOf(Entity.getUpdateCount()));
        Hud.INSTANCE.showDebugging("Sprites Drawn", String.valueOf(Sprite.getDrawnCount()));
        
        // if the window was resized lets ensure all the rendering stuff is configured to the new dimensions
        if(Display.wasResized() && !fullscreen) {
            setResolution(Display.getWidth(),Display.getHeight());
            initializeGlDisplay();
        }
        
        // update hud to show the current dimensions!
        //Hud.INSTANCE.set("Dimensions", String.valueOf(Display.getWidth()) + "x" + String.valueOf(Display.getHeight()));
        
        //Reset counters back to 0, these are mostly just generated for displaying in the hud
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
    private void setDisplayMode(int width, int height, boolean fullscreen) {

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
            
            if(!Display.isCreated()) {
                Display.create();
                Display.setTitle(title);   
            }

        } catch (LWJGLException e) {
            if(Display.isCreated())
                Debugging.INSTANCE.showWarning("Unable to setup mode "+width+"x"+height+" fullscreen="+fullscreen + e);
            else
                Debugging.INSTANCE.showError("Unable to setup mode "+width+"x"+height+" fullscreen="+fullscreen + e);
        }
    }
        
    
    public void toggleFullscreen() {
        setupDisplay(!fullscreen);
    }

    public void toggleDebugging(){
        debugging = !debugging;
    }
    
    private void setupDisplay(boolean fullscreen) {
        this.fullscreen = fullscreen;  
        
        // if windowed I gotta do this first *shrug*
        if(!this.fullscreen)
            Display.setResizable(allowResizing());
        
        
        setDisplayMode(getWidth(), getHeight(), fullscreen);
        
        if(Debugging.INSTANCE.hasError())
            return; //I guess something fatal happened, oops..
        
        // something went wrong and it's still window mode O.o
        if(this.fullscreen && !Display.isFullscreen()) {
            this.fullscreen = !this.fullscreen;     //whatever windowed mode is cooler anyways
            Display.setResizable(allowResizing());  //resizing is always better too!
        }
        
        // hide the mouse if fullscreen
        Mouse.setGrabbed(!allowMouse());

        // we need vsync on in fullscreen or visual tearing will be seen
        Display.setVSyncEnabled(allowVSync()); 
        
        // lets setup opengl display settings, this is here mostly to make sure the width and height are set properly for rendering
        initializeGlDisplay();
    }
        
    private boolean allowMouse(){
        return !fullscreen;
    }
    
    private boolean allowVSync(){
        return true;//fullscreen;
    }
    
    private boolean allowResizing(){
        return !fullscreen;
    }
    
    public void setTitle(String title) {
	this.title = title;
	if(Display.isCreated()) {
            Display.setTitle(title);
	}
    }
    
    private void setResolution(int width, int height) {
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
    
    /**
    * Start the rendering process. This method will cause the display to redraw
    * as fast as possible.
    */
    private void startRendering() {            
        setTitle("Awesome Secret Game");
            
        setupDisplay(fullscreen = false);
  
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
    
    private void initializeGlDisplay(){
        GL11.glViewport(0,0,getWidth(),getHeight());
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, getWidth(),getHeight(), 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);    
    }
    
    public void shutdown(){
        Display.destroy();
    }
} 
