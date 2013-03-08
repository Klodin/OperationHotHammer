/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kickball;

/**
 *
 * @author Klodin
 */
public class DrawStadium{
    public String stadiumGrid[][];
    public int width;
    public int height;
    
    public DrawStadium(int width, int height){
        this.width = width;
        this.height = height;
        initialize();
    }
    
    public DrawStadium(){
        this.width = 40;
        this.height = 50;
        initialize();
    }
    
    private void initialize(){
        this.stadiumGrid = new String[width][height];
        String path = "KickBall/Assets/";
        for (int i = 0; i < this.stadiumGrid.length; i++){
            for (int j = 0; j < this.stadiumGrid[i].length; j++){
                this.stadiumGrid[i][j] = path + "Terrain/grass.png";
            }
        }
    }
}
