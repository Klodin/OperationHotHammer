/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Core.Debugging;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Kaitlyn
 */
public enum Debugging {
    INSTANCE;
    
    private ArrayList<String> messageQueue;
    
    public void showWarning(String message) {
        showMessage("Warning:" + message);
    }
    
    public void showMessage(String message) {
        String time = (new Timestamp(System.currentTimeMillis())).toString();
        messageQueue.add(time + " | " + message);
    }
}
