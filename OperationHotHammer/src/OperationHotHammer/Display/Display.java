package OperationHotHammer.Display;

import java.util.ArrayList;

public enum Display {
  INSTANCE;
  ArrayList<RenderingListener> listeners;
  
  public void addListener(RenderingListener rl) {
      listeners.add(rl);
  }
  
  public void render(){
      
      for(RenderingListener listener : listeners){
        listener.update();
      }
      
  }
} 
