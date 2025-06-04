package com.example.mnraderbackend.domain.user.service;

import com.example.mnraderbackend.common.argument_resolver.PreAuthorize;
import com.example.mnraderbackend.common.model.User;
import com.example.mnraderbackend.domain.user.repository.UserRepository;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void registerFcmToken(Long userId, String fcmToken) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        user.setFcmToken(fcmToken);
        // userRepository.save(user); ← 영속성 컨텍스트에서 자동 업데이트 됨
    }

    public void sendLostAlarm(@PreAuthorize String userId, Long regionId) {
        sendFirebaseMessage("동물 실종", "현 지역에서 동물이 실종되었습니다.", regionId);
    }

    public void sendSightAlarm(@PreAuthorize String userId, Long regionId) {
        sendFirebaseMessage("유기동물 발견", "현 지역에서 동물이 발견되었습니다.", regionId);
    }
    private void sendFirebaseMessage(String title, String content, Long regionId) {
        List<String> registrationTokens = userRepository.findFcmTokensByRegionId(regionId);
        MulticastMessage message = MulticastMessage.builder()
                .putData("title", title)
                .putData("content", content)
                .addAllTokens(registrationTokens)
                .build();

        try {
            BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
            if (response.getFailureCount() > 0) {
                List<SendResponse> responses = response.getResponses();
                for (int i = 0; i < responses.size(); i++) {
                    SendResponse sendResponse = responses.get(i);
                    if (!sendResponse.isSuccessful()) {
                        log.error("Firebase 메시지 전송 실패: {}", sendResponse.getException().getMessage());
                    }
                }
            } else {
                log.info("Firebase 메시지 전송 성공");
            }
        } catch (FirebaseMessagingException e) {
            log.error("Firebase 메시지 전송 실패: {}", e.getMessage());
        }
    }
}
