package com.jiwon.firebasePracticeServer.auth;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.stereotype.Component;

@Component
public class FirebaseTokenVerifier {


    // FirebaseToken 객체에 들어 있는 거
        // 1. uid (핵심 식별자)
        // 2. email (있다면)
        // 3. name (제공자에 따라)
        // 4. claims (커스텀 클레임 등)
    public FirebaseToken verify(String idToken) throws FirebaseAuthException {
        // JWT 형식의 ID Token을 받아서 Firebase 공개키/서명 정보를 이용해 서명이 유효한지 검증
        // 토큰의 iss/aud/exp 같은 값으로 우리 프로젝트 토큰이 맞는지, 만료되지 않았는지 확인
        // 검증이 끝나면 FirebaseToken(= 해석된 토큰 정보)을 반환
        return FirebaseAuth.getInstance().verifyIdToken(idToken);
    }
}
