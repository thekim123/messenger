package com.namusd.messenger.service;

import com.namusd.messenger.config.security.auth.PrincipalDetails;
import com.namusd.messenger.model.domain.ChatRoomType;
import com.namusd.messenger.model.dto.ChatRoomDto;
import com.namusd.messenger.model.entity.ChatRoom;
import com.namusd.messenger.model.entity.User;
import com.namusd.messenger.repository.ChatMessageRepository;
import com.namusd.messenger.repository.ChatRoomRepository;
import com.namusd.messenger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;


    @Transactional(readOnly = true)
    public ChatRoomDto.Response getPrivateChatRoom(Authentication auth, Long targetUserId) {
        User loginUser = ((PrincipalDetails) auth.getPrincipal()).getUser();
        ChatRoom existRoom = chatRoomRepository.findPrivateChatRoom(loginUser.getId(), targetUserId)
                .orElseThrow(() -> new EntityNotFoundException("Chat room not found"));
        return existRoom.toDto();
    }

    @Transactional
    public ChatRoomDto.Response makePrivateChatRoom(Authentication auth, Long targetUserId) {
        UUID uuid = UUID.randomUUID();
        ChatRoom chatRoom = ChatRoom.builder()
                .isPrivate(true)
                .type(ChatRoomType.PRIVATE)
                .roomName(uuid.toString())
                .build();

        User loginUser = ((PrincipalDetails) auth.getPrincipal()).getUser();
        List<User> participants = userRepository.findAllById(Arrays.asList(loginUser.getId(), targetUserId));
        chatRoom.withParticipants(participants);
        return chatRoomRepository.save(chatRoom).toDto();
    }


}
