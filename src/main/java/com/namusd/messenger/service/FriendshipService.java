package com.namusd.messenger.service;

import com.namusd.messenger.handler.ex.BadRequestApiException;
import com.namusd.messenger.helper.FriendshipServiceHelper;
import com.namusd.messenger.helper.UserServiceHelper;
import com.namusd.messenger.model.domain.FriendStatus;
import com.namusd.messenger.model.dto.FriendshipDto;
import com.namusd.messenger.model.entity.Friendship;
import com.namusd.messenger.model.entity.User;
import com.namusd.messenger.repository.FriendshipRepository;
import com.namusd.messenger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;

    private final UserRepository userRepository;

    @Transactional
    public FriendshipDto.Response sendFriendRequest(Long userId, Long friendId) {
        User user = UserServiceHelper.findUser(userId, userRepository);
        User friend = UserServiceHelper.findUser(friendId, userRepository);

        if (FriendshipServiceHelper.existFriendship(userId, friendId, friendshipRepository)) {
            throw new BadRequestApiException("이미 요청을 한 사용자입니다.");
        }

        Friendship friendship = Friendship.builder()
                .user(user)
                .friend(friend)
                .status(FriendStatus.PENDING)
                .build();
        Friendship entity = friendshipRepository.save(friendship);
        return entity.toDto();
    }

    @Transactional
    public FriendshipDto.Response acceptFriendRequest(Long userId, Long friendId) {
        Friendship friendship = FriendshipServiceHelper.findFriendship(userId, friendId, friendshipRepository);
        friendship.accept();
        return friendshipRepository.save(friendship).toDto();
    }

    @Transactional(readOnly = true)
    public List<Friendship> getFriends(Long userId) {
        return friendshipRepository.findByUserIdAndStatus(userId, FriendStatus.ACCEPTED);
    }


}
