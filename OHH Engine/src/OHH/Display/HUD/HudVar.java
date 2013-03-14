/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OHH.Display.HUD;

/**
 *
 * @author Kaitlyn
 */
public class HudVar {
    public String name;
    public String value;
    public boolean debug = false;
    
    public HudVar(String name, String value, boolean debug){
        this.name = name;
        this.value = value;
        this.debug = debug;
    }
}
