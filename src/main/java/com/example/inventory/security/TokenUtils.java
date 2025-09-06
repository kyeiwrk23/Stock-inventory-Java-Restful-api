package com.example.inventory.security;

import com.example.inventory.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class TokenUtils {

    private static  final Logger log = LoggerFactory.getLogger(TokenUtils.class);

    @Value(value = "${spring.secreteKey}")
    private String seceretKey;

    @Value(value = "${spring.expirationMs}")
    private int expirationMs;

    @Value(value = "${spring.cookieName}")
    private String cookieName;

    @Autowired
    private UserRepository  userRepository;

    public String getTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }

    public ResponseCookie generateResponseCookie(UserDetailsImpl userDeatails){
        String jwt = generateTokenFromUsername(userDeatails.getUsername());
        ResponseCookie responseCookie = ResponseCookie.from(cookieName,jwt)
                .path("/api/inventory")
                .maxAge(24 * 60 * 60)
                .httpOnly(false)
                .build();

        return responseCookie;
    }

    public ResponseCookie cleanResponseCookie(){
        ResponseCookie responseCookie = ResponseCookie.from(cookieName,null)
                .path("/api/inventory")
                .build();

        return responseCookie;
    }

    public String getTokenFromCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, cookieName);
        if(cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .subject(username)
                .expiration(new Date( new Date().getTime() + expirationMs))
                .signWith(key())
                .issuedAt(new Date())
                .compact();
    }



    public String generateUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try{
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parseSignedClaims(token);
            return true;
        }catch (MalformedJwtException | IllegalArgumentException e){
            log.error("Invalid JWT token {}",e.getMessage());
        }catch (ExpiredJwtException e){
            log.error("Expired JWT token {}",e.getMessage());
        } catch (UnsupportedJwtException e)
        {
            log.error("Unsupported JWT token {}",e.getMessage());
        }
        return false;
    }

    public Key key()
    {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(seceretKey));
    }
}
