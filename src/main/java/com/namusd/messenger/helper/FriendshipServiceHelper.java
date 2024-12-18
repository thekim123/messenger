package com.namusd.messenger.helper;

import com.namusd.messenger.model.entity.Friendship;
import com.namusd.messenger.repository.FriendshipRepository;

import javax.persistence.EntityNotFoundException;

public class FriendshipServiceHelper {

    public static Friendship findFriendship(Long userId, Long friendId, FriendshipRepository repository) {
        return repository.findByUserIdAndFriendId(userId, friendId)
                .orElseThrow(() -> new EntityNotFoundException("No pending friendship request"));
    }

    public static boolean existFriendship(Long userId, Long friendId, FriendshipRepository repository) {
        return repository.findByUserIdAndFriendId(userId, friendId).isPresent();
    }
}
