package com.capstone.choremore.services;

import com.capstone.choremore.models.User;

import java.util.List;

public interface UserService {

    void saveUser(User user);

//    void authWithAuthManager(User user);

    List<User> getUsersByChildRole();

    User getUserById(long id);

    void authWithoutPassword(User user);

//    void authWithAuthManager(User user);

//    void authenticateUserAndSetSession(User user);

}
