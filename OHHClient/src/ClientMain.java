


import OperationHotHammer.Core.Game;
import OperationHotHammer.Core.Util.Debugging.Debugging;
import OperationHotHammer.Core.Util.Settings;
import OperationHotHammer.Display.GameWindow;
import OperationHotHammer.Display.Hud;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;


class ClientMain {
    
    boolean quitRequested = false;
    boolean KEY_LEFT = false;
    boolean KEY_RIGHT = false;
    boolean KEY_UP = false;
    boolean KEY_DOWN = false;
    
    public static void main(String argv[]) {
        ClientMain app = new ClientMain();
        app.run();
    }
    
    public void run(){
        GameWindow.INSTANCE.initialize();
        
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
                delta = (float)(time - lastLoopTime) / Settings.FRAME_RATE_MILLISECONDS;
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
                        if (Keyboard.isKeyDown(Keyboard.KEY_LMENU)) {
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
            Game.INSTANCE.changePositionX(Settings.CAMERA_MOVE_SPEED * delta);
                        
        if(KEY_LEFT)
            Game.INSTANCE.changePositionX(-Settings.CAMERA_MOVE_SPEED * delta);
                        
        if(KEY_UP)
            Game.INSTANCE.changePositionY(-Settings.CAMERA_MOVE_SPEED * delta);
                        
        if(KEY_DOWN)
            Game.INSTANCE.changePositionY(Settings.CAMERA_MOVE_SPEED * delta);
    }
}
