package com.company.bookShop.utils;

import com.company.bookShop.dto.login.LoginResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Base64;
import java.util.Date;

public class JwtUtil {
    private static final String secretKey = "2D4A614E645267556B58703273357638792F423F4428472B4B6250655368566D";

    public static String encode(String username) {
        byte[] decodedSecret = Base64.getDecoder().decode(secretKey);

        String jwtToken = Jwts.builder()
                .setSubject(username)
                .setAudience("Solr")
                .signWith(SignatureAlgorithm.HS256, decodedSecret).compact();


        return jwtToken;
    }

    public static Long decode(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        Long id = (Long) claims.get("id");
        return id;
    }

    public static String getRefreshToken(LoginResponseDto loginResponseDto) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000)));
        jwtBuilder.setIssuer("Bearer");
        jwtBuilder.signWith(SignatureAlgorithm.HS256, secretKey);
        jwtBuilder.claim("loginResponseDto", loginResponseDto);
        return jwtBuilder.compact();
    }
}