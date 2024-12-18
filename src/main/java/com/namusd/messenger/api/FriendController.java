package com.namusd.messenger.api;

import com.namusd.messenger.config.security.auth.PrincipalDetails;
import com.namusd.messenger.model.dto.FriendshipDto;
import com.namusd.messenger.model.entity.Friendship;
import com.namusd.messenger.model.entity.User;
import com.namusd.messenger.service.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/friend")
public class FriendController {
    private final FriendshipService friendshipService;

    @PostMapping("/request/{friendUsername}")
    public ResponseEntity<?> sendFriendRequest(Authentication auth, @PathVariable String friendUsername) {
        User user = ((PrincipalDetails) auth.getPrincipal()).getUser();
        FriendshipDto.Response dto = friendshipService.sendFriendRequest(user.getId(), friendUsername);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/accept/{friendUsername}")
    public ResponseEntity<?> acceptFriendRequest(@RequestParam Long userId, @PathVariable String friendUsername) {
        FriendshipDto.Response response = friendshipService.acceptFriendRequest(userId, friendUsername);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/reject/{friendUsername}")
    public ResponseEntity<?> rejectFriendRequest(Authentication auth, @PathVariable String friendUsername) {
        User user = ((PrincipalDetails) auth.getPrincipal()).getUser();
        FriendshipDto.Response response = friendshipService.rejectFriendRequest(user.getId(), friendUsername);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list/all")
    public ResponseEntity<?> getFriends(Authentication auth) {
        List<Friendship> friends = friendshipService.getFriends(auth);
        return ResponseEntity.ok(friends);
    }

    @GetMapping("/list/pending")
    public ResponseEntity<?> getPendingFriends(Authentication auth) {
        List<Friendship> friends = friendshipService.getPendingFriends(auth);
        return ResponseEntity.ok(friends);
    }
}
