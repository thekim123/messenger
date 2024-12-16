package com.namusd.messenger.repository;

import com.namusd.messenger.model.entity.Friendship;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FriendshipRepository extends CrudRepository<Friendship, Long> {
    Optional<Friendship> findByUserIdAndFriendId(Long userId, Long friendId);
}
