/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kickball;

import OHH.Core.Util.Settings;
import java.util.Arrays;
import kickball.Data.TextReader;
import kickball.Data.TextReader;

public class MapTest {
    public static void main(String[] args){
        String directory = System.getProperty("user.dir") + "\\src\\kickball\\Assets\\test1.map";
        System.out.println(directory);
        TextReader reader = new TextReader(directory);
        String nextLine;
        nextLine = reader.get();
        while (nextLine != null){
            String commandLine[] = nextLine.split(" ");
            String command = commandLine[0];
            String arguments[] = Arrays.copyOfRange(commandLine, 1, commandLine.length - 1);
            switch(command){
                case "setSize":
                    if (arguments.length < 2){
                        System.out.println("Insufficient arguments");
                    } else {
                        System.out.println("Let's make a " + arguments[0] + " x " + arguments[1] + " grid!");
                    }
                    break;
                case "drawLine":
                    if (arguments.length < 4){
                        System.out.println("Insufficient arguments");
                    } else {
                        if (arguments[0] == arguments[2]){
                            System.out.println("Let's draw a Horizontal line from (" + arguments[0] + ", " + arguments[1] + ") to ("  + arguments[0] + ", " + arguments[1] + ")");
                        } else if (arguments[1] == arguments[3]){
                            System.out.println("Let's draw a Vertical line from (" + arguments[0] + ", " + arguments[1] + ") to ("  + arguments[0] + ", " + arguments[1] + ")");
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
    }
}
