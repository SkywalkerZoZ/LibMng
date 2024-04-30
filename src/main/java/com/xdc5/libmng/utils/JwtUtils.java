package com.xdc5.libmng.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

public class JwtUtils {
    private static final String SECRET_KEY = "skywalker@gmail.com_herobrinex@163.com_qwer4396";
    //30天
    private static final long EXPIRATION_TIME = 1000L *60*60*24*30;

    public static String generateToken(HashMap<String,Object> claims) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        return Jwts.builder()
                .addClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public static boolean validateToken(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public static String extractAttribute(String token,String attr) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        return claimsJws.getBody().get(attr).toString();
    }

//    public static void main(String[] args) {
//        HashMap<String,Object> claims=new HashMap<>();
//        claims.put("username","skywalker");
//        claims.put("password","qwer4396");
//        String token = JwtUtils.generateToken(claims);
//        System.out.println("Generated Token: " + token);
//
//        if (JwtUtils.validateToken(token)) {
//            System.out.println("Token is valid");
//            String extracted = JwtUtils.extractAttribute(token,"username");
//            System.out.println("Extracted : " + extracted);
//        } else {
//            System.out.println("Token is invalid");
//        }
//    }
}
