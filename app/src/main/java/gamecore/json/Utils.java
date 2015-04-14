package gamecore.json;

import org.json.JSONObject;


public class Utils {
        public static boolean contains(JSONObject jsonObject, String key) {
            if (jsonObject!=null) {
                return jsonObject.has(key) && !jsonObject.isNull(key);
            }else{
                return false;
            }
        }
}
