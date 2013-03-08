/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OHH.Core.Util.Debugging;

/**
 *
 * @author Kaitlyn
 */
public class SingleMessage extends DebuggingMessage {
    private final String[] message = new String[2];
    
    boolean shown = false;
    
    public SingleMessage(String time, String message){
        this.message[0] = time;
        this.message[1] = message;
    }
    @Override
    public boolean hasMessage(){
        return !shown;
    }
    @Override
    public String[] popMessage(){
        shown = true;
        return message;
    }
    @Override
    public String[] peekMessage(){
        return message;
    }
    @Override 
    public int getCount(){
        return 1;
    }
}
