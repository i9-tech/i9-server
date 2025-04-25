package school.sptech.config;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;

public class GerenciadorTokenJwt {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.validity}")
    private String jwtTokenValidity;

//    public String getUsernameFromToken(String token){
//        return getClaimForToken(token, Claims::getSubject);
//    }
//
//    public Date getExpi(String token){
//        return getClaimForToken(token, Claims::getSubject);
//    }


}
