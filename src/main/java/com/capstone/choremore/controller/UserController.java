package com.capstone.choremore.controller;

import com.capstone.choremore.models.*;
import com.capstone.choremore.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.UnsupportedEncodingException;

@Controller
public class UserController {

    @Autowired
    private UserService userServ;

    @GetMapping("/create-avatar")
    public String childAvatarForm(Model model) {

        userServ.createAvatarView(model);

        return "users/avatar-register";

    }

    @PostMapping("/avatar-creation")
    public String createChildAvatar(@ModelAttribute User user) {

        userServ.createChildUser(user);

        return "redirect:/avatar-manager";

    }

    @GetMapping("/avatar-manager")
    public String avatarManager(Model model) {

        userServ.getAvatarManager(model);

        return "avatars/index";

    }

    @GetMapping("/profile")
    public String showProfile(Model model) {

        userServ.getProfileView(model);

        return "users/profile";

    }



    @GetMapping("/child-profile")
    public String showChildProfile(Model model) throws UnsupportedEncodingException {

        userServ.childProfileView(model);

        return "avatars/child-profile";

    }

    @PostMapping("/avatarbuilder")
    public String addImageAndClassToAvatar(@RequestParam("image") MultipartFile image, @RequestParam("class_type") String class_type) throws Exception {

        userServ.postAvatarBuilder(image, class_type);

        return "redirect:/child-profile";

    }

    @GetMapping("/avatar-form")
    public String showAvatarForm(Model model) {

        userServ.avatarFormView(model);

        return "avatars/avatar-form";

    }

    @GetMapping("/battle-arena")
    public String showBattleArena(Model model) {

        userServ.battleArenaView(model);

        return "avatars/battle-arena";

    }

    @GetMapping("/sign-up")
    public String showSignupForm(Model model) {

        model.addAttribute("user", new User());

        return "users/sign-up";

    }

    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User user, Model model, HttpServletRequest request) throws ServletException {

        if (userServ.createNewUser(user, model, request)) {

            return "redirect:/create-avatar";

        } else {

            return "users/sign-up";

        }

    }

}
