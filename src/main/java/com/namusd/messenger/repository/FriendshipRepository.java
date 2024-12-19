package com.namusd.messenger.repository;

import com.namusd.messenger.model.domain.FriendStatus;
import com.namusd.messenger.model.entity.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    Optional<Friendship> findByUserIdAndFriendId(Long userId, Long friendId);

    List<Friendship> findByUserIdAndStatus(Long userId, FriendStatus status);

    List<Friendship> findByFriendIdAndStatus(Long id, FriendStatus friendStatus);
}
