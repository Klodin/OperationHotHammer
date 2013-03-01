
package OperationHotHammer.Core.Interfaces;

import org.lwjgl.util.vector.Vector3f;

public interface ISprite {
    public void draw(Vector3f position);
    public void update(float delta, IEntity e);
    public ISprite setWidth(int w);
    public ISprite setHeight(int h);
    public int getWidth();
    public int getHeight();
    public ITexture getTexture();
}
