/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Core.Util;

import OperationHotHammer.Core.GameObjects.Entity;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Kaitlyn
 */
public class EntityArrayList extends EntityList{

    private ArrayList<Entity> list = new ArrayList<>();
    
    @Override
    public Iterator iterator() {
        return list.iterator();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public void add(Entity e) {
        list.add(e);
    }
    
    @Override
    public int size() {
        return list.size();
    }
}
