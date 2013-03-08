
package OHH.Display;

import OHH.Core.Util.Debugging.Debugging;
import OHH.Display.Text.Text;
import java.awt.Font;
import java.util.LinkedHashMap;
import java.util.Map;
import org.newdawn.slick.Color;


public enum Hud {
        INSTANCE;
        
        private LinkedHashMap<String,String> vars = new LinkedHashMap<>();
        private float fontSize = 20f;
        private Text uifont;
        
        private boolean init = false;
        
        public void initialize(String font){
            uifont = new Text(font, Font.PLAIN, fontSize, false);
            this.init = true;
        }
        
        public void set(String name, String value) {
            vars.put(name, value);
        }
        
        public void draw(int screenWidth, int screenHeight) {
            int line = 0;
            int column = 0;
            
            if(init) {
                for (Map.Entry<String, String> entry : vars.entrySet()) { 
                    uifont.draw(10+column, 10+fontSize*line++, entry.getKey() + ": " + entry.getValue(), Color.white);
                    if(10+fontSize*line+fontSize > screenHeight-10) {
                        column += 300;
                        line = 0;
                    }
                }
            }else{
                Debugging.INSTANCE.showWarning("Hud attempted to draw but was never initialized with a font!");
            }
        }
    
}
