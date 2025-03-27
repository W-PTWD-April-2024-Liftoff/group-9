package com.parkrangers.parkquest_backend.services;

import com.parkrangers.parkquest_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
//                .build();

    @Value("${google.client-id")
    private String googleClientId;
//            throw new Exception("Invalid Google token.");

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    public User verifyGoogleToken(String token) throws Exception {
//        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
//                new NetHttpTransport(), new GsonFactory())
//                .setAudience(Collections.singletonList(googleClientId))
//
//        GoogleIdToken idToken = verifier.verify(token);
//        if (idToken == null) {
//        }
//
//        GoogleIdToken.Payload payload = idToken.getPayload();
//        String googleId = payload.getSubject();
//        String name = (String)
//    }
}
