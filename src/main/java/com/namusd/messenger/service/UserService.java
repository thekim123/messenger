package com.namusd.messenger.service;

import com.namusd.messenger.helper.UserServiceHelper;
import com.namusd.messenger.model.domain.UserRole;
import com.namusd.messenger.model.dto.UserDto;
import com.namusd.messenger.model.entity.User;
import com.namusd.messenger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void join(UserDto.Join dto) {
        User user = User.builder()
                .password(passwordEncoder.encode(dto.getPassword()))
                .username(dto.getUsername())
                .roles(UserRole.USER)
                .build();
        userRepository.save(user);
    }

    @Transactional
    public void addFriend(Long userId, Long friendId) {
        User user = UserServiceHelper.findById(userId, userRepository);
        User friend = UserServiceHelper.findById(friendId, userRepository);

        user.getFriends().add(friend);
        friend.getFriends().add(user); // 대칭적 관계 유지
        userRepository.save(user);
        userRepository.save(friend);
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return UserServiceHelper.findByUsername(username, userRepository);
    }
}
