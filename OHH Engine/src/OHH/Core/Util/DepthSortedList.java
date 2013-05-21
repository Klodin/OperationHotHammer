/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OHH.Core.Util;

import OHH.Core.GameObjects.Entity;
import java.util.Comparator;
import java.util.Iterator;
import joracle.util.TreeList;

/**
 *
 * @author Kaitlyn
 */
public class DepthSortedList extends EntityList {

    private TreeList<Entity> list;
    
    public DepthSortedList() {
        list = new TreeList<>(new Comparator<Entity>(){
            @Override
            public int compare(Entity o1, Entity o2){
                return o1.getPosition().z > o2.getPosition().z? 1 : -1;
            }
            
            @Override
            public boolean equals(Object obj){
                return (obj.getClass() == this.getClass());
            }
        });   
    }
    
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
