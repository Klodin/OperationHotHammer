/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OHH.Display.Text;

import OHH.Core.Util.Color4f;
import OHH.Core.Util.Debugging.Debugging;
import OHH.Core.Util.TrueTypeFont;
import java.awt.Font;
import java.io.InputStream;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author Kaitlyn
 */
public class Text {
    private Font awtFont;
    private TrueTypeFont font;
    private boolean loaded = false;
    private Color4f color = new Color4f(1f,1f,1f,0.8f);
    
    public Text(final String name, final int style, final int size, final boolean antialias) {
        try {
            InputStream inputStream = ResourceLoader.getResourceAsStream(name);
            
            awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            awtFont = awtFont.deriveFont(style, size); // set font size
            
            font = new TrueTypeFont(awtFont, antialias);
            
            loaded = true;
            
        } catch (Exception e) {
            Debugging.INSTANCE.showError("A problem occured when loading a font! (" + name + ", " + style + ", " + size + ", antialias:" + (antialias?"yes":"no") + ")");            
            loaded = false;
        }
    }
    
    public void draw(float x, float y, String text){
        if(loaded) {
            color.bind();
            font.drawString(x, y, text, 1f, 1f);
        }
    }
}


