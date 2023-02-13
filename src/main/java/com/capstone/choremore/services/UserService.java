package com.capstone.choremore.services;

import com.capstone.choremore.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {

    User getCurrentUser();

    Boolean createNewUser(User user, Model model, HttpServletRequest request) throws ServletException;

    void createChildUser(User user);

    List<User> getChildOfParent();

    void getAvatarManager (Model model);

    void getProfileView(Model model);

    void createAvatarView(Model model);

    void childProfileView(Model model) throws UnsupportedEncodingException;

    void postAvatarBuilder(MultipartFile image, String class_type) throws Exception;

    void avatarFormView(Model model);

    void battleArenaView (Model model);

}
