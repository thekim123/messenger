package com.namusd.messenger.service;

import com.namusd.messenger.config.security.auth.PrincipalDetails;
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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;

    private final UserRepository userRepository;

    @Transactional
    public FriendshipDto.Response sendFriendRequest(Long userId, String friendUsername) {
        User user = UserServiceHelper.findById(userId, userRepository);
        User friend = UserServiceHelper.findByUsername(friendUsername, userRepository);

        if (FriendshipServiceHelper.existFriendship(userId, friend.getId(), friendshipRepository)) {
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
    public FriendshipDto.Response acceptFriendRequest(Long userId, String friendUsername) {
        User friend = UserServiceHelper.findByUsername(friendUsername, userRepository);
        Friendship friendship = FriendshipServiceHelper.findFriendship(friend.getId(), userId, friendshipRepository);
        friendship.accept();

        User loginUser = UserServiceHelper.findById(userId, userRepository);
        Friendship entity = Friendship.builder()
                .status(FriendStatus.ACCEPTED)
                .friend(friend)
                .user(loginUser)
                .build();
        friendshipRepository.save(entity);
        return friendshipRepository.save(friendship).toDto();
    }

    @Transactional
    public FriendshipDto.Response rejectFriendRequest(Long userId, String friendUsername) {
        User friend = UserServiceHelper.findByUsername(friendUsername, userRepository);
        Friendship friendship = FriendshipServiceHelper.findFriendship(friend.getId(), userId, friendshipRepository);
        friendship.reject();
        return friendshipRepository.save(friendship).toDto();
    }

    @Transactional(readOnly = true)
    public List<Friendship> getFriends(Authentication auth) {
        User user = ((PrincipalDetails) auth.getPrincipal()).getUser();
        return friendshipRepository.findByUserIdAndStatus(user.getId(), FriendStatus.ACCEPTED);
    }

    @Transactional(readOnly = true)
    public List<Friendship> getPendingFriends(Authentication auth) {
        User user = ((PrincipalDetails) auth.getPrincipal()).getUser();
        return friendshipRepository.findByUserIdAndStatus(user.getId(), FriendStatus.PENDING);
    }

    @Transactional(readOnly = true)
    public List<Friendship> getRejectedFriends(Authentication auth) {
        User user = ((PrincipalDetails) auth.getPrincipal()).getUser();
        return friendshipRepository.findByUserIdAndStatus(user.getId(), FriendStatus.REJECTED);
    }

}
