/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Core.Util;

import OperationHotHammer.Core.Game;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Kaitlyn
 */
public enum Debugging {
    INSTANCE;
    
    private ArrayList<String> messageQueue = new ArrayList<>();
    
    public void showWarning(String message) {
        showMessage("Warning:" + message);
    }
        
    public void showError(String message) {
        showMessage("Error:" + message);
        Game.INSTANCE.shutdown();
    }
    
    public void showMessage(String message) {
        String time = (new Timestamp(System.currentTimeMillis())).toString();
        messageQueue.add(time + " | " + message);
    }
    
    public int getMessageCount() {
        return messageQueue.size();
    }
    
    public String[] getMessages() {
        String[] messages = new String[messageQueue.size()];
        messages = messageQueue.toArray(messages);
        messageQueue.clear();
        return messages;
    }
}
