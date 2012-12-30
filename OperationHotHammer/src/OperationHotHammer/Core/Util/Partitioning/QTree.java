package OperationHotHammer.Core.Util.Partitioning;

import OperationHotHammer.Core.GameObjects.Entity;
import java.util.ArrayList;


public class QTree {
   private final QTreeNode node;
   
   // define a quadtree extends as width and height, define quadtree depth.
   public QTree(final float worldExtends, int worldDepth) {
      node = new QTreeNode(0,0,worldExtends/2, worldDepth);
   }

   // insert a GameObject at the quadtree
   public void insertObject(final Entity obj) {
      node.insertObject(obj, obj.collider);
   }
   
   // insert a GameObject at the quadtree
   public ArrayList<Entity> retrieveObjects(float centerX, float centerY, float halfWidth) {
      
      ArrayList<Entity> objects = new ArrayList<>();
      node.retrieveObjects(objects, centerX, centerY, halfWidth);
      return objects;
   }
   
   // clean the quadtree
   public void clean() {
      node.clean();
   }
}