/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package OperationHotHammer.Display;

public abstract class RenderingListener {
    
    @SuppressWarnings("LeakingThisInConstructor")
    RenderingListener() {
        Display.INSTANCE.addListener(this);
    }
    
    abstract void update();
}
