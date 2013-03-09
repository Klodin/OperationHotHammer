package OHH.Display.Sprite.Animation;

import OHH.Core.Interfaces.IEntity;
import OHH.Core.Interfaces.ISprite;
import OHH.Core.Interfaces.ITexture;
import OHH.Core.Util.Settings;
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
    
    public void addSprite(ISprite sprite, float frameDisplayTime){
        animationFrames.add(new AnimationFrame(sprite, frameDisplayTime));
    }
    
    @Override
    public void setWidth(int w) {
        for(int i = 0; i < animationFrames.size(); i++)
            animationFrames.get(i).sprite.setWidth(w);
        width = w;
    }

    public void setHeight(int h) {
        for(int i = 0; i < animationFrames.size(); i++)
            animationFrames.get(i).sprite.setHeight(h);
        height = h;
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
            time+=delta*Settings.FRAME_RATE_PERMILLISECOND;

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
            getAnimationFrame().sprite.draw(position);
    }
    
    @Override
    public void drawWireframe(Vector3f position) {
        if(animationFrames.size()>0)
            getAnimationFrame().sprite.drawWireframe(position);
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
        return getAnimationFrame().sprite.getTexture();
    }

    @Override
    public boolean isTiled() {
        return getAnimationFrame().sprite.isTiled();
    }

    @Override
    public boolean isStretched() {
        return getAnimationFrame().sprite.isStretched();
    }

    @Override
    public boolean isCentered() {
        return getAnimationFrame().sprite.isCentered();
    }

    @Override
    public boolean isMaintainingAspectMin() {
        return getAnimationFrame().sprite.isMaintainingAspectMin();
    }

    @Override
    public boolean isMaintainingAspectMax() {
        return getAnimationFrame().sprite.isMaintainingAspectMax();
    }
    
    
}
