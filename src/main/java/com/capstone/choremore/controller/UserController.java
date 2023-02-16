package com.capstone.choremore.controller;

import com.capstone.choremore.models.*;
import com.capstone.choremore.repositories.UserRepo;
import com.capstone.choremore.services.EmailService;
import com.capstone.choremore.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userServ;

    @Autowired
    private UserRepo userDao;

    @GetMapping("/create-avatar")
    public String childAvatarForm(Model model) {

        userServ.createAvatarView(model);

        return "users/avatar-register";

    }

    @PostMapping("/avatar-creation")
    public String createChildAvatar(@ModelAttribute User user) {

        userServ.createChildUser(user);

        return "redirect:/profile";

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

//    @GetMapping("/check-username")
//    @ResponseBody
//    public ResponseEntity<Map<String, Boolean>> checkUsername(@RequestParam String username) {
//        Map<String, Boolean> response = new HashMap<>();
//        boolean exists = userDao.existsByUsername(username);
//        response.put("exists", exists);
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/check-email")
//    @ResponseBody
//    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestParam String email) {
//        Map<String, Boolean> response = new HashMap<>();
//        boolean exists = userDao.existsByEmail(email);
//        response.put("exists", exists);
//        return ResponseEntity.ok(response);
//    }

    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User user, Model model, HttpServletRequest request) throws ServletException {

        if (userServ.createNewUser(user, model, request)) {

            return "redirect:/create-avatar";

        } else {

            return "users/sign-up";

        }

    }

}
