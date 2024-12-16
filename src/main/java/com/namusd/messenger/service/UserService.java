package com.namusd.messenger.service;

import com.namusd.messenger.helper.UserServiceHelper;
import com.namusd.messenger.model.dto.UserDto;
import com.namusd.messenger.model.entity.User;
import com.namusd.messenger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(UserDto.Join dto) {
        User user = User.builder()
                .password(passwordEncoder.encode(dto.getPassword()))
                .username(dto.getUsername())
                .build();
        userRepository.save(user);
    }


    public void addFriend(Long userId, Long friendId) {
        User user = UserServiceHelper.findUser(userId, userRepository);
        User friend = UserServiceHelper.findUser(friendId, userRepository);

        user.getFriends().add(friend);
        friend.getFriends().add(user); // 대칭적 관계 유지
        userRepository.save(user);
        userRepository.save(friend);
    }
}
