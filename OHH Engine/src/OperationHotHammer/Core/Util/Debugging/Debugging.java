/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Core.Util.Debugging;

import OperationHotHammer.Core.Game;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author Kaitlyn
 */
public enum Debugging {
    INSTANCE;
    private boolean hasError = false;
    
    private LinkedList<DebuggingMessage> messageQueue = new LinkedList<>();
    private MessageGroup messageGroup;
    
        
    public void beginGroup(String message){
        if(messageGroup == null)
            messageGroup = new MessageGroup(getFormattedTime(), message, 1);
        else
            messageGroup.beginGroup(getFormattedTime(), message);
    }
    
    public void finishGroup(){
        if(messageGroup != null) {
            if(messageGroup.hasGroup()) {
                messageGroup.finishGroup();
            }else{
                add(messageGroup);            
                messageGroup = null;
            }
        }
    }
    
   public void finishAll(){
        if(messageGroup!=null) {
            messageGroup.finishAll();
            add(messageGroup);
            messageGroup = null;
        }
    }
    
    public void showMessage(String message) {
        if(messageGroup != null)
            messageGroup.add(getFormattedTime(), message);    
        else
            add(getFormattedTime(), message);
    }
    
    public void showWarning(String message) {
        if(messageGroup != null)
            messageGroup.add(getFormattedTime(), "*Warning* - " + message);
        else
            add(getFormattedTime(), "*Warning* - " + message);
    }
        
    public void showError(String message) {
        hasError = true;
        if(messageGroup != null) {
            messageGroup.add(getFormattedTime(), "*Error!* - " + message);
            messageGroup.finishAll();
            add(messageGroup);
            messageGroup = null;
        }
        Game.INSTANCE.shutdown();
    }
    
    private String getFormattedTime(){
        long timeL = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date resultdate = new Date(timeL);
        return sdf.format(resultdate);
    }
    
    private void add(String time, String message){
        add(new SingleMessage(time, message));
    }
    
    private void add(DebuggingMessage message){
        messageQueue.push(message);
    }
    
    public void add(MessageGroup message){
        messageQueue.push(message.pop());
        if(message.hasMessage())
            messageQueue.push(message);
    }
    
    public int getMessageCount() {
        int count = 0;
        
        if(messageGroup != null) {
            System.out.println("WARNING!!! A debugging message group was not closed before trying to use it.");
            finishAll();
        }
        
        for (DebuggingMessage message : messageQueue){
            count += message.getCount();
        }
        
        return count;
    }
    
    public String[] getMessages() {
        String[] messages = new String[getMessageCount()];
        int i = 0;
        String[] message;
        while(messageQueue.size()>0 && messageQueue.peekLast().hasMessage()) {
            
            message = messageQueue.peekLast().popMessage();
            messages[i++] = message[0] + "| " + message[1];
            if(!messageQueue.peekLast().hasMessage())
                messageQueue.removeLast();
        }
        
        messageQueue.clear();
        
        return messages;
    }
    
    public boolean hasError(){
        return this.hasError;
    }
}
