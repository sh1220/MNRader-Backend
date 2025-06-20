package com.example.mnraderbackend.common.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FirebaseInitializer {

    public static void initialize() throws Exception {
        String path = System.getenv("FIREBASE_CREDENTIALS_JSON");
        if (path == null) {
            throw new IllegalStateException("FIREBASE_CREDENTIALS_JSON 환경변수가 설정되어 있지 않습니다.");
        }
        InputStream serviceAccount = new FileInputStream(path);


        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);


    }
}