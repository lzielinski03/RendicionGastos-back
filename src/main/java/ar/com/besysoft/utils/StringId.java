package ar.com.besysoft.utils;

import java.util.Base64;

/**
 * Created by lzielinski on 11/08/2016.
 */
public class StringId {
    public static String encode(String id) {
        return Base64.getEncoder().encodeToString(id.getBytes());
    }

    public static String decode(String encodedStringId) {
        return new String(Base64.getDecoder().decode(encodedStringId));
    }
}
