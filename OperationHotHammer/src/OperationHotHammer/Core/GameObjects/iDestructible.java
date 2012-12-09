/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Core.GameObjects;

/**
 *
 * @author Kaitlyn
 */
public interface iDestructible {
        public abstract void addDamage(float amount, int element);
        public abstract boolean isDestroyed();
        public abstract void destroy();
        public abstract void undestroy();
        public abstract float getHealth();
        public abstract float getMaxHealth();
        public abstract void setHealth(float hp);
        public abstract void modifyHealth(float hp);
        public abstract float setMaxHealth(float hp);
        public abstract boolean isDestructable();
}
