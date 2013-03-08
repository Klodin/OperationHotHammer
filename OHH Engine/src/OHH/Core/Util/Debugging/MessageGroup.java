/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OHH.Core.Util.Debugging;

import java.util.LinkedList;

/**
 *
 * @author Kaitlyn
 */
public class MessageGroup extends DebuggingMessage {
    private LinkedList<DebuggingMessage> messageQueue = new LinkedList<>();
    
    private MessageGroup messageGroup;
    private final int depth;
    
    public void beginGroup(String time, String message){
        if(messageGroup == null)
            messageGroup = new MessageGroup(time, message, depth+1);
        else
            messageGroup.beginGroup(time, message);
    }
    
    public boolean hasGroup(){
        return messageGroup != null;
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
    
    public MessageGroup(String time, String message, int depth){
        this.depth = depth;
        add(time, message);
    }
    
    public void add(String time, String message){
        add(new SingleMessage(time, message));
    }
    
    public void add(DebuggingMessage message){
        if(messageGroup != null)
            messageGroup.add(message);    
        else
            messageQueue.push(message);
    }
    
    public void add(MessageGroup message){
        messageQueue.push(message.pop());
        if(message.hasMessage())
            messageQueue.push(message);
    }
    
    public DebuggingMessage pop(){
        return messageQueue.removeLast();
    }
    
    @Override
    public boolean hasMessage(){
        if(messageGroup != null) {
            System.out.println("WARNING!!! A debugging message group was not closed before trying to use it.");
            finishAll();
        }
        return messageQueue.size() > 0;
    }
    
    @Override
    public String[] popMessage(){
        if(messageGroup != null) {
            System.out.println("WARNING!!! A debugging message group was not closed before trying to use it.");
            finishAll();
        }
        
        if(messageQueue.size() == 0)
            return null;
        
        String msg = messageQueue.peekLast().peekMessage()[1];
        int messageDuplicateCount = 0;
        String[] message;
        String[] temp;
                
        do{
            
            message = messageQueue.peekLast().popMessage();
            if(!messageQueue.peekLast().hasMessage())
                messageQueue.removeLast();
            
            messageDuplicateCount++;
        }while(messageQueue.size() > 0 && messageQueue.peekLast().peekMessage()[1].equals(msg));
        
        message[1] = "\u21B3" + message[1];
        
        if(messageDuplicateCount > 1)
            message[1] = message[1] + " [x" + messageDuplicateCount + "]";
        
        return message;
    }
    
    @Override
    public String[] peekMessage(){
        if(messageGroup != null) {
            System.out.println("WARNING!!! A debugging message group was not closed before trying to use it.");
            finishAll();
        }
        
        if(messageQueue.size() == 0)
            return null;
        
        String[] message = messageQueue.peekLast().peekMessage();

        return message;
    }
    
    @Override 
    public int getCount() {
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
}
