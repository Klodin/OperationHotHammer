


import OperationHotHammer.Core.Game;
import OperationHotHammer.Core.Util.Debugging.Debugging;
import OperationHotHammer.Core.Util.Settings;
import OperationHotHammer.Display.GameWindow;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;


class ClientMain {
    
    
    public static void main(String argv[]) {
        GameWindow.INSTANCE.initialize();
        
        if(!Debugging.INSTANCE.hasError()) {
            Debugging.INSTANCE.showMessage("");
            Debugging.INSTANCE.showMessage("Beginning Main Game Loop");
            Debugging.INSTANCE.showMessage(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            Debugging.INSTANCE.showMessage("");
        
            long lastLoopTime = (Sys.getTime() * 1000) / Sys.getTimerResolution();
            long prevFpsTime = (Sys.getTime() * 1000) / Sys.getTimerResolution();
            long count = 0;
            boolean key_f1 = false;

            while(Game.INSTANCE.isRunning()) {
                long time = (Sys.getTime() * 1000) / Sys.getTimerResolution();
                float delta = (float)(time - lastLoopTime);

                if(delta < Settings.FRAME_RATE_MILLISECONDS) {
                    continue;
                }

                count++;
                if((time - prevFpsTime) >= 1000L) {
                    int fps = (int)(count / ((time - prevFpsTime) / 1000L));
                    count = 0;
                    prevFpsTime = time;
                    GameWindow.INSTANCE.setFPS(fps);
                }
                lastLoopTime = time;

                if(Keyboard.isKeyDown(Keyboard.KEY_F1) && !key_f1) {    // Is F1 Being Pressed?
                    key_f1 = true;                                      // Tell Program F1 Is Being Held
                    GameWindow.INSTANCE.toggleFullscreen();            // Toggle Fullscreen / Windowed Mode
                }
                if(!Keyboard.isKeyDown(Keyboard.KEY_F1)) {              // Is F1 Being Pressed?
                    key_f1 = false;
                }

                if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {              // Is F1 Being Pressed?
                    Game.INSTANCE.changePositionX(10);
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {              // Is F1 Being Pressed?
                    Game.INSTANCE.changePositionX(-10);
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_UP)) {              // Is F1 Being Pressed?
                    Game.INSTANCE.changePositionY(-10);
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {              // Is F1 Being Pressed?
                    Game.INSTANCE.changePositionY(10);
                }


                if(Display.isCloseRequested() || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                    Game.INSTANCE.shutdown();
                    Display.destroy();
                }else{
                    GameWindow.INSTANCE.update(delta);
                    GameWindow.INSTANCE.draw();
                }

                if(Debugging.INSTANCE.getMessageCount() > 0) {
                    String[] messages = Debugging.INSTANCE.getMessages(); 
                    for(String message : messages) {
                        if(message != null)
                            System.out.println(message);
                    }
                }

            }
            Debugging.INSTANCE.showMessage("");
            Debugging.INSTANCE.showMessage("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            Debugging.INSTANCE.showMessage("Exited Main Game Loop");
        }
        
        //if any messages left spit them out
        if(Debugging.INSTANCE.getMessageCount() > 0) {
                String[] messages = Debugging.INSTANCE.getMessages(); 
                for(String message : messages) {
                    if(message != null)
                        System.out.println(message);
                }
        }
    }
}
