/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OHH.Core.Data;
import OHH.Core.GameObjects.Entity;
import java.io.*;
import java.util.Scanner;

/**
 *
 * @author Klodin
 */
public class DataParser {
    public static Entity parseFile(File file){
        Entity entity = null;
        try {
            FileReader in = new FileReader(file);
            Scanner scan = new Scanner(in);
            while (scan.hasNext()){
                String command = scan.nextLine();
                String orders[] = command.split(" ");
                switch(orders[0]){
                    case "type":
                        switch (orders[1]){
                            case "terrain":
                                //entity = new Terrain();
                                break;
                        }
                        break;
                    default:
                        break;
                }
            }
            scan.close();
            in.close();
            
        } catch(IOException e){
            
        }
        return entity;
    }
}
