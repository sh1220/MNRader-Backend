package com.example.mnraderbackend.common.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FirebaseInitializer {

    public static void initialize() throws Exception {
        String rawJson = System.getenv("FIREBASE_CREDENTIALS_JSON");

        if (rawJson == null || rawJson.trim().isEmpty()) {
            throw new IllegalStateException("FIREBASE_CREDENTIALS_JSON 환경변수가 없습니다.");
        }
        String json = System.getenv("FIREBASE_CREDENTIALS_JSON");
        InputStream serviceAccount = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);


        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .build();

        FirebaseApp.initializeApp(options);
    }
}