/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kickball;

import kickball.Data.TextReader;
import java.util.Arrays;
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
    
      private Integer str2Int(String s){
      return Integer.parseInt(s);
    }

    private void initialize(){
        this.stadiumGrid = new String[width][height];
        String path = "KickBall/Assets/";
        String directory = System.getProperty("user.dir") + "\\src\\kickball\\Assets\\test1.map";
        TextReader reader = new TextReader(directory);
        String nextLine;
        nextLine = reader.get();
        String floor = "grass.png";
        
        /*
        for (int i = 0; i < this.stadiumGrid.length; i++){
            for (int j = 0; j < this.stadiumGrid[i].length; j++){
                this.stadiumGrid[i][j] = path + "Terrain/Water1.png";
            }
        }
        * Draws a default map 
        * 
        */
        
        while (nextLine != null){
            
            String commandLine[] = nextLine.split(" ");
            String command = commandLine[0];
            String arguments[] = new String[0];
            if (commandLine.length > 1){
                 arguments = Arrays.copyOfRange(commandLine, 1, commandLine.length);
            }
            
             switch(command){
                case "setSize":
                    if (arguments.length < 2){
                        System.out.println("Insufficient arguments for command: " + command);
                    } else {
                        System.out.println("Let's make a " + arguments[0] + " x " + arguments[1] + " grid!");
                        this.stadiumGrid = new String[str2Int(arguments[0])][str2Int(arguments[1])];
                    }
                    break;
                case "drawLine":
                    if (arguments.length < 5){
                        System.out.println("Insufficient arguments for command: " + command);
                    } else {
                        floor = arguments[5];
                        if (arguments[0].equals(arguments[2])){
                            System.out.println("Let's draw a Vertical line from (" + arguments[0] + ", " + arguments[1] + ") to ("  + arguments[2] + ", " + arguments[3] + ")");
                            if(str2Int(arguments[1]) < str2Int(arguments[3])){
                                for (int i = str2Int(arguments[1]), j = str2Int(arguments[0]), k = str2Int(arguments[3]); i < k; i++){
                                    this.stadiumGrid[j][i] = path + "Terrain/"+ floor;    
                                } 
                              } 
                            else if(str2Int(arguments[1]) > str2Int(arguments[3])){
                                for (int i = str2Int(arguments[3]), j = str2Int(arguments[0]), k = str2Int(arguments[1]); i < k; i++){
                                  this.stadiumGrid[j][i] = path + "Terrain/"+ floor;
                              }  
                            }
                    }
                      else if (arguments[1].equals(arguments[3])){
                            System.out.println("Let's draw a Horizontal line from (" + arguments[0] + ", " + arguments[1] + ") to ("  + arguments[2] + ", " + arguments[3] + ")");
                            if(str2Int(arguments[0]) < str2Int(arguments[2])){
                                for (int i = str2Int(arguments[0]), j = str2Int(arguments[1]), k = str2Int(arguments[2]); i < k; i++){
                                    this.stadiumGrid[i][j] = path + "Terrain/"+ floor;    
                                }
                            }
                            else if(str2Int(arguments[0]) > str2Int(arguments[2])){
                              for (int i = str2Int(arguments[2]), j = str2Int(arguments[1]), k = str2Int(arguments[0]); i < k; i++){
                                  this.stadiumGrid[i][j] = path + "Terrain/"+ floor;
                              }  
                            }  
                      }
                         else {
                            System.out.println("Silly user, that's not a line!");
                        }
                    }
                    break;
                default:
                    System.out.println("Unrecognized Command: " + command);
                    break;
            }
            nextLine = reader.get();
        }
      }
   }


  