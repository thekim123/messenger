package com.namusd.messenger.service;

import com.namusd.messenger.handler.ex.BadRequestApiException;
import com.namusd.messenger.helper.UserServiceHelper;
import com.namusd.messenger.model.domain.FriendStatus;
import com.namusd.messenger.model.entity.Friendship;
import com.namusd.messenger.model.entity.User;
import com.namusd.messenger.repository.FriendshipRepository;
import com.namusd.messenger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;

    private final UserRepository userRepository;

    public Friendship sendFriendRequest(Long userId, Long friendId) {
        User user = UserServiceHelper.findUser(userId, userRepository);
        User friend = UserServiceHelper.findUser(friendId, userRepository);

        if (friendshipRepository.findByUserIdAndFriendId(userId, friendId).isPresent()) {
            throw new BadRequestApiException("이미 요청을 한 사용자입니다.");
        }

        Friendship friendship = Friendship.builder()
                .user(user)
                .friend(friend)
                .status(FriendStatus.PENDING)
                .build();
        Friendship entity = friendshipRepository.save(friendship);
        return entity;
    }

    public Friendship acceptFriendRequest(Long userId, Long friendId) {
        Friendship friendship = friendshipRepository.findByUserIdAndFriendId(friendId, userId)
                .orElseThrow(() -> new IllegalStateException("No pending friendship request"));

        friendship.setStatus("ACCEPTED");
        return friendshipRepository.save(friendship);
    }

    public List<Friendship> getFriends(Long userId) {
        return friendshipRepository.findByUserIdAndStatus(userId, "ACCEPTED");
    }
}
