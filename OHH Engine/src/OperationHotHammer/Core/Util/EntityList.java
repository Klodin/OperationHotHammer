/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Core.Util;

import OperationHotHammer.Core.GameObjects.Entity;
import java.util.ArrayList;

/**
 *
 * @author Kaitlyn
 */
public abstract class EntityList implements Iterable<Entity> {
    abstract public void clear();
    abstract public void add(Entity e);
    abstract public int size();
}
