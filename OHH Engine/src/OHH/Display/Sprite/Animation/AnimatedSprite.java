package OHH.Display.Sprite.Animation;

import OHH.Core.GameObjects.Boundary.IBoundaryShape;
import OHH.Core.Interfaces.IEntity;
import OHH.Core.Interfaces.ISprite;
import OHH.Core.Interfaces.ITexture;
import OHH.Core.Util.Color4f;
import OHH.Core.Util.Settings;
import java.util.LinkedList;
import org.lwjgl.util.vector.Vector3f;

public class AnimatedSprite implements ISprite {

    private LinkedList<AnimationFrame> animationFrames = new LinkedList<>();
    private int currentFrame = 0;
    private SpriteBehaviour runnable;

    private float time = 0;
    
    private int width = 0;
    private int height = 0;
    
    public AnimatedSprite(){
    }
    
    public AnimatedSprite(SpriteBehaviour r){
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
    public ISprite resize(int w, int h){
        setWidth(w);
        setHeight(h);
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
    
    public ISprite getSprite(){
        return animationFrames.get(currentFrame).sprite;
    }
    
    @Override
    public IBoundaryShape getBoundary(){
        return getSprite().getBoundary();
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
        
        //run it
        if(runnable != null){
            runnable.run(delta, getAnimationFrame().sprite);
        }
    }
        
    @Override
    public void draw(Vector3f position) {
        if(animationFrames.size()>0)
            getSprite().draw(position);
    }
    
    @Override
    public void drawWireframe(Vector3f position) {
        if(animationFrames.size()>0)
            getSprite().drawWireframe(position);
    }
    
        
    @Override
    public void drawFilled(Vector3f position, Color4f colour) {
        if(animationFrames.size()>0)
            getSprite().drawFilled(position, colour);
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
        return getSprite().getTexture();
    }

    @Override
    public boolean isTiled() {
        return getSprite().isTiled();
    }

    @Override
    public boolean isStretched() {
        return getSprite().isStretched();
    }

    @Override
    public boolean isCentered() {
        return getSprite().isCentered();
    }

    @Override
    public boolean isMaintainingAspectMin() {
        return getSprite().isMaintainingAspectMin();
    }

    @Override
    public boolean isMaintainingAspectMax() {
        return getSprite().isMaintainingAspectMax();
    }
    
    
}
