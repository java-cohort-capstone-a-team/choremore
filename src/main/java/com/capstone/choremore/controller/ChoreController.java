package com.capstone.choremore.controller;

import com.capstone.choremore.models.Chore;
import com.capstone.choremore.models.User;
import com.capstone.choremore.services.ChoreService;
import com.capstone.choremore.services.UserService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@NoArgsConstructor
public class ChoreController {

    @Autowired
    private ChoreService choreServ;

    @Autowired
    private UserService userServ;

    public ChoreController(ChoreService choreServ, UserService userServ) {

        this.choreServ = choreServ;
        this.userServ = userServ;

    }

    @GetMapping("/chore-manager")
    public String choreManager(Model model, Model model2) {

        model2.addAttribute("users", userServ.getUsersByChildRole());
        model.addAttribute("chore", new Chore());

        return "chores/index";

    }

    @PostMapping("/createchore")
    public String createChore(@ModelAttribute Chore chore, @RequestParam(name = "option") long id) {

        User child = userServ.getUserById(id);

        chore.setChild(child);

        choreServ.createChore(chore);

        return "redirect:/chore-manager";
    }
}