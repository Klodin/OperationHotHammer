
package OperationHotHammer;

import OperationHotHammer.Core.Game;
import OperationHotHammer.Core.Util.Debugging;
import OperationHotHammer.Core.Util.Settings;
import OperationHotHammer.Display.GameWindow;


class Main {
    
    
    public static void main(String argv[]) {
        GameWindow.INSTANCE.initialize();
        
        long lastLoopTime = System.nanoTime();
        
        Debugging.INSTANCE.showMessage("Beginning main game loop...");
        
        long start = System.nanoTime();
        long count = 0;
        
        while(Game.INSTANCE.isRunning()) {
            long time = System.nanoTime();
            float delta = ((time - lastLoopTime) / 1000000L);
            
            if(delta < Settings.FRAME_RATE_MILLISECONDS) {
                //try{
                    //Thread.sleep(100);//sleep for 100 ms
                //}catch(InterruptedException e){

                //}
                continue;
            }
            count++;
            GameWindow.INSTANCE.setFPS((int)(count / (((time - start) / 1000000000L)+1)));
            
            lastLoopTime = time;
            
            GameWindow.INSTANCE.draw(delta);
            
            if(Debugging.INSTANCE.getMessageCount() > 0) {
                String[] messages = Debugging.INSTANCE.getMessages(); 
                for(String message : messages) {
                    System.out.println(message);
                }
            }
            
        }
        
        //if any messages left spit them out
        if(Debugging.INSTANCE.getMessageCount() > 0) {
                String[] messages = Debugging.INSTANCE.getMessages(); 
                for(String message : messages) {
                    System.out.println(message);
                }
        }
    }
}
