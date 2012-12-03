package OperationHotHammer.Display;

public abstract class RenderingListener {
    
    @SuppressWarnings("LeakingThisInConstructor")
    RenderingListener() {
        Display.INSTANCE.addListener(this);
    }
    
    abstract void update();
}
