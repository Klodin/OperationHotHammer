/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OHH.Core.Util;

import org.lwjgl.opengl.GL11;

/**
 *
 * @author Kaitlyn
 */
public class Color4f {
    public float r;
    public float g;
    public float b;
    public float a;
    
    public Color4f(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
    
    public void bind(){
        GL11.glColor4f(r,g,b,a);
    }
}
