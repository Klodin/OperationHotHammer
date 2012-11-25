/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Display;

/**
 *
 * @author Kaitlyn
 */

public abstract class Displayable {
    public final static int LAYER_BACK    = 1;
    public final static int LAYER_MIDDLE  = 2;
    public final static int LAYER_FRONT   = 3;
    
    public int x, y, layer;
    
    public int getX() { return x; }
    public int getY() { return y; }
    public int getLayer() { return layer; }
    
    public void setX(int _x) { x = _x; }
    public void setY(int _y) { y = _y; }
    public void setLayer(int _z) { layer = _z; }
    
    abstract void draw();
}
