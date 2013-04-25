/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kickball;

import OHH.Core.GameObjects.Entities.Terrain;
import OHH.Core.GameObjects.Entity;
import java.util.Arrays;
import kickball.Data.TextReader;

/**
 *
 * @author Klodin
 */
public class EntityLoader {
    public Entity loadEntity(String path, NewInterface runnable){
        TextReader reader = new TextReader(path);
        
        Terrain t = null;
        String nextLine;
        nextLine = reader.get();
        while (nextLine != null){
            String commandLine[] = nextLine.split(" ");
            String command = commandLine[0];
            String arguments[] = new String[0];
            if (commandLine.length > 1){
                 arguments = Arrays.copyOfRange(commandLine, 1, commandLine.length);
            }
            //CUSTOM CODE GOES HERE
            runnable.run(command, arguments);
            
            nextLine = reader.get();
        }
        return t;
    }
}
