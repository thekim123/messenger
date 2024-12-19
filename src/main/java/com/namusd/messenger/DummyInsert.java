package com.namusd.messenger;


import com.namusd.messenger.model.dto.UserDto;
import com.namusd.messenger.model.entity.User;
import com.namusd.messenger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class DummyInsert {

    final UserService userService;

//    @PostConstruct
//    public void init() {
//
//        UserDto.Join user1 = new UserDto.Join("yhkim", "1234");
//        UserDto.Join user2 = new UserDto.Join("asdf", "1234");
//        UserDto.Join user3 = new UserDto.Join("qwer", "1234");
//
//        userService.join(user1);
//        userService.join(user2);
//        userService.join(user3);
//    }
}
