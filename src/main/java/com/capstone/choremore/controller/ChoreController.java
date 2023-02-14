package com.capstone.choremore.controller;

import com.capstone.choremore.models.Chore;
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

    @GetMapping("/chore-manager")
    public String choreView(Model model) {

        choreServ.choreManagerView(model);

        return "chores/index";

    }

    @PostMapping("/createchore")
    public String createChore(@ModelAttribute Chore chore, @RequestParam(name = "option") long id) {

        choreServ.createChore(chore, id);

        return "redirect:/profile";

    }

    @PostMapping("/changestatus")
    public String changeStatus(@RequestParam(name = "button") long id) {

        choreServ.changeChoreStatus(id);

        return "redirect:/child-profile";

    }

    @PostMapping("/changestatusPro")
    public String changeStatusPro(@RequestParam(name = "button") long id) {

        choreServ.changeChoreStatus(id);

        return "redirect:/child-profile";

    }

    @PostMapping("/approved")
    public String approveCompleted(@RequestParam(name = "button") long id) {

        choreServ.approveChore(id);

        return "redirect:/profile";

    }

    @PostMapping("/approvedPro")
    public String approveCompletedPro(@RequestParam(name = "buttonpro") long id) {

        choreServ.approveChore(id);

        return "redirect:/profile";

    }

    @GetMapping("/chores-view")
    public String showAllChores(Model model) {

        choreServ.choresView(model);

        return "avatars/child-profile";

    }

    @PostMapping("/deletechore")
    public String deleteChore(@RequestParam(name = "button") long id) {

        choreServ.deleteChoreById(id);

        return "redirect:/profile";

    }

    @PostMapping("/deletechorePro")
    public String deleteChorePro(@RequestParam(name = "button") long id) {

        choreServ.deleteChoreById(id);

        return "redirect:/profile";

    }

    @PostMapping("/editchore")
    public String editChore(@ModelAttribute Chore chore, @RequestParam(name = "option") long id, @RequestParam(name = "editThis") long id2) {

        choreServ.editChore(chore, id, id2);

        return "redirect:/profile";

    }

    @PostMapping("/editchorePro")
    public String editChorePro(@ModelAttribute Chore chore, @RequestParam(name = "option") long id, @RequestParam(name = "editThis") long id2) {

        choreServ.editChore(chore, id, id2);

        return "redirect:/profile";

    }

}