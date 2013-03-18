/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package OHH.Core.Util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class Settings {
    public static final int FRAME_RATE_PERSECOND = 60;
    public static final float FRAME_RATE_PERMILLISECOND = 1000/FRAME_RATE_PERSECOND;
    public static final float GRID_SPACE_SIZE = 50.0f;
    public static final float PLAYER_SIZE = GRID_SPACE_SIZE/2;
    public static final String ASSETS_DIR = System.getProperty("user.dir") + "\\src\\OperationHotHammer\\Assets\\";
    public static final float MOVEMENT_SPEED = 4.4f;
    
    public static final float ENTITY_Z_GROUND = 50;
    public static final float ENTITY_Z_CREATURES = 100;
    
    public static final float CAMERA_EASE_TO_POSITION = 24f; //distance divided by: e.g. 1/24 per tick
    public static final float CAMERA_EASE_LOWER_LIMIT = 4.5f;
    public static final float CAMERA_EASE_UPPER_LIMIT = 8f;
    public static final float CAMERA_EASE_MAXDISTANCE = 120f;
    public static final float CAMERA_EASE_MINDISTANCE = 40f;
    
    public static final float PI = 3.1415926535897932384626f;
            
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
