
package OHH.Display.HUD;

import OHH.Core.Util.Debugging.Debugging;
import OHH.Display.Text.Text;
import java.awt.Font;
import java.util.LinkedHashMap;
import java.util.Map;
import org.newdawn.slick.Color;


public enum Hud {
        INSTANCE;
        
        private LinkedHashMap<String,HudVar> vars = new LinkedHashMap<>();
        private int fontSize = 20;
        private Text uifont;
        
        private boolean init = false;
        
        public void initialize(String font){
            uifont = new Text(font, Font.PLAIN, fontSize, false);
            this.init = true;
        }
        
        public void show(String name, String value) {
            vars.put(name, new HudVar(name, value, false));
        }
        
        public void showDebugging(String name, String value) {
            vars.put(name, new HudVar(name, value, true));
        }
        
        public void draw(int screenWidth, int screenHeight, boolean debugging) {
            int line = 0;
            int column = 0;
            
            if(init) {
                for (Map.Entry<String, HudVar> entry : vars.entrySet()) { 
                    if(debugging || !entry.getValue().debug) {
                        uifont.draw(10+column, 10+fontSize*line++, entry.getKey() + ": " + entry.getValue().value);
                        if(10+fontSize*line+fontSize > screenHeight-10) {
                            column += 300;
                            line = 0;
                        }
                    }
                }
            }else{
                Debugging.INSTANCE.showWarning("Hud attempted to draw but was never initialized with a font!");
            }
        }
    
}
