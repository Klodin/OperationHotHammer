
package OperationHotHammer.Core.Interfaces;

import org.lwjgl.util.vector.Vector3f;

public interface IDisplayable {
    public void attach(ISprite s);
    public void draw(int resWidth, int resHeight, Vector3f cameraPosition);
}
