package OHH.Core.Util.Partitioning;

import OHH.Core.GameObjects.Entity;
import OHH.Core.Util.EntityList;


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
   public void retrieveObjects(EntityList objects, float centerX, float centerY, int halfWidth, int halfHeight) {
      node.retrieveObjects(objects, centerX, centerY, halfWidth, halfHeight);
   }
   
   // insert a GameObject at the quadtree
   public void retrieveObjects(EntityList objects, float centerX, float centerY, float halfWidth) {
      node.retrieveObjects(objects, centerX, centerY, halfWidth);
   }
   
   // clean the quadtree
   public void clean() {
      node.clean();
   }
}