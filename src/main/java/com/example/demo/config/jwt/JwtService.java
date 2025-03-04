package com.example.demo.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.dto.ProfileResponse;
import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Component
public class JwtService {

    @Value("${security.secret.key}")
    private String secretKey;

    private final UserRepo userRepo;

    @Autowired
    public JwtService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public String generateToken(User user) {
        return JWT.create()
                .withClaim("id", user.getId())
                .withClaim("email", user.getEmail())
                .withIssuedAt(ZonedDateTime.now().toInstant())
                .withExpiresAt(ZonedDateTime.now().plusMinutes(10000).toInstant())
                .sign(Algorithm.HMAC256(secretKey));
    }

    public User verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String email = decodedJWT.getClaim("email").asString();

        return userRepo.findUserByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    //-------------------

    public ProfileResponse getProfile() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String email = authentication.getName();
       User user = userRepo.findUserByEmail(email).orElseThrow(() -> new EntityNotFoundException(String.format("User with email %s not found", email)));

        return new ProfileResponse(user.getId(),user.getEmail(),user.ownGetName());
    }




}
