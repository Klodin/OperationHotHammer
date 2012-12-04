package OperationHotHammer.Core.GameObjects;

import OperationHotHammer.Core.GameObjects.Boundary.BoundaryShape;
import OperationHotHammer.Core.GameObjects.StatusEffects;

public abstract class GameObject {
   public final BoundaryShape collider;
   
   public float health = -1; //lets assume -1 means invincible.. some objects may be destructable like trees or chairs have have "health"
   public float weight = -1; //everything should have weight.. just makes physics easier.  Lets assume -1 means immovable for default
   
   public float x;
   public float y;
   
   public final StatusEffects statusEffects = new StatusEffects();
   
   public GameObject(float xx, float yy, BoundaryShape colliderShape) {
      collider = colliderShape;
      x = xx;
      y = yy;
   }
   
   public void update(float delta) {
       statusEffects.update(delta);
   }
}