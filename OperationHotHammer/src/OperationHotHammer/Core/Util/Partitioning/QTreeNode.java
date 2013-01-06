package OperationHotHammer.Core.Util.Partitioning;

import OperationHotHammer.Core.GameObjects.Boundary.Circle;
import OperationHotHammer.Core.GameObjects.Boundary.IBoundaryShape;
import OperationHotHammer.Core.GameObjects.Entity;
import OperationHotHammer.Core.Util.EntityArrayList;
import OperationHotHammer.Core.Util.EntityList;
import org.lwjgl.util.vector.Vector3f;

public class QTreeNode {
    private final int currDepth; // the current depth of this node
    private final Vector3f center; // the center of this node
    private final QTreeNode[] nodes; // the child nodes
    private final float halfWidth;
    private final EntityList objects; // the objects stored at this node
   
    public QTreeNode(float centerX, float centerY, float halfW, int stopDepth) {
        this.currDepth = stopDepth;
        this.halfWidth = halfW;
      
        // set Vector to current x-y-z values
        this.center = new Vector3f(centerX, centerY, 0.0f);
      
        this.objects = new EntityArrayList();
      
        float offsetX = 0.0f;
        float offsetY = 0.0f;
      
        if (stopDepth > 0) {
            // create 4 child nodes as long as depth is still greater than 0
            this.nodes = new QTreeNode[4];
         
            // halve child nodes size
            float step = halfWidth * 0.5f;
         
            // loop through and create new child nodes
            for (int i = 0; i < 4; i++) {
            
                // compute the offsets of the child nodes
                offsetX = (((i & 1) == 0) ? step : -step);
                offsetY = (((i & 2) == 0) ? step : -step);
            
                nodes[i] = new QTreeNode(centerX + offsetX, centerY + offsetY, step, stopDepth - 1);
            }   
        }else {
            this.nodes = null;
        }
    }
   
    public void insertObject(final Entity obj, final IBoundaryShape collider) {
        int index = 0; // get child node index as 0 initially
        boolean straddle = false; // set straddling to false
        float delta;
      
        switch(collider.getShape()){
          
            case IBoundaryShape.CIRCLE:
              
                // get the raw arrays, makes it easier to run these in a loop
                final float[] objPos    = {((Circle)collider).center.x,((Circle)collider).center.y,((Circle)collider).center.z};
                final float[] nodePos   = {center.x,center.y,center.z};

                for (int i = 0; i < 2; i++) {
                   // compute the delta, nodePos Vector index - objPos Vector
                   delta = nodePos[i] - objPos[i];

                   // if the absolute of delta is less than or equal to radius object straddling, break
                   if (Math.abs(delta) <= ((Circle)collider).radius) {
                      straddle = true;
                      break;
                   }

                   // compute the index to insert to child node
                   if (delta > 0.0f) {
                      index |= (1 << i);
                   }
                }

                if (!straddle && currDepth > 0) {
                   // not straddling, insert to child at index
                   nodes[index].insertObject(obj, collider);
                }
                else {
                   // straddling, insert to this node
                   objects.add(obj);
                }
            
                break;
        }
    }
   
    public void retrieveObjects(EntityList objList, float centerX, float centerY, float halfW){
       
        final Vector3f nodePos = new Vector3f(centerX,centerY,0);
        Vector3f subNodePos = new Vector3f();
        Vector3f deltaV3f = new Vector3f();
        float delta;
       
        for(Entity object : objects){
            if(object.collider.getShape() == IBoundaryShape.CIRCLE) {
                subNodePos.x = object.position.x;
                subNodePos.y = object.position.y;
                float radius = ((Circle)object.collider).radius;
                
                //for (int i = 0; i < 2; i++) {
                    deltaV3f.x = nodePos.x - subNodePos.x;
                    deltaV3f.y = nodePos.y - subNodePos.y;
                    delta = deltaV3f.x*deltaV3f.x + deltaV3f.y*deltaV3f.y;
                    if(delta <= (radius + halfW)*(radius + halfW)){
                        objList.add(object);
                        
                    }
                //}
            }
        }
       
        if(nodes != null) {
            for(int n = 0; n < 4; n++) {
                subNodePos.x = nodes[n].center.x;
                subNodePos.y = nodes[n].center.y;

                //for (int i = 0; i < 2; i++) {
                    deltaV3f.x = nodePos.x - subNodePos.x;
                    deltaV3f.y = nodePos.y - subNodePos.y;
                    delta = deltaV3f.x*deltaV3f.x + deltaV3f.y*deltaV3f.y;
                    if (delta <= (nodes[n].halfWidth + halfW)*(nodes[n].halfWidth + halfW)) {
                        nodes[n].retrieveObjects(objList, centerX, centerY, halfW);
                        
                    }
                //}
            }
        }
    }
   
    public void clean() {
        objects.clear();
      
        // clean children if available
        if (currDepth > 0) {
            for (int i = 0; i < 4; i++) {
                nodes[i].clean();
            }
        }
    }
}