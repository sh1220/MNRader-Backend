package com.example.mnraderbackend.common.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class FirebaseInitializer {

    public static void initialize() throws Exception {
        String rawJson = System.getenv("FIREBASE_CREDENTIALS_JSON");

        if (rawJson == null || rawJson.trim().isEmpty()) {
            throw new IllegalStateException("FIREBASE_CREDENTIALS_JSON 환경변수가 없습니다.");
        }

        // 역슬래시와 큰따옴표가 포함된 문자열을 정상 JSON으로 복원
        String json = rawJson.replace("\\\"", "\"").replace("\\\\n", "\\n");


        ByteArrayInputStream serviceAccount = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
    }
}