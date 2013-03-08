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
public interface IEntity {
    String getState();
    Vector3f getPosition();
    float getX();
    float getY();
    float getZ();
    void setX(float x);
    void setY(float y);
    void setZ(float z);
}
