package OperationHotHammer.Core.GameObjects;

import OperationHotHammer.Core.GameObjects.Boundary.BoundaryShape;
import OperationHotHammer.Core.GameObjects.StatusEffects.StatusEffects;

public abstract class Entity {
   public final BoundaryShape collider;
   
   public float weight = 999999999;
   
   public float x;
   public float y;
   
   public Entity(float xx, float yy, BoundaryShape colliderShape) {
      collider = colliderShape;
      x = xx;
      y = yy;
   }
   
   public abstract void update(float delta);
}