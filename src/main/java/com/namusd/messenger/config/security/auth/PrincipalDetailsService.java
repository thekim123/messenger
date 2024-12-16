package com.namusd.messenger.config.security.auth;

import com.namusd.messenger.model.entity.User;
import com.namusd.messenger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;


@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Loading user by username: {}", username);

        User userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User not found: {}", username);
                    return new EntityNotFoundException("존재하지 않는 회원입니다.");
                });

        log.debug("User found: {}", username);
        return new PrincipalDetails(userEntity);
    }


}
