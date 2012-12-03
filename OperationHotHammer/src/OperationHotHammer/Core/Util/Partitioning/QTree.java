package OperationHotHammer.Core.Util.Partitioning;

import OperationHotHammer.Core.GameObjects.GameObject;


public class QTree {
   private final QTreeNode node;
   
   // define a quadtree extends as width and height, define quadtree depth.
   public QTree(final float worldExtends, int worldDepth) {
      node = new QTreeNode(0,0,worldExtends, worldDepth);
   }

   // insert a GameObject at the quadtree
   public void insertObject(final GameObject obj) {
      node.insertObject(obj, obj.collider);
   }
   
   // clean the quadtree
   public void clean() {
      node.clean();
   }
}