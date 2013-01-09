
package OperationHotHammer.Display;

import OperationHotHammer.Core.GameObjects.Entity;
import org.lwjgl.util.vector.Vector3f;

public interface ISprite {
    public void draw(Vector3f position);
    public void update(float delta, Entity e);
    public void setWidth(int w);
    public void setHeight(int h);
}
