package SimIndie;

import OHH.Core.Game;
import OHH.Core.GameObjects.Entities.SimpleCreature;
import OHH.Core.GameObjects.Entity;
import OHH.Core.Interfaces.IScenery;
import OHH.Core.Interfaces.ITexture;
import OHH.Core.Scene;
import OHH.Core.Util.Debugging.Debugging;
import OHH.Core.Util.Settings;
import OHH.Display.Camera;
import OHH.Display.GameWindow;
import OHH.Display.Hud;
import OHH.Display.Sprite.Animation.AnimatedSprite;
import OHH.Display.Sprite.Animation.TextureScrollBehaviour;
import OHH.Display.Sprite.Scenery;
import OHH.Display.Sprite.Sprite;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;


class SimIndie {
    
    boolean quitRequested = false;
    boolean KEY_LEFT = false;
    boolean KEY_RIGHT = false;
    boolean KEY_UP = false;
    boolean KEY_DOWN = false;
    
    public static void main(String argv[]) {
        //this is needed when compiling into a jar file for distribution
        //System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/natives/");
        
        SimIndie app = new SimIndie();
        app.run();
    }
    
    public void run(){
        GameWindow.INSTANCE.initialize();
        Hud.INSTANCE.initialize("SimIndie/Assets/Fonts/DisposableDroidBB.ttf");
        
        /////////////////////////////

        //Generate some content
        Scene scene = new Scene("Testing Scene", 500, 500);
        
        Entity e;
        AnimatedSprite as;
        Sprite s;

        Debugging.INSTANCE.beginGroup("Initializing (Scene '" + scene.getName() + "')");

            Debugging.INSTANCE.showMessage("Creating Player Entity");
            as = new AnimatedSprite();
            as.addSprite(new Sprite("SimIndie/Assets/Sprites/Witch/standing_1.png", ITexture.STRETCH | ITexture.MAINTAIN_ASPECT_MIN), 1200f);
            as.addSprite(new Sprite("SimIndie/Assets/Sprites/Witch/standing_2.png", ITexture.STRETCH | ITexture.MAINTAIN_ASPECT_MIN), 800f);
            as.addSprite(new Sprite("SimIndie/Assets/Sprites/Witch/standing_1.png", ITexture.STRETCH | ITexture.MAINTAIN_ASPECT_MIN), 2400f);
            as.addSprite(new Sprite("SimIndie/Assets/Sprites/Witch/standing_2.png", ITexture.STRETCH | ITexture.MAINTAIN_ASPECT_MIN), 400f);
            as.addSprite(new Sprite("SimIndie/Assets/Sprites/Witch/standing_1.png", ITexture.STRETCH | ITexture.MAINTAIN_ASPECT_MIN), 300f);
            as.addSprite(new Sprite("SimIndie/Assets/Sprites/Witch/standing_2.png", ITexture.STRETCH | ITexture.MAINTAIN_ASPECT_MIN), 200f);
            e = new SimpleCreature(250,250); 
            e.attach(as);
            scene.addPlayer(e); // This is likely not the ideal location for this but eh
            
            Debugging.INSTANCE.showMessage("Setting Camera To Follow Player");
            Camera.INSTANCE.setTarget(e);

            Debugging.INSTANCE.showMessage("Setting Background");
            s = new Sprite("SimIndie/Assets/dirt2.jpg", ITexture.TILED);
            scene.addBackground(new Scenery(s, IScenery.MOVE_WITH_CAMERA));   

            Debugging.INSTANCE.showMessage("Setting Foreground");
            as = new AnimatedSprite(new TextureScrollBehaviour(-1.5f, -1.2f));
            as.addSprite(new Sprite("SimIndie/Assets/cloudsbg.png", ITexture.TILED), 1000f);
            scene.addForeground(new Scenery(as, IScenery.MOVE_WITH_CAMERA, 2.1f));  

        Debugging.INSTANCE.finishGroup();
        
        if(!Debugging.INSTANCE.hasError()) {
            scene.load();
        }
        
        /////////////////////////////
        
        if(!Debugging.INSTANCE.hasError()) {
            Debugging.INSTANCE.showMessage("");
            Debugging.INSTANCE.showMessage("Beginning Main Game Loop");
            Debugging.INSTANCE.showMessage(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            Debugging.INSTANCE.showMessage("");
        
            long lastLoopTime = getTime();
            long prevFpsTime = getTime();
            
            long frameCount = 0;
            float delta;
            long time;
            int fps = 0;
            
            Hud.INSTANCE.set("FPS", "0");
            
            while(Game.INSTANCE.isRunning()) {
                time = getTime();
                delta = (float)(time - lastLoopTime) / Settings.FRAME_RATE_PERMILLISECOND;
                lastLoopTime = time;
                
                frameCount++;
                if((time - prevFpsTime) >= 1000L) {
                    fps = (int)(frameCount / ((time - prevFpsTime) / 1000L));
                    frameCount = 0;
                    prevFpsTime = time;
                    Hud.INSTANCE.set("FPS", String.valueOf(fps));
                }                

                //resolve any key presses
                handleUserInputs(1);//delta);

                
                if(quitRequested) {
                    GameWindow.INSTANCE.shutdown();
                    Game.INSTANCE.shutdown();
                }else{
                    GameWindow.INSTANCE.update(delta);
                    GameWindow.INSTANCE.draw();
                }
 
                showDebuggingMessages();

            }
            Debugging.INSTANCE.showMessage("");
            Debugging.INSTANCE.showMessage("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            Debugging.INSTANCE.showMessage("Exited Main Game Loop");
        }
        
        //if any messages left spit them out
        showDebuggingMessages();
    }
    
    public long getTime(){
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
    
    public void showDebuggingMessages(){
        if(Debugging.INSTANCE.getMessageCount() > 0) {
            String[] messages = Debugging.INSTANCE.getMessages(); 
            for(String message : messages) {
                if(message != null)
                    System.out.println(message);
            }
        }
    }
    
    public void handleUserInputs(float delta){

        if (Display.isCloseRequested())
        {
            // The display window is being closed
            quitRequested = true;
            return;
        }

        while (Keyboard.next())
        {
            int key = Keyboard.getEventKey();
            boolean isDown = Keyboard.getEventKeyState();
            if (isDown) {
                switch (key) {
                    case Keyboard.KEY_ESCAPE:
                        quitRequested = true;
                        break;

                    case Keyboard.KEY_RETURN:
                        if (Keyboard.isKeyDown(Keyboard.KEY_LMENU) || Keyboard.isKeyDown(Keyboard.KEY_RMENU)) {
                            GameWindow.INSTANCE.toggleFullscreen();
                        }
                        break;
                     
                    case Keyboard.KEY_RIGHT:
                        KEY_RIGHT = true;
                        break;
                        
                    case Keyboard.KEY_LEFT:
                        KEY_LEFT = true;
                        break;
                        
                    case Keyboard.KEY_UP:
                        KEY_UP = true;
                        break;
                        
                    case Keyboard.KEY_DOWN:
                        KEY_DOWN = true;
                        break;
                }
            }else{
                //key up
                switch(key){
                 case Keyboard.KEY_RIGHT:
                        KEY_RIGHT = false;
                        break;
                        
                    case Keyboard.KEY_LEFT:
                        KEY_LEFT = false;
                        break;
                        
                    case Keyboard.KEY_UP:
                        KEY_UP = false;
                        break;
                        
                    case Keyboard.KEY_DOWN:
                        KEY_DOWN = false;
                        break;   
                }
            }
        }

        if(KEY_RIGHT)
            Game.INSTANCE.changePositionX(Settings.MOVEMENT_SPEED * delta);
                        
        if(KEY_LEFT)
            Game.INSTANCE.changePositionX(-Settings.MOVEMENT_SPEED * delta);
                        
        if(KEY_UP)
            Game.INSTANCE.changePositionY(-Settings.MOVEMENT_SPEED * delta);
                        
        if(KEY_DOWN)
            Game.INSTANCE.changePositionY(Settings.MOVEMENT_SPEED * delta);
        
        if(KEY_RIGHT || KEY_LEFT || KEY_UP || KEY_DOWN)
            Camera.INSTANCE.setEasing(false);
        else
            Camera.INSTANCE.setEasing(true);
    }
}
