package ar.com.besysoft.utils;

import ar.com.besysoft.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by lzielinski on 05/08/2016.
 */
public class JsonParser {
    public static String toJsonString(User user) {
        String jsonString = null;
        try {
            jsonString = new ObjectMapper().writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public static User toObject(String json) {
        User user = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            user = mapper.readValue(json, User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }
}
