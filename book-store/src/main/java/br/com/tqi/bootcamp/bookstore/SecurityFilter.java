package br.com.tqi.bootcamp.bookstore;

import br.com.tqi.bootcamp.bookstore.exception.UnauthorizedException;
import java.util.Base64;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

//@Component
//@Slf4j
//public class SecurityFilter implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//
//        checkAuthentication(request.getHeader(HttpHeaders.AUTHORIZATION));
//
//        return true;
//    }
//
//    private void checkAuthentication(final String authentication) {
//        if (authentication == null) {
//
//            throw new UnauthorizedException();
//        } else {
//            validateUser(authentication);
//        }
//    }
//
//    private void validateUser(final String authentication) {
//        String encodedCredential = removePrefix(authentication);
//        String decode = new String(Base64.getDecoder().decode(encodedCredential));
//        final String user = decode.split(":")[0];
//        final String password = decode.split(":")[1];
//
//        if (!user.equals("bootcamp") || !password.equals("vempratqi"))
//            throw new UnauthorizedException();
//
//    }
//
//    private String removePrefix(final String authorization) {
//        return authorization.replace("Basic ", "");
//    }
//}
