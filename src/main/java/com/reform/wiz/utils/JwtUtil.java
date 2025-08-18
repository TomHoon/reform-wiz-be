package com.reform.wiz.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

@Component
@Log4j2
public class JwtUtil {

    private static String key = "hellothisisreformwizwelcometoreformworldexcitingisnit";

    // JWT token 생성
    public String createToken(Map<String, Object> valueMap, int min) {
        SecretKey key = null;
        try {
            key = Keys.hmacShaKeyFor(JwtUtil.key.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }

        return Jwts.builder().header()
                .add("typ", "JWT")
                .add("alg", "HS256")
                .and()
                .issuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .expiration((Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))).claims(valueMap)
                .signWith(key)
                .compact();
    }

    public Map<String, Object> validateToken(String token) {
        SecretKey key = null;

        try {
            key = Keys.hmacShaKeyFor(JwtUtil.key.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }

        Claims claims = Jwts.parser().verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        log.info("claims:: {}", claims);
        return claims;
    }
}
