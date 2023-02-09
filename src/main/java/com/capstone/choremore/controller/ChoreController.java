package com.capstone.choremore.controller;

import com.capstone.choremore.models.Chore;
import com.capstone.choremore.repositories.ChoreRepo;
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

    @Autowired
    private ChoreRepo choreDao;

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

    @PostMapping("/changestatusPro")
    public String changeStatusPro(@RequestParam(name = "button") long id) {

        choreServ.changeChoreStatus(id);

        return "redirect:/child-profile";

    }

    @PostMapping("/approved")
    public String approveCompleted(@RequestParam(name = "button") long id) {

        choreServ.approveChore(id);

        return "redirect:/chore-manager";

    }

    @PostMapping("/approvedPro")
    public String approveCompletedPro(@RequestParam(name = "buttonpro") long id) {

        choreServ.approveChore(id);

        return "redirect:/profile";

    }

    @GetMapping("/chores-view")
    public String showAllChores(Model model) {

        model.addAttribute("chores", choreServ.childShowChores());

        return "chores/choresview";

    }

    @PostMapping("/deletechore")
    public String deleteChore(@RequestParam(name = "button") long id) {

        choreServ.deleteChoreById(id);

        return "redirect:/chore-manager";

    }

    @PostMapping("/deletechorePro")
    public String deleteChorePro(@RequestParam(name = "button") long id) {

        choreServ.deleteChoreById(id);

        return "redirect:/profile";

    }

    @PostMapping("/editchore")
    public String editChore(@ModelAttribute Chore chore, @RequestParam(name = "option") long id, @RequestParam(name = "editThis") long id2) {

        choreServ.editChore(chore, id, id2);

        return "redirect:/chore-manager";

    }

    @PostMapping("/editchorePro")
    public String editChorePro(@ModelAttribute Chore chore, @RequestParam(name = "option") long id, @RequestParam(name = "editThis") long id2) {

        choreServ.editChore(chore, id, id2);

        return "redirect:/profile";

    }

}