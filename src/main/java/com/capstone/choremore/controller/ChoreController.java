package com.capstone.choremore.controller;

import com.capstone.choremore.models.Chore;
import com.capstone.choremore.services.AvatarService;
import com.capstone.choremore.services.ChoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChoreController {

    @Autowired
    private ChoreService choreServ;

    @Autowired
    private AvatarService avatarServ;

    @GetMapping("/chore-manager")
    public String choreView(Model model, Model model2, Model model3) {

        model2.addAttribute("avatars", avatarServ.showAvatarsByParentsId());
        model.addAttribute("chore", new Chore());
        model3.addAttribute("chores", choreServ.showChoresByParentsId());

        return "chores/index";

    }

    @PostMapping("/createchore")
    public String createChore(@ModelAttribute Chore chore, @RequestParam(name = "option") long id) {

        choreServ.createChore(chore, id);

        return "redirect:/chore-manager";
    }

    @PostMapping("/changestatus")
    public String changeStatus(@RequestParam(name = "button") long id) {

        choreServ.changeChoreStatus(id);

        return "redirect:/chores-view";

    }

    @PostMapping("/approved")
    public String approveCompleted(@RequestParam(name = "button") long id) {

        choreServ.approveChore(id);

        return "redirect:/chore-manager";

    }

    @GetMapping("/chores-view")
    public String showAllChores(Model model) {

        model.addAttribute("chores", choreServ.childShowChores());

        return "chores/choresview";

    }

}