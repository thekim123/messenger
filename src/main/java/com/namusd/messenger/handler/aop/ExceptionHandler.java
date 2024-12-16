package com.namusd.messenger.handler.aop;

import com.namusd.messenger.handler.ex.BadRequestApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;

@Slf4j
public class ExceptionHandler {

    // CustomApiException 발생시 400 에러코드와 메시지 전달
    @org.springframework.web.bind.annotation.ExceptionHandler(BadRequestApiException.class)
    public ResponseEntity<?> apiException(BadRequestApiException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("에러 메세지 : " + e.getMessage());
    }

    // EntityNotFoundException 발생시 400 에러코드와 메시지 전달
    @org.springframework.web.bind.annotation.ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("해당 데이터가 DB에 존재하지 않습니다. \n" +
                "에러 메세지 : " + ex.getMessage());
    }

    // AccessDeniedException 발생시 403 에러코드와 메시지 전달
    @org.springframework.web.bind.annotation.ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        log.debug("해당 Url에 접근 권한이 없습니다. \n" +
                "에러 메세지 : " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("해당 Url에 접근 권한이 없습니다.");
    }

//    // TokenExpiredException 발생시 401 에러코드와 메시지 전달
//    @org.springframework.web.bind.annotation.ExceptionHandler(TokenExpiredException.class)
//    public ResponseEntity<String> handleTokenExpiredException(TokenExpiredException ex) {
//        log.error("만료된 토큰입니다. 리프레시 토큰을 이용해 새로운 토큰으로 갱신해주세요.\n" +
//                "에러메세지: {}", Arrays.asList(ex.getStackTrace()));
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("만료된 토큰입니다. 리프레시 토큰을 이용해 새로운 토큰으로 갱신해주세요.");
//    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Throwable rootCause = ex.getRootCause();
        if (rootCause instanceof SQLException) {
            SQLException sqlEx = (SQLException) rootCause;
            if (sqlEx.getSQLState().startsWith("23")) {
                // SQLState 23xxx는 constraint violation을 나타냄
                log.error("데이터 중복 오류가 발생했습니다: " + sqlEx.getMessage());
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("데이터 중복 오류가 발생했습니다.");
            }
        }

        // 다른 DataIntegrityViolationException 경우를 처리할 수도 있습니다.
        log.error("데이터 중복 오류가 발생했습니다: " + ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("서버 내부 오류가 발생했습니다. ");
    }
}