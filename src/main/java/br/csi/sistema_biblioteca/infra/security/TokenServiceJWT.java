package br.csi.sistema_biblioteca.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenServiceJWT {
    public String gerarToken(User user){
        try{
            Algorithm algorithm = Algorithm.HMAC256("POO2");
            return JWT.create()
                    .withIssuer("API Sistema Biblioteca")
                    .withSubject(user.getUsername())
                    .withClaim("ROLE", user.getAuthorities().stream().toList().get(0).toString())
                    .withExpiresAt(dataExpiracao())
                    .sign(algorithm);
        }catch (JWTCreationException e){
            throw new RuntimeException("Eror ao gerar o token JWT", e);
        }
    }

    private Instant dataExpiracao(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getSubject(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256("POO2");
            return JWT.require(algorithm)
                    .withIssuer("API Sistema Biblioteca")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e){
            throw new RuntimeException("Token inválido ou expirado");
        }
    }
}