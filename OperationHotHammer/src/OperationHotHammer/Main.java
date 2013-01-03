
package OperationHotHammer;

import OperationHotHammer.Core.Game;
import OperationHotHammer.Core.Util.Debugging;
import OperationHotHammer.Core.Util.Settings;
import OperationHotHammer.Display.Display;


class Main {
    
    
    public static void main(String argv[]) {
        Display.INSTANCE.initialize();
        
        long lastLoopTime = System.nanoTime();
        
        Debugging.INSTANCE.showMessage("Beginning main game loop...");
        
        //long start = System.nanoTime();
        //long count = 0;
        
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
            //count++;
            //double fps = count / (((time - start) / 1000000000L)+1);
            //Debugging.INSTANCE.showMessage("Draw... (" + fps + ")");
            
            lastLoopTime = time;
            
            Display.INSTANCE.render(delta);
            
            if(Debugging.INSTANCE.getMessageCount() > 0) {
                String[] messages = Debugging.INSTANCE.getMessages(); 
                for(String message : messages)
                    System.out.println(message);
            }
            
        }   
    }
}
