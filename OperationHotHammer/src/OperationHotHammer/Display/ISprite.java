
package OperationHotHammer.Display;

import org.lwjgl.util.vector.Vector3f;

public interface ISprite {
    public void draw(Vector3f position);
    public void setWidth(int w);
    public void setHeight(int h);
}
