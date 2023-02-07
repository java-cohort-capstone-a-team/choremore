package com.capstone.choremore.controller;

import com.capstone.choremore.models.Avatar;
import com.capstone.choremore.models.Chore;
import com.capstone.choremore.models.User;
import com.capstone.choremore.models.UserWithRoles;
import com.capstone.choremore.repositories.AvatarRepo;
import com.capstone.choremore.repositories.ChoreRepo;
import com.capstone.choremore.services.ChoreService;
import com.capstone.choremore.services.UserService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@NoArgsConstructor
public class ChoreController {

    @Autowired
    private ChoreRepo choreDao;

    @Autowired
    private ChoreService choreServ;

    @Autowired
    private UserService userServ;

    @Autowired
    private AvatarRepo avatarDao;

    public ChoreController(ChoreService choreServ, UserService userServ) {

        this.choreServ = choreServ;
        this.userServ = userServ;

    }

    @GetMapping("/chore-manager")
    public String choreManager(Model model, Model model2, Model model3) {

        UserWithRoles me = (UserWithRoles) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Avatar> myAvatars = avatarDao.findAvatarByParentId(me.getId());

        model2.addAttribute("avatars", myAvatars);

        model.addAttribute("chore", new Chore());
        model3.addAttribute("chores", choreServ.showAllChores());

        return "chores/index";

    }

    @PostMapping("/createchore")
    public String createChore(@ModelAttribute Chore chore, @RequestParam(name = "option") long id) {

        User child = userServ.getUserById(id);

        chore.setChild(child);
        child.setChore(chore);

        choreServ.createChore(chore);

        return "redirect:/chore-manager";
    }

    @GetMapping("/chores-view")
    public String showAllChores(Model model) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Chore> myChores = choreDao.findAllByChildId(user.getId());

        model.addAttribute("chores", myChores);

        return "chores/choresview";

    }
}