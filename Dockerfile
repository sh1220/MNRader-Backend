# Amazon Corretto 17 기반 이미지 사용
FROM amazoncorretto:17

# 환경변수 정의 (빌드 시 전달)
ARG DATASOURCE_PASSWORD
ARG DATASOURCE_URL
ARG DATASOURCE_URL_DEV
ARG DATASOURCE_USERNAME

# 컨테이너 실행 시 유지될 환경변수
ENV DATASOURCE_PASSWORD=$DATASOURCE_PASSWORD
ENV DATASOURCE_URL=$DATASOURCE_URL
ENV DATASOURCE_URL_DEV=$DATASOURCE_URL_DEV
ENV DATASOURCE_USERNAME=$DATASOURCE_USERNAME

# Spring Boot Profile 설정 (dev)
ENV SPRING_PROFILES_ACTIVE=dev

# 컨테이너에서 사용할 포트 설정
EXPOSE 8080

# 작업 디렉토리 설정
WORKDIR /app
# JAR 파일 복사
COPY ./build/libs/app.jar ./app.jar

# 비루트 사용자 생성 및 전환
RUN groupadd -r appuser && useradd -r -g appuser appuser
USER appuser
# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
