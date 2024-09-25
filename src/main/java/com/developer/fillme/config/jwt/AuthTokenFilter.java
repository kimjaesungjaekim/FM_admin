package com.developer.fillme.config.jwt;

import com.developer.fillme.config.security.UserInfoServiceImpl;
import com.developer.fillme.entity.TokenEntity;
import com.developer.fillme.exception.BaseException;
import com.developer.fillme.repository.ITokenRepo;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.Objects;

import static com.developer.fillme.constant.EException.TOKEN_NOT_EXIST;

@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;
    private final ITokenRepo tokenRepo;
    private final UserInfoServiceImpl userInfoService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) {
        var jwtToken = jwtUtil.parseJwt(request);
        try {
            if (jwtToken == null) {
                filterChain.doFilter(request, response);
                return;
            }

            var username = jwtUtil.extractUsername(jwtToken);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (username != null && authentication == null) {
                var userDetails = userInfoService.loadUserByUsername(username);

                if (jwtUtil.isTokenValid(jwtToken, userDetails)) {

                    TokenEntity status = tokenRepo.findByAccessToken(jwtToken);
                    if (Objects.isNull(status)) {
                        throw new BaseException(TOKEN_NOT_EXIST);
                    }

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,                  // Principal: info User
                            null,                         // Credentials: user password (not required if using JWT)
                            userDetails.getAuthorities()  // Authorities: List Authorities
                    );
                    // Set authentication details from HTTP request
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // Set Authentication to SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}
