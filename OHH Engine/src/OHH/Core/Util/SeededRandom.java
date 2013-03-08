/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OHH.Core.Util;

/**
 *
 * @author Kaitlyn
 */
class SeededRandom {
    private final static long constant = 2 ^ 13 + 1;
    private final static long prime = 37;
    private final static long maximum = 2 ^ 50;
    
    private long seed;
    
    public SeededRandom(long seed){
        this.seed = seed;
    }
    
    public long next(){
        seed *= constant;
        seed += prime;
        seed %= maximum;
        return seed;
    }
    
    public void setSeed(long seed){
        this.seed = seed;
    }
}
 