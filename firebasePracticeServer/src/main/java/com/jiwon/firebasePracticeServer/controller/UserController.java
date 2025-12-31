package com.jiwon.firebasePracticeServer.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.jiwon.firebasePracticeServer.auth.FirebaseTokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final FirebaseTokenVerifier verifier;

    @GetMapping("/api/protected")
    public ResponseEntity<String> protectedApi(
            @RequestHeader("Authorization") String authorization
    ) throws FirebaseAuthException {

        // 토큰에 Bearer 헤더가 붙어 있으니까 없애주기 위해서 replace 함수를 이용해서 삭제해줌
        String token = authorization.replace("Bearer ", "");
        FirebaseToken decodedToken = verifier.verify(token);

        return ResponseEntity.ok("Hello " + decodedToken.getUid());
    }
}
