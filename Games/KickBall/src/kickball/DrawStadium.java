/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kickball;

import OHH.Core.GameObjects.Entities.Terrain;
import OHH.Core.Interfaces.ITexture;
import OHH.Display.Sprite.Sprite;
import java.util.Arrays;
/**
 *
 * @author Klodin
 */
public class DrawStadium{
    public Terrain stadiumGrid[][];
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
        this.stadiumGrid = new Terrain[width][height];
        
        final String path = "KickBall/Assets/";
        String directory = System.getProperty("user.dir") + "\\src\\kickball\\Assets\\test1.map";
        String floor = "default.png";
        for (Terrain[] line : stadiumGrid){
            Terrain t = new Terrain();
            t.label = path + floor;
            Arrays.fill(line, t);
        }
        
        EntityLoader el = new EntityLoader();
        
        el.loadEntity(directory, new NewInterface(){
            @Override
            public void run(String command, String[] arguments){
                switch(command){
                    case "setSize":
                        if (arguments.length < 2){
                            System.out.println("Insufficient arguments for command: " + command);
                        } else {
                            System.out.println("Let's make a " + arguments[0] + " x " + arguments[1] + " grid!");
                            stadiumGrid = new Terrain[Integer.parseInt(arguments[0])][Integer.parseInt(arguments[1])];
                        }
                        break;
                    case "drawLine":
                        if (arguments.length < 5){
                            System.out.println("Insufficient arguments for command: " + command);
                        } else {
                            if (arguments[0].equals(arguments[2])){
                                System.out.println("Let's draw a Vertical line from (" + arguments[0] + ", " + arguments[1] + ") to ("  + arguments[2] + ", " + arguments[3] + ") with " + arguments[4]);
                                if(Integer.parseInt(arguments[1]) < Integer.parseInt(arguments[3])){
                                    for (int i = Integer.parseInt(arguments[1]), j = Integer.parseInt(arguments[0]); i < Integer.parseInt(arguments[3]); i++){
                                        stadiumGrid[j][i] = setTerrain(path + "Terrain/"+ arguments[4], j, i);
                                    } 
                                } else if(Integer.parseInt(arguments[1]) > Integer.parseInt(arguments[3])){
                                    for (int i = Integer.parseInt(arguments[3]), j = Integer.parseInt(arguments[0]); i < Integer.parseInt(arguments[1]); i++){
                                        stadiumGrid[j][i] = setTerrain(path + "Terrain/"+ arguments[4], j, i);
                                    }  
                                }
                            } else if (arguments[1].equals(arguments[3])){
                                System.out.println("Let's draw a Horizontal line from (" + arguments[0] + ", " + arguments[1] + ") to ("  + arguments[2] + ", " + arguments[3] + ") with " + arguments[4]);
                                if(Integer.parseInt(arguments[0]) < Integer.parseInt(arguments[2])){
                                    for (int i = Integer.parseInt(arguments[0]), j = Integer.parseInt(arguments[1]); i < Integer.parseInt(arguments[2]); i++){
                                        stadiumGrid[i][j] = setTerrain(path + "Terrain/"+ arguments[4], i, j);
                                    }
                                } else if(Integer.parseInt(arguments[0]) > Integer.parseInt(arguments[2])){
                                    for (int i = Integer.parseInt(arguments[2]), j = Integer.parseInt(arguments[1]); i < Integer.parseInt(arguments[0]); i++){
                                        stadiumGrid[i][j] = setTerrain(path + "Terrain/"+ arguments[4], i, j);
                                    }  
                                }  
                            } else {
                                System.out.println("Silly user, that's not a line!");
                            }
                        }
                        break;
                    default:
                        System.out.println("Unrecognized Command: " + command);
                        break;
                }
            }
        });
    }
    private Terrain setTerrain(String terrainPath, float x, float y){
        //Sprite pic = new Sprite(stadium[i][j], ITexture.STRETCH | ITexture.MAINTAIN_ASPECT_MIN);
        Terrain tile = new Terrain(x, y);
        //tile.attach(pic);
        tile.label = terrainPath;
        return tile;
    }
    
    
}