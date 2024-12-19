package com.namusd.messenger.repository;

import com.namusd.messenger.model.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Query(value = "select cr " +
            "from ChatRoom cr " +
            "join cr.participants cu1 " +
            "join cr.participants cu2 " +
            "where cu1.id=:id " +
            "and cu2.id=:targetUserId ")
    Optional<ChatRoom> findPrivateChatRoom(Long id, Long targetUserId);

}
