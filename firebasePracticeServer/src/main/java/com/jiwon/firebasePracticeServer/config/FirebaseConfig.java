package com.jiwon.firebasePracticeServer.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

/**
 * 이 클래스는 애플리케이션 시작 시 Firebase Admin SDK를 메모리에 올려두고
 * 이후 FirebaseAuth.getInstance()가 언제든 사용 가능하게 만드는 사전 준비 담당자
 *
 * 언제 호출되나?
 * 애플리케이션이 기동될 때,
 * 1. SpringApplication.run(...) 실행
 * 2. Spring이 모든 Bean을 생성
 * 3. @Configuration 클래스도 Bean으로 등록
 * 4. Bean 생성 완료 직후
 * 👉 @PostConstruct 붙은 메서드 자동 호출
 * 5. 서버 정상 기동 완료
 *
 * 즉, 서버가 “READY” 상태가 되기 직전에
 * Spring Framework 내부에서 Spring의 Bean Lifecycle 관리 로직이 호출
 * @Configuration + @PostConstruct 조합: 서버 시작 전에 빈 등록하고 자동 호출하는 설정 함수
 */
@Configuration
public class FirebaseConfig {

    // Spring Framework 내부에서 호출
    @PostConstruct // “이 Bean이 생성되고, 의존성 주입이 끝난 뒤 자동으로 한 번 실행해라”
    public void init() throws IOException {
        InputStream serviceAccount =
                new ClassPathResource("firebase/firebase-admin.json").getInputStream();

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }
}
