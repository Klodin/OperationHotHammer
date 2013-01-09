/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Core.GameObjects;

import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Kaitlyn
 */
public interface iPhysics {
    public abstract void setHeading(Vector3f vec);
    public abstract void setHeading(Vector3f vec, float duration);
}
