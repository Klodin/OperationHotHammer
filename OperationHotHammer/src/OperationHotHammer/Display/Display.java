/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
        
package OperationHotHammer.Display;

import java.util.ArrayList;

/**
 *
 * @author Kaitlyn
 */
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
