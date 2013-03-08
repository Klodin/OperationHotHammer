/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Core.Interfaces;

import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Kaitlyn
 */
public interface IPosition {
    Vector3f getPosition();
    float getX();
    float getY();
    float getZ();
    void setX(float X);
    void setY(float Y);
    void setZ(float Z);
}
