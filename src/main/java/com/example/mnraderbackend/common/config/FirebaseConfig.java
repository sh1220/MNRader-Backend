package com.example.mnraderbackend.common.config;

import com.example.mnraderbackend.common.firebase.FirebaseInitializer;
import com.google.firebase.FirebaseApp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp firebaseApp() throws Exception {
        FirebaseInitializer.initialize();
        return FirebaseApp.getInstance();
    }
}