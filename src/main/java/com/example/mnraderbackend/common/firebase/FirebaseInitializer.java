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
        String path = "/home/ubuntu/firebase-key.json"; // 실제 경로
        FileInputStream serviceAccount = new FileInputStream(path);




        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);


    }
}