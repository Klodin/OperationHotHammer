/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package OperationHotHammer.Core.Util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class Settings {
    public static final float MAXSPEED = 500;
    public static final float MINSPEED = 1;
    public static final int FRAME_RATE_SECONDS = 60;
    public static final float FRAME_RATE_MILLISECONDS = 1000/FRAME_RATE_SECONDS;
    public static final float GRID_SPACE_SIZE = 50.0f;
    public static final float PLAYER_SIZE = GRID_SPACE_SIZE/2;
    public static final String ASSETS_DIR = System.getProperty("user.dir") + "\\src\\OperationHotHammer\\Assets\\";
    public static final float MOVEMENT_SPEED = 6;
    public static final Map<String, String> DATA_MAP;
    static {
        Map<String, String> map = new HashMap<>();
        map.put("grass1", "grass1.dat");
        map.put("grass2", "grass2.dat");
        DATA_MAP = Collections.unmodifiableMap(map);
    }
    public static final Map<String, String> TEXTURE_MAP;
    static {
        Map<String, String> map = new HashMap<>();
        map.put("grass1", "grass1.dat");
        map.put("grass2", "grass2.dat");
        TEXTURE_MAP = Collections.unmodifiableMap(map);
    }
}
