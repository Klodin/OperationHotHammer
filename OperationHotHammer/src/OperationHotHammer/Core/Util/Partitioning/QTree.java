package OperationHotHammer.Core.Util.Partitioning;

import OperationHotHammer.Core.GameObjects.Entity;
import java.util.ArrayList;


public class QTree {
   private final QTreeNode node;
   
   // define a quadtree extends as width and height, define quadtree depth.
   public QTree(final float worldExtends, int worldDepth) {
      node = new QTreeNode(0,0,worldExtends, worldDepth);
   }

   // insert a GameObject at the quadtree
   public void insertObject(final Entity obj) {
      node.insertObject(obj, obj.collider);
   }
   
   // insert a GameObject at the quadtree
   public ArrayList<Entity> retreiveObjects(float centerX, float centerY, float range) {
      
      ArrayList<Entity> objects = new ArrayList<>();
      node.retreievObjects(objects, centerX - range/2, centerY - range/2, centerX + range/2, centerY + range/2);
      return objects;
   }
   
   // clean the quadtree
   public void clean() {
      node.clean();
   }
}