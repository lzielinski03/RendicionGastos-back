package ar.com.besysoft.utils;

/**
 * Created by lzielinski on 11/08/2016.
 */
public class Json {
    public static String StringFormat(String entry, String objDelimiter, String pairDelimiter) {
        String jsonInfo = "{";
        for(String pair : entry.split(objDelimiter)) {
            String[] keyValue = pair.trim().split(pairDelimiter);
            jsonInfo = jsonInfo + "\"" + keyValue[0] + "\":";
            jsonInfo = jsonInfo + "\"" + keyValue[1] + "\",";
        }
        jsonInfo = jsonInfo.substring(0, jsonInfo.length()-1);
        return jsonInfo + "}";
    }
}
