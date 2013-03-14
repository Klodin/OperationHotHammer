
package OHH.Core.Interfaces;

import org.lwjgl.util.vector.Vector3f;

public interface ISprite {
    public void draw(Vector3f position);
    public void drawWireframe(Vector3f position);
    public void update(float delta, IEntity e);
    public void setWidth(int w);
    public void setHeight(int h);
    public int getWidth();
    public int getHeight();
    public ITexture getTexture();
    public boolean isTiled();
    public boolean isStretched();
    public boolean isCentered();
    public boolean isMaintainingAspectMin();
    public boolean isMaintainingAspectMax();
}
