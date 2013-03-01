/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Core.Interfaces;

/**
 *
 * @author Kaitlyn
 */
public interface IEntity {
    String getState();
    float getX();
    float getY();
    void setX(float x);
    void setY(float y);
}
