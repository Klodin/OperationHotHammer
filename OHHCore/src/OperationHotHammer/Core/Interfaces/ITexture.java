/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Core.Interfaces;

/**
 *
 * @author Kaitlyn
 */
public interface ITexture {
    public final static int TILED               = 1;
    public final static int STRETCH             = 2;
    public final static int MAINTAIN_ASPECT_MAX = 4;
    public final static int MAINTAIN_ASPECT_MIN = 8;
    
    public void bind();
    
    public int getTextureWidth();
    public int getTextureHeight();
    public int getOriginalTextureWidth();
    public int getOriginalTextureHeight();
    public float getOffsetX();
    public float getOffsetY();
    
    public void setOffsetX(float x);
    public void setOffsetY(float y);
    public void setTextureWidth(int w);
    public void setTextureHeight(int h);
    
    public ITexture clone();
}
