/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OHH.Core.Interfaces;

/**
 *
 * @author Kaitlyn
 */
public interface IScenery {
    public static int CENTERED_TO_SCREEN = 1;
    public static int MOVE_WITH_CAMERA = 2;
    
    public ISprite getSprite();
    public int getType();
    public float getParallex();
    
    public void setSprite(ISprite sprite);
    public void setType(int type);
    public void setParallex(float parallex);
}
