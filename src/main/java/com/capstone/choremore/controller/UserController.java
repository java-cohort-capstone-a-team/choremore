package com.capstone.choremore.controller;

import com.capstone.choremore.models.Avatar;
import com.capstone.choremore.models.Chore;
import com.capstone.choremore.models.Message;
import com.capstone.choremore.models.User;
import com.capstone.choremore.repositories.AvatarRepo;
import com.capstone.choremore.repositories.ChoreRepo;
import com.capstone.choremore.repositories.MessageRepo;
import com.capstone.choremore.repositories.UserRepo;
import com.capstone.choremore.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.SessionException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.List;

@Controller
@NoArgsConstructor
public class UserController {

    @Autowired
    private UserRepo userDao;

    @Autowired
    private AvatarRepo avatarDao;

    @Autowired
    private ChoreRepo choreDao;

    @Autowired
    private MessageRepo messageDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/profile")
    public String showProfile(Model model, Model model2, Model model3, Model model4) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Avatar> myAvatars = avatarDao.findAvatarByParentId(user.getId());
        List<Chore> myChores = choreDao.findChoreByParentId(user.getId());
        List<Message> childMessages = messageDao.findAll();

        model.addAttribute("user", user);
        model2.addAttribute("avatars", myAvatars);
        model3.addAttribute("chores", myChores);
        model4.addAttribute("messages", childMessages);

        return "users/profile";

    }

    @GetMapping("/child-profile")
    public String showChildProfile(Model model, Model model2, Model model3) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Chore> myChores = choreDao.findAllByChildId(user.getId());
        List<Message> messages = messageDao.findAll();

        model3.addAttribute("messages", messages);

        model2.addAttribute("chores", myChores);

        model.addAttribute("user", user);

        return "avatars/child-profile";

    }

    @GetMapping("/sign-up")
    public String showSignupForm(Model model) {

        model.addAttribute("user", new User());

        return "users/sign-up";

    }

    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User user, Model model, HttpServletRequest request) throws ServletException {

        try {

            String clearPass = user.getPassword();

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            userDao.save(user);

            request.login(user.getUsername(), clearPass);

            if (request.isUserInRole("ROLE_PARENT")) {
                model.addAttribute("avatar", new Avatar());
                return "/users/avatar-register";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/sign-up";
    }
}
