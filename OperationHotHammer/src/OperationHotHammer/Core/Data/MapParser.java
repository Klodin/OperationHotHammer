/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationHotHammer.Core.Data;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author Klodin
 */

public class MapParser {
    
    public MapParser(String dir){
        try {
            File file = new File(dir);
            FileReader in = new FileReader(file);
            Scanner scan = new Scanner(in);
            
            ArrayList<String> queue = new ArrayList<>();
            while (scan.hasNext()){
                queue.add(scan.nextLine());
                int i = 0;
                while (queue.size() > i){
                    switch(queue.get(0)){
                        case "Grid":
                            String[] sizes = scan.nextLine().split(" ");
                            
                            break;
                        default: queue.remove(0); break;
                    }
                }
            }
        } catch (IOException e){
            
        }
    }
}
