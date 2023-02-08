package com.capstone.choremore.controller;

import com.capstone.choremore.models.User;
import com.capstone.choremore.services.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AvatarController {

    @Autowired
    AvatarService avatarServ;

    @GetMapping("/skill-builder")
    public String showChildProfile(Model model) {

        model.addAttribute("avatar", avatarServ.showAvatarByChildId());

        return "avatars/skill-builder";

    }

    @PostMapping("/hpplus")
    public String saveHp(@ModelAttribute User user) {

        avatarServ.editHp();

        return "redirect:/skill-builder";

    }

    @PostMapping("/strengthplus")
    public String saveStrength(@ModelAttribute User user) {

        avatarServ.editStrength();

        return "redirect:/skill-builder";

    }

    @PostMapping("/defenseplus")
    public String saveDefense(@ModelAttribute User user) {

        avatarServ.editDefense();

        return "redirect:/skill-builder";

    }

}
