/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kickball;

import OHH.Core.Util.Settings;
import kickball.Data.MapParser;
import kickball.Data.MapParser;

/**
 *
 * @author Raymond
 */

public class MapTest {
    public static void main(String[] args){
        System.out.println(Settings.ASSETS_DIR + "test1.map");
        MapParser m = new MapParser(Settings.ASSETS_DIR + "test1.map");
    }
}
