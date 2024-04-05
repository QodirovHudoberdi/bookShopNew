package com.company.bookShop.utils;

import com.company.bookShop.dto.login.LoginResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Base64;
import java.util.Date;

public class JwtUtil {
    private static final String secretKey = "2D4A614E645267556B58703273357638792F423F4428472B4B6250655368566D";

    public static String encode(String username) {
        byte[] decodedSecret = Base64.getDecoder().decode(secretKey);


        return Jwts.builder()
                .setSubject(username)
                .setAudience("Solr")
                .signWith(SignatureAlgorithm.HS256, decodedSecret).compact();
    }

    public static Long decode(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return (Long) claims.get("id");
    }

    public static String getRefreshToken(String usPas) {
        byte[] decodedSecret = Base64.getDecoder().decode(secretKey);


        return Jwts.builder()
                .setSubject(usPas)
                .setAudience("Solr")
                .signWith(SignatureAlgorithm.HS256, decodedSecret).compact();
    }

    public static String getCurrentUserName(){
        return SecurityContextHolder.getContext().getAuthentication().getName();

    }
}