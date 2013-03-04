package OperationHotHammer.Display.Sprite.Animation;

import OperationHotHammer.Core.Interfaces.IEntity;
import OperationHotHammer.Core.Interfaces.ISprite;
import OperationHotHammer.Core.Interfaces.ITexture;
import OperationHotHammer.Core.Util.Settings;
import java.util.LinkedList;
import org.lwjgl.util.vector.Vector3f;

public class AnimatedSprite implements ISprite {

    private LinkedList<AnimationFrame> animationFrames = new LinkedList<>();
    private int currentFrame = 0;
    private AnimationBehaviour runnable;

    private float time = 0;
    
    private int width = 0;
    private int height = 0;
    
    public boolean hasInitRunnable = false;
    
    public AnimatedSprite(){
    }
    
    public AnimatedSprite(AnimationBehaviour r){
        runnable = r;
    }
    
    public void add(ISprite sprite, float frameDisplayTime){
        animationFrames.add(new AnimationFrame(sprite, frameDisplayTime));
    }
    
    @Override
    public ISprite setWidth(int w) {
        for(int i = 0; i < animationFrames.size(); i++)
            animationFrames.get(i).sprite.setWidth(w);
        width = w;
        return this;
    }

    @Override
    public ISprite setHeight(int h) {
        for(int i = 0; i < animationFrames.size(); i++)
            animationFrames.get(i).sprite.setHeight(h);
        height = h;
        return this;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
    
    @Override
    public void update(float delta, IEntity e) {
        
        if(animationFrames.size() > 1) {
            time+=delta*Settings.FRAME_RATE_MILLISECONDS;

            AnimationFrame af;
            if(time >= (af = animationFrames.get(currentFrame)).time) {
                time -= af.time;
                currentFrame = ++currentFrame % animationFrames.size();
            }
        }
        
        //init it on first loop
        if(runnable != null && !hasInitRunnable) {
            runnable.init(this, e);
            hasInitRunnable = true;
        }
        
        //run it
        if(runnable != null){
            runnable.run(delta, this, e);
        }
    }
        
    @Override
    public void draw(Vector3f position) {
        if(animationFrames.size()>0)
            animationFrames.get(currentFrame).sprite.draw(position);
    }
    
    public AnimationFrame getAnimationFrame(int index){
        return animationFrames.get(index);
    }
    
    public AnimationFrame getAnimationFrame(){
        return animationFrames.get(currentFrame);
    }
    
    public int getAnimationFrameCount(){
        return animationFrames.size();
    }
    
    public ITexture getTexture(){
        return animationFrames.get(currentFrame).sprite.getTexture();
    }
}
