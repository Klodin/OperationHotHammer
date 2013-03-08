/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Display;

import OperationHotHammer.Core.Game;
import OperationHotHammer.Core.Interfaces.IPosition;
import OperationHotHammer.Core.Util.Settings;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Kaitlyn
 */
public enum Camera {
    INSTANCE;
    
    private IPosition target;
    private boolean easing = true;
    
    public void setTarget(IPosition target){
        this.target = target;
    }
    
    public IPosition getTarget(){
        return this.target;
    }
    
    public void setEasing(boolean easing){
        this.easing = easing;
    }
    
    public void update(float delta){
        /* camera easing.. */
        
        if(getTarget() == null)
            return; //nothing to do here
        
        //vectors for player and scene (the camera's position)
        Vector3f scenePos = Game.INSTANCE.getScene().getPosition();
        Vector3f playerPos = getTarget().getPosition();
        
        //vector between the camera position and player position
        Vector3f vectorBetween = new Vector3f(scenePos.x - playerPos.x, scenePos.y - playerPos.y, 0);
        
        //calculate magnitude of vector
        float distance = (float)Math.sqrt(vectorBetween.x*vectorBetween.x + vectorBetween.y*vectorBetween.y + 0);
        if(distance==0) return; //to be safe, avoid divide by 0 etc
        
        //normalize the vector
        vectorBetween.x /= distance;
        vectorBetween.y /= distance;
        
        float adjX = 0, adjY = 0;
        float adjDistance = 0;
        
        //if(easing) {
        
            //how much to move towards scene
            adjDistance = distance / Settings.CAMERA_EASE_TO_POSITION * delta;
            
            //if we are close enough to the player (or negative, meaning overshooting their position somehow) then move directly to the player
            if(distance < 1f) {
                Game.INSTANCE.getScene().setX(playerPos.x);
                Game.INSTANCE.getScene().setY(playerPos.y);   
                return;
            }
            
            //1) lower limit if slower, or 2) or use lowerlimit/2 if within the range of minDistance (allows a smoother finish)
            float lower = (distance < Settings.CAMERA_EASE_MINDISTANCE * delta ? (Settings.CAMERA_EASE_LOWER_LIMIT * delta)/((Settings.CAMERA_EASE_MINDISTANCE * delta)/distance) : Settings.CAMERA_EASE_LOWER_LIMIT * delta);
            
            //limit 'slowest we'll move towards it'
            if(adjDistance < lower || distance < Settings.CAMERA_EASE_MINDISTANCE * delta)
                adjDistance *= 0.2;         //20% of original adjusted amount
                adjDistance += 0.8 * lower; //80% of lower speed, this smooth the transition to lower speed

            //limit 'fastest we'll move towards it'
            if(adjDistance > Settings.CAMERA_EASE_UPPER_LIMIT * delta)
                adjDistance = Settings.CAMERA_EASE_UPPER_LIMIT * delta;

            //when on lower speed, ensure the speed will precisly close the gap to reach the character's world position (smoother animation)
            if(adjDistance == lower)
                adjDistance = distance / (float)((int)(distance / lower));
            
            //apply the adjustment amount
            adjDistance = distance - adjDistance;
            
            //adjust the vector by the new distance
            vectorBetween.x *= adjDistance;
            vectorBetween.y *= adjDistance;

            //transpose to appropriate origin
            adjX = vectorBetween.x + playerPos.x;
            adjY = vectorBetween.y + playerPos.y;

            //lets update the scene position now
            Game.INSTANCE.getScene().setX(adjX);
            Game.INSTANCE.getScene().setY(adjY);
        //}else{ 
            //not easing
            if(distance > Settings.CAMERA_EASE_MAXDISTANCE) {
                adjDistance = Settings.CAMERA_EASE_MAXDISTANCE;
                
                //adjust the vector by the new distance
                vectorBetween.x *= adjDistance;
                vectorBetween.y *= adjDistance;

                //transpose to appropriate origin
                adjX = vectorBetween.x + playerPos.x;
                adjY = vectorBetween.y + playerPos.y;

                //lets update the scene position now
                Game.INSTANCE.getScene().setX(adjX);
                Game.INSTANCE.getScene().setY(adjY);   
            }
        //}        
    }
}
