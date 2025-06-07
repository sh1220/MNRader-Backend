package com.example.mnraderbackend.common.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class FirebaseInitializer {

    public static void initialize() throws Exception {
        String json = System.getenv("FIREBASE_CREDENTIALS_JSON");

        if (json == null) {
            throw new IllegalStateException("FIREBASE_CREDENTIALS_JSON 환경변수가 없습니다.");
        }

        ByteArrayInputStream serviceAccount = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
    }
}