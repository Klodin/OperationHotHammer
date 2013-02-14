/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Core.Interfaces;

/**
 *
 * @author Kaitlyn
 */
public interface IBackground {
    public static int BACKGROUND_FIXED = 1;
    public static int BACKGROUND_PAN = 2;
    
    public ISprite getBackgroundSprite();
    public int getBackgroundType();
    public float getBackgroundParallexRatio();
    
    public void setBackgroundSprite(ISprite sprite);
    public void setBackgroundType(int type);
    public void setBackgroundParallexRatio(float parallex);
}
