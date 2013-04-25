/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kickball.Data;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author Klodin
 */

public class TextReader {
    private Scanner scan;
    private File file;
    private FileReader in;
    
    public TextReader(String dir){
        try {
            this.file = new File(dir);
            this.in = new FileReader(file);
            this.scan = new Scanner(in);
        } catch (IOException e){
            System.out.println(e);
        }
    }
    
    public String get(){
        String getItem = null;
        try {
            if (scan.hasNext()){
                getItem = scan.nextLine();
            } else {
                this.scan.close();
                this.in.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return getItem;
    }
}
