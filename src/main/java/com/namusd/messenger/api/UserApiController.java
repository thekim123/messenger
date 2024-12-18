package com.namusd.messenger.api;

import com.namusd.messenger.model.dto.UserDto;
import com.namusd.messenger.model.entity.User;
import com.namusd.messenger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApiController {
    private final UserService userService;

    @PostMapping("join")
    public ResponseEntity<?> join(@RequestBody UserDto.Join user) {
        userService.join(user);
        return ResponseEntity.created(URI.create("/api/user/join")).body("가입 완료");
    }

    @PostMapping("/{userId}/add/{friendId}")
    public ResponseEntity<String> addFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        userService.addFriend(userId, friendId);
        return ResponseEntity.ok("Friend added successfully");
    }


}
