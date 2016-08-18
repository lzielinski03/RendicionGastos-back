package ar.com.besysoft.security;

import ar.com.besysoft.entity.User;
import ar.com.besysoft.utils.JsonParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by lzielinski on 18/07/2016.
 */
public class Token {

    private byte[] key;
    private String jwt;
    private final String salt = "besy1234";
    private User user;

    public Token(User user) {
        this.user = user;
        initToken();
        this.jwt = Jwts.builder()
                .setSubject("{\"user\": " + JsonParser.toJsonString(user) + "}")
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public Token(String jwt) {
        initToken();
        this.jwt = jwt;
    }

    private void initToken() {
        try {
            key = (salt).getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-512");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String getJwt() {
        return jwt;
    }

    public User getUser() {
        //System.out.println(Jwts.parser().setSigningKey(this.key).parseClaimsJws(this.jwt).getBody().values());
        String chunkUser = Jwts.parser().setSigningKey(this.key).parseClaimsJws(this.jwt).getBody().getSubject();
        return JsonParser.toObject(chunkUser.substring(9, chunkUser.length()-1));
    }

    public static String getJwt(User user) {
        byte[] key = null;
        try {
            key = ("besy1234").getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-512");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return  Jwts.builder()
                .setSubject("{\"user\": " + JsonParser.toJsonString(user) + "}")
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }
}