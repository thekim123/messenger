package com.namusd.messenger.api;

import com.namusd.messenger.model.dto.FriendshipDto;
import com.namusd.messenger.model.entity.Friendship;
import com.namusd.messenger.service.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/friend")
public class FriendController {
    private final FriendshipService friendshipService;

    @PostMapping("/request/{friendId}")
    public ResponseEntity<?> sendFriendRequest(@RequestParam Long userId, @PathVariable Long friendId) {
        FriendshipDto.Response dto = friendshipService.sendFriendRequest(userId, friendId);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PostMapping("/accept/{friendId}")
    public ResponseEntity<?> acceptFriendRequest(@RequestParam Long userId, @PathVariable Long friendId) {
        FriendshipDto.Response response = friendshipService.acceptFriendRequest(userId, friendId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list/all")
    public ResponseEntity<List<Friendship>> getFriends(@RequestParam Long userId) {
        List<Friendship> friends = friendshipService.getFriends(userId);
        return ResponseEntity.ok(friends);
    }
}
