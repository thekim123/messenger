package com.namusd.messenger.api;

import com.namusd.messenger.model.dto.ChatRoomDto;
import com.namusd.messenger.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@Slf4j
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatService chatService;

    @GetMapping("/private/{targetUserId}")
    public ResponseEntity<?> getPrivateChatRoom(Authentication auth, @PathVariable Long targetUserId) {
        ChatRoomDto.Response response = chatService.getPrivateChatRoom(auth, targetUserId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/private/{targetUserId}")
    public ResponseEntity<?> makePrivateChatRoom(Authentication auth, @PathVariable Long targetUserId) {
        ChatRoomDto.Response response = chatService.makePrivateChatRoom(auth, targetUserId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
