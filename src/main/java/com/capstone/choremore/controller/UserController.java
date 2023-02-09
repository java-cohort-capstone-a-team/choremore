package com.capstone.choremore.controller;

import com.capstone.choremore.models.Chore;
import com.capstone.choremore.models.Message;
import com.capstone.choremore.models.User;
import com.capstone.choremore.models.UserWithRoles;
import com.capstone.choremore.services.AvatarService;
import com.capstone.choremore.services.ChoreService;
import com.capstone.choremore.services.MessageService;
import com.capstone.choremore.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userServ;

    @Autowired
    private ChoreService choreServ;

    @Autowired
    private MessageService messageServ;

    @Autowired
    private AvatarService avatarServ;

    @GetMapping("/create-avatar")
    public String childAvatarForm(Model model) {

        model.addAttribute("user", new User());

        return "users/avatar-register";

    }

    @PostMapping("/avatar-creation")
    public String createChildAvatar(@ModelAttribute User user) {

        userServ.createChildUser(user);

        return "redirect:/avatar-manager";

    }

    @GetMapping("/avatar-manager")
    public String avatarManager(Model model, Model model2) {

        model2.addAttribute("users", userServ.getChildOfParent());
        model.addAttribute("user", new User());

        return "avatars/index";

    }

    @GetMapping("/profile")
    public String showProfile(Model model, Model model2, Model model3, Model model4, Model model5) {

        model.addAttribute("user", userServ.getCurrentUser());
        model2.addAttribute("avatars", avatarServ.showAvatarsByParentsId());
        model3.addAttribute("chores", choreServ.showChoresByParentsId());
        model4.addAttribute("messages", messageServ.getChildMessages());
        model5.addAttribute("chore", new Chore());

        return "users/profile";

    }



    @GetMapping("/child-profile")
    public String showChildProfile(Model model, Model model2, Model model3, Model model4) {

//        User user = userServ.getCurrentUser();
//
//        if (user.getAvatar().getImage() == null) {
//
//            return "redirect:/create-avatar";
//
//        }


        model3.addAttribute("messages", messageServ.showMessages());
        model4.addAttribute("message", new Message());
        model2.addAttribute("chores", choreServ.showChoresByChildId());
        model.addAttribute("user", userServ.getCurrentUser());

        return "avatars/child-profile";

    }

    @GetMapping("/battle-arena")
    public String showBattleArena(Model model, Model model2, Model model3) {

        model.addAttribute("user", userServ.getCurrentUser());
        model2.addAttribute("avatars", avatarServ.showAvatarsByParentsId());
        model3.addAttribute("chores", choreServ.showChoresByParentsId());

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

    @PostMapping("/deletechild")
    public String deleteChildren(@RequestParam(name = "button") long id) {

        userServ.deleteExistenceChildById(id);

        return "redirect:/avatar-manager";

    }

}
