/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kickball;

import OHH.Core.Util.Settings;
import kickball.Data.TextReader;
import kickball.Data.TextReader;

public class MapTest {
    public static void main(String[] args){
        String directory = System.getProperty("user.dir") + "\\src\\kickball\\Assets\\test1.map";
        System.out.println(directory);
        TextReader m = new TextReader(directory);
        String s;
        s = m.get();
        while (s != null){
            System.out.println(s);
            s = m.get();
        }
    }
}
