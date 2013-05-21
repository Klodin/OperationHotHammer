
package OHH.Core.Interfaces;

import OHH.Core.GameObjects.Boundary.IBoundaryShape;
import OHH.Core.Util.Color4f;
import org.lwjgl.util.vector.Vector3f;

public interface ISprite {
    public void draw(Vector3f position);
    public void drawWireframe(Vector3f position);
    public void drawFilled(Vector3f position, Color4f colour);
    public void update(float delta, IEntity e);
    public void setWidth(int w);
    public void setHeight(int h);
    public int getWidth();
    public int getHeight();
    public ISprite resize(int w, int h);
    public ITexture getTexture();
    public IBoundaryShape getBoundary();
    public boolean isTiled();
    public boolean isStretched();
    public boolean isCentered();
    public boolean isMaintainingAspectMin();
    public boolean isMaintainingAspectMax();
}
