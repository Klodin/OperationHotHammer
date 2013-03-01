/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Core.Interfaces;

/**
 *
 * @author Kaitlyn
 */
public interface IForeground {
    public static int FIXED = 1;
    public static int PAN = 2;
    
    public ISprite getSprite();
    public int getType();
    public float getParallexRatio();
    
    public void setSprite(ISprite sprite);
    public void setType(int type);
    public void setParallexRatio(float parallex);
}
