package com.capstone.choremore.services;

import com.capstone.choremore.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;

import java.util.List;

public interface UserService {

    User getCurrentUser();

    Boolean createNewUser(User user, Model model, HttpServletRequest request) throws ServletException;

    void createChildUser(User user);

    List<User> getChildOfParent();

//    void deleteChildExistenceById(long id);

}
