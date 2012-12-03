/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Core.Util;


public class Vector {
   public final float vec[];
   
   public Vector() {
      vec = new float[3];
   }
   
   public Vector(final float x, final float y, final float z) {
      vec = new float[3];
      
      vec[0] = x;
      vec[1] = y;
      vec[2] = z;
   }
   
   public void set(final Vector position) {
      for (int i = 0; i < 3; i++) {
         vec[i] = position.vec[i];
      }
   }
}