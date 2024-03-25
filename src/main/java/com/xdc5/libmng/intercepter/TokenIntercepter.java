package com.xdc5.libmng.intercepter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.xdc5.libmng.entity.Result;
import com.xdc5.libmng.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class TokenIntercepter implements HandlerInterceptor {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURL().toString();
        log.info("request url: {}", url);

        String jwt = request.getHeader("Authorization");
        if (!JwtUtils.validateToken(jwt)) {
            log.info("NOT_LOGIN");
            Result error = Result.error("NOT_LOGIN");
            String notLogin = objectMapper.writeValueAsString(error);
            response.getWriter().write(notLogin);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        try {
            // 解析userId
            String userIdStr = JwtUtils.extractAttribute(jwt, "userId");
            String userRole= JwtUtils.extractAttribute(jwt,"userRole");
            Integer userId = Integer.parseInt(userIdStr);
            request.setAttribute("userId", userId);
            request.setAttribute("userRole",userRole);
            log.info("parsed userId: "+userId);
        } catch (Exception e) {
            response.getWriter().write(jwt);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }


        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterCompletion");
    }
}
