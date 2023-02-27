package com.likelion.Demojwt.Security;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.util.ToStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserService customUserDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
            try {
                //lay jwt tu request
                String jwt = getJwtFromRequest(request);
                if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)){
                    //lay id tu chuoi jwt
                    Long userId = tokenProvider.getUserIdFromJWT(jwt);
                    //lay thong tin nguoi dung tu id
                    UserDetails userDetails = customUserDetailsService.loadUserById(userId);
                    if (userDetails != null){
                        //neu nguoi dung ko hop le , set thong tin cho security Context
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }catch (Exception ex){
                log.error("failed on set user authentication ",ex);
            }
            filterChain.doFilter(request,response);
    }
    private String getJwtFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("bearer")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
