package com.example.mnraderbackend.common.response.status;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum BaseExceptionResponseStatus implements ResponseStatus {

    /**
     * 1000: 요청 성공 (OK)
     */
    // 1000: 회원가입 & 로그인 요청 성공
    SUCCESS(1000, HttpStatus.OK.value(), "요청에 성공하였습니다."),
    SIGNUP_SUCCESS(1001, HttpStatus.OK.value(), "회원가입에 성공하였습니다."),
    SIGNIN_SUCCESS(1002, HttpStatus.OK.value(), "로그인에 성공하였습니다."),
    // 1100: 페이지 조회 성공
    HOME_SUCCESS(1101, HttpStatus.OK.value(), "홈 페이지 조회에 성공하였습니다."),
    ALARM_SUCCESS(1102, HttpStatus.OK.value(), "알림 페이지 조회에 성공하였습니다."),
    MY_SUCCESS(1103, HttpStatus.OK.value(), "마이 페이지 조회에 성공하였습니다."),
    MY_POST_SUCCESS(1104, HttpStatus.OK.value(), "내가 올린 게시물 조회에 성공하였습니다."),
    MY_SCRAP_SUCCESS(1105, HttpStatus.OK.value(), "스크랩 조회에 성공하였습니다."),
    ANIMAL_DETAIL_SUCCESS(1106, HttpStatus.OK.value(), "동물 상세페이지 조회에 성공하였습니다."),

    // 1200: 마이페이지 수정 성공
    MY_UPDATE_ALARM_SUCCESS(1201, HttpStatus.OK.value(), "알림 설정에 성공하였습니다."),
    MY_UPDATE_EMAIL_SUCCESS(1202, HttpStatus.OK.value(), "이메일 설정에 성공하였습니다."),
    MY_UPDATE_REGION_SUCCESS(1203, HttpStatus.OK.value(), "지역 설정에 성공하였습니다."),
    // 1300: 토큰
    FCM_TOKEN_POST_SUCCESS(1301, HttpStatus.OK.value(), "FCM 토큰 등록에 성공하였습니다."),
    // 1600: animal
    ANIMAL_REGISTER_SUCCESS(1605, HttpStatus.OK.value(), "동물 실종/목격 등록에 성공했습니다"),
    ANIMAL_SCRAP_SUCCESS(1606, HttpStatus.OK.value(), "동물 스크랩 등록에 성공했습니다."),
    ANIMAL_SCRAP_CANCEL_SUCCESS(1607, HttpStatus.OK.value(), "동물 스크랩 취소에 성공했습니다."),

    /**
     * 2000: Request 오류 (BAD_REQUEST)
     */
    BAD_REQUEST(2000, HttpStatus.BAD_REQUEST.value(), "유효하지 않은 요청입니다."),
    URL_NOT_FOUND(2001, HttpStatus.BAD_REQUEST.value(), "유효하지 않은 URL 입니다."),
    METHOD_NOT_ALLOWED(2002, HttpStatus.METHOD_NOT_ALLOWED.value(), "해당 URL에서는 지원하지 않는 HTTP Method 입니다."),
    INVALID_PARAMETER(2003, HttpStatus.BAD_REQUEST.value(), "요청 파라미터가 유효하지 않습니다."),
    INVALID_REQUEST_BODY(2004, HttpStatus.BAD_REQUEST.value(), "요청 본문이 유효하지 않습니다."),

    /**
     * 3000: Server, Database 오류 (INTERNAL_SERVER_ERROR)
     */
    SERVER_ERROR(3000, HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버에서 오류가 발생하였습니다."),
    DATABASE_ERROR(3001, HttpStatus.INTERNAL_SERVER_ERROR.value(), "데이터베이스에서 오류가 발생하였습니다."),
    BAD_SQL_GRAMMAR(3002, HttpStatus.INTERNAL_SERVER_ERROR.value(), "SQL에 오류가 있습니다."),

    /**
     * 4000: 실패
     */

    //4000: 회원가입 & 로그인실패
    SIGNUP_FAIL(4001, HttpStatus.NOT_FOUND.value(),  "회원가입에 실패하였습니다."),
    SIGNIN_FAIL(4002, HttpStatus.NOT_FOUND.value(),  "로그인에 실패하였습니다."),
    INVALID_USER_VALUE(4003, HttpStatus.BAD_REQUEST.value(), "요청에서 잘못된 값이 존재합니다."),
    DUPLICATE_EMAIL(4004, HttpStatus.BAD_REQUEST.value(), "이미 존재하는 이메일입니다."),
    DUPLICATE_NICKNAME(4005, HttpStatus.BAD_REQUEST.value(), "이미 존재하는 닉네임입니다."),
    EMAIL_NOT_FOUND(4006, HttpStatus.BAD_REQUEST.value(), "존재하지 않는 이메일입니다."),
    USER_NOT_FOUND(4007, HttpStatus.BAD_REQUEST.value(), "존재하지 않는 회원입니다."),
    PASSWORD_NO_MATCH(4008, HttpStatus.BAD_REQUEST.value(), "비밀번호가 일치하지 않습니다."),

    // 4100: 페이지 조회 실패
    HOME_FAIL(4101, HttpStatus.NOT_FOUND.value(),  "홈 페이지 조회에 실패하였습니다."),
    ALARM_FAIL(4102, HttpStatus.NOT_FOUND.value(),  "알림 페이지 조회에 실패하였습니다."),
    MY_FAIL(4103, HttpStatus.NOT_FOUND.value(),  "마이 페이지 조회에 실패하였습니다."),
    MY_POST_FAIL(4104, HttpStatus.NOT_FOUND.value(),  "내가 올린 게시물 조회에 실패하였습니다."),
    MY_SCRAP_FAIL(4105, HttpStatus.NOT_FOUND.value(),  "스크랩 조회에 실패하였습니다."),
    ANIMAL_DETAIL_FAIL(4106, HttpStatus.NOT_FOUND.value(), "동물 상세페이지 조회에 실패하였습니다."),
    // 4200: 마이페이지 수정 실패
    MY_UPDATE_ALARM_FAIL(4201, HttpStatus.NOT_FOUND.value(), "알림 설정에 실패하였습니다."),
    MY_UPDATE_EMAIL_FAIL(4202, HttpStatus.NOT_FOUND.value(), "이메일 설정에 실패하였습니다."),
    MY_UPDATE_REGION_FAIL(4203, HttpStatus.NOT_FOUND.value(), "지역 설정에 실패하였습니다."),
    // 4300: 토큰
    FCM_TOKEN_POST_FAIL(4301, HttpStatus.NOT_FOUND.value(), "FCM 토큰 등록에 실패하였습니다."),
    FCM_TOKEN_NULL_FAIL(4302, HttpStatus.NOT_FOUND.value(), "FCM 토큰이 유효하지 않습니다."),
    // 4400 : image
    INVALID_IMG(4401,HttpStatus.BAD_REQUEST.value(), "잘못된 이미지 파일입니다."),
    UPLOAD_FAIL(4402,HttpStatus.BAD_REQUEST.value(), "파일 업로드에 실패했습니다. 인터넷 연결을 확인하거나, 나중에 다시 시도해 주세요."),
    INVALID_USER_DB_VALUE(4403,HttpStatus.BAD_REQUEST.value(), "유저 정보에 오류가 발생했습니다. 관리자에게 문의해주세요."),
    NULL_USER_VALUE(4404,HttpStatus.BAD_REQUEST.value(), "요청에 필요한 값이 존재하지 않습니다."),
    EMPTY_USER_VALUE(4405,HttpStatus.BAD_REQUEST.value(), "요청에 필요한 값이 비어있습니다."),
    // 4500: region
    REGION_NOT_FOUND(4501, HttpStatus.NOT_FOUND.value(), "존재하지 않는 지역입니다."),
    // 4600: animal
    ANIMAL_NULL(4601, HttpStatus.BAD_REQUEST.value(), "동물 정보가 존재하지 않습니다."),
    ANIMAL_NOT_FOUND(4602, HttpStatus.NOT_FOUND.value(), "존재하지 않는 동물입니다."),
    BREED_NOT_FOUND(4603, HttpStatus.NOT_FOUND.value(), "존재하지 않는 품종입니다."),
    INVALID_ANIMAL_VALUE(4604, HttpStatus.BAD_REQUEST.value(), "유효하지 않은 동물 정보입니다."),
    ANIMAL_REGISTER_FAIL(4605, HttpStatus.BAD_REQUEST.value(), "동물 실종/목격 등록에 실패하였습니다."),
    ANIMAL_SCRAP_FAIL(4606, HttpStatus.BAD_REQUEST.value(), "동물 스크랩 등록에 실패하였습니다."),
    ANIMAL_SCRAP_CANCEL_FAIL(4607, HttpStatus.BAD_REQUEST.value(), "동물 스크랩 취소에 실패하였습니다."),

    // 4700 : Alarm
    ALARM_NULL(4701, HttpStatus.BAD_REQUEST.value(), "알림 정보가 존재하지 않습니다."),
    /**
     * 5000: Authorization 오류
     */
    INVALID_TOKEN(5001, HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 토큰입니다."),
    MALFORMED_TOKEN(5002, HttpStatus.UNAUTHORIZED.value(), "토큰이 올바르게 구성되지 않았습니다."),
    TOKEN_NOT_FOUND(5003, HttpStatus.BAD_REQUEST.value(), "토큰이 HTTP Header에 없습니다."),
    UNSUPPORTED_TOKEN_TYPE(5004, HttpStatus.BAD_REQUEST.value(), "지원되지 않는 토큰 형식입니다."),
    EXPIRED_TOKEN(5005, HttpStatus.UNAUTHORIZED.value(), "토큰이 만료되었습니다."),
    ACCESS_DENIED(5006, HttpStatus.FORBIDDEN.value(), "접근 권한이 없습니다."),


    /**
     * 9000: 미디어  오류
     */

    FILE_TOO_LARGE(9000, HttpStatus.PAYLOAD_TOO_LARGE.value(), "업로드할 파일의 크기가 너무 큽니다.");


    private final int code;
    private final int status;
    private final String message;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
