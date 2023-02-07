package com.capstone.choremore.controller;

import com.capstone.choremore.models.Avatar;
import com.capstone.choremore.models.User;
import com.capstone.choremore.repositories.AvatarRepo;
import com.capstone.choremore.repositories.UserRepo;
import com.capstone.choremore.services.AvatarService;
import com.capstone.choremore.services.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.swing.*;
import java.util.List;

@Controller
@NoArgsConstructor
@AllArgsConstructor
public class AvatarController {

    @Autowired
    UserRepo userDao;

    @Autowired
    AvatarRepo avatarDao;

    @Autowired
    UserService userServ;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/create-avatar")
    public String showAvatarForm(Model model) {

        model.addAttribute("user", new User());

        return "users/avatar-register";

    }

    @GetMapping("/avatar-manager")
    public String avatarManager(Model model, Model model2) {

        model2.addAttribute("users", userServ.getUsersByChildRole());
        model.addAttribute("user", new User());

        return "avatars/index";

    }

    @PostMapping("/hpplus")
    public String saveHp(@ModelAttribute User user) {

        User me = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Avatar myAvatar = me.getAvatar();

        if (myAvatar.getBuild_points() > 0) {
            myAvatar.setHp(myAvatar.getHp() + 1);
            myAvatar.setBuild_points(myAvatar.getBuild_points() - 1);
            avatarDao.save(myAvatar);
        }

        return "redirect:/skill-builder";

    }

    @PostMapping("/strengthplus")
    public String saveStrength(@ModelAttribute User user) {

        User me = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Avatar myAvatar = me.getAvatar();

        if (myAvatar.getBuild_points() > 0) {
            myAvatar.setStrength(myAvatar.getStrength() + 1);
            myAvatar.setBuild_points(myAvatar.getBuild_points() - 1);
            avatarDao.save(myAvatar);
        }

        return "redirect:/skill-builder";

    }

    @PostMapping("/defenseplus")
    public String saveDefense(@ModelAttribute User user) {

        User me = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Avatar myAvatar = me.getAvatar();

        if (myAvatar.getBuild_points() > 0) {
            myAvatar.setDefense(myAvatar.getDefense() + 1);
            myAvatar.setBuild_points(myAvatar.getBuild_points() - 1);
            avatarDao.save(myAvatar);
        }

        return "redirect:/skill-builder";

    }

    @PostMapping("/avatar-creation")
    public String saveUser(@ModelAttribute User user) {

        Avatar avatar = new Avatar();

        User me = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        user.setRoles("ROLE_CHILD");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
        avatar.setParent(me);
        avatar.setChild(user);
        avatarDao.save(avatar);

        user.setAvatar(avatar);

//        me.setAvatars(List.of(avatar));

        userDao.save(user);
        avatarDao.save(avatar);
//        userDao.save(me);

        return "redirect:/avatar-manager";

    }

}
