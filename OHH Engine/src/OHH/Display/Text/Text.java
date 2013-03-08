/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OHH.Display.Text;

import OHH.Core.Util.Debugging.Debugging;
import java.awt.Font;
import java.io.InputStream;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author Kaitlyn
 */
public class Text {
    private Font awtFont;
    private TrueTypeFont font;
    private boolean loaded = false;
    
    public Text(final String name, final int style, final float size, final boolean antialias) {
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
    
    public void draw(float x, float y, String text, Color color){
        if(loaded) {
            Color.white.bind();
            TextureImpl.bindNone();
            font.drawString(x, y, text, color);
        }
    }
}


