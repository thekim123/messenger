package com.namusd.messenger.helper;

import com.namusd.messenger.model.entity.User;
import com.namusd.messenger.repository.UserRepository;

import javax.persistence.EntityNotFoundException;

public class UserServiceHelper {

    public static User findById(Long id, UserRepository repository){
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public static User findByUsername(String username, UserRepository repository){
        return repository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
    }
}
