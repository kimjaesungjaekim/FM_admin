package com.developer.fillme.config.jwt;

import com.developer.fillme.exception.BaseException;
import com.developer.fillme.model.GenerateTokenInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.developer.fillme.constant.EException.SECRET_KEY_EXCEPTION;

@Component
public class JwtUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);
    @Value("${spring.security.access-token}")
    private String secretAccessToken;
    @Value("${spring.security.access-token-time}")
    private long tokenExpiryTime;

    @Value("${spring.security.refresh-token}")
    private String secretRefreshToken;
    @Value("${spring.security.refresh-token-time}")
    private long refreshExpiryTime;


    public String extractUsername(String token) {
        return extractClaim(token, secretAccessToken, Claims::getSubject);
    }

    public String generateToken(GenerateTokenInfo info) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", info.getEmail());
        claims.put("username", info.getUsername());
        String assetToken = createToken(claims, info.getUsername(), secretAccessToken, tokenExpiryTime);
        LOGGER.info("[ ACCESS-TOKEN ] - {}", assetToken);
        return assetToken;
    }

    public String generateToken(GenerateTokenInfo info, int expiryTime) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", info.getEmail());
        String assetToken = createToken(claims, info.getUsername(), secretAccessToken, expiryTime);
        LOGGER.info("[ TOKEN-FORGOT-PASSWORD ] - {}", assetToken);
        return assetToken;
    }

    public String refreshToken(String username) {
        Map<String, Object> claims = Map.of("username", username);
        String refreshToken = createToken(claims, username, secretRefreshToken, refreshExpiryTime);
        LOGGER.info("[ REFRESH-TOKEN ] - {}", refreshToken);
        return refreshToken;
    }


    public <T> T extractClaim(String token, String secretKey, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token, secretKey);
        return claimsResolver.apply(claims);
    }

    /**
     * Token creation method
     *
     * @param claims additional information
     * @param subject who this token belongs to
     * @param expirationTime how long this token can last (milliseconds)
     * @return returns a token string
     */
    private String createToken(Map<String, Object> claims, String subject, String secretKey, long expirationTime) {
        return Jwts.builder()
                .subject(subject)
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(generalSigningKey(secretKey))
                .compact();
    }

    /**
     * Method to extract information from JWT
     *
     * @param token JWT token to extract claims
     * @return information inside JWT
     */
    private Claims extractAllClaims(String token, String secretKey) {
        JwtParserBuilder parserBuilder = Jwts.parser().verifyWith(generalSigningKey(secretKey));
        return parserBuilder.build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Method to check the validity of JWT token
     *
     * @param token JWT token to check
     * @param userDetails User details to compare
     * @return boolean true if token is valid, false if invalid
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && isTokenExpired(token, secretAccessToken);
    }

    public boolean isTokenValid(String token) {
        return isTokenExpired(token, secretAccessToken);
    }

    public boolean isReFreshTokenValid(String token) {
        return isTokenExpired(token, secretRefreshToken);
    }

    private boolean isTokenExpired(String token, String secretKey) {
        return !extractExpiration(token, secretKey).before(new Date());
    }

    private Date extractExpiration(String token, String secretKey) {
        return extractClaim(token, secretKey, Claims::getExpiration);
    }

    /**
     * Method to extract JWT from HttpServletRequest
     *
     * @param request HttpServletRequest containing JWT
     * @return JWT token if found, null if not found
     */
    public String parseJwt(HttpServletRequest request) {
        String value = request.getHeader("Authorization");
        if (value != null && value.startsWith("Bearer ")) {
            return value.substring(7);
        }
        return null;
    }

    private SecretKey generalSigningKey(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        if (keyBytes.length < 32) { // 32 bytes = 256 bits
            LOGGER.error("The key byte array is too short. It must be at least 256 bits (32 bytes) long. {}", secretKey);
            throw new BaseException(SECRET_KEY_EXCEPTION);
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String usernameByContext() {
        String username = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            LOGGER.error("Authentication is null");
            return username;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            username = userDetails.getUsername();
        } else {
            // Nếu principal không phải là một instance của UserDetails, ghi log lỗi
            if (principal == null) {
                LOGGER.error("Principal is null");
            } else {
                LOGGER.error("Principal is not an instance of UserDetails, it is an instance of: {}", principal.getClass().getName());
            }
        }
        return username;
    }
}
