package com.capstone.choremore.controller;

import com.capstone.choremore.models.Message;
import com.capstone.choremore.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageServ;

    @PostMapping("/createmsg")
    public String createNewMessage(@ModelAttribute Message message) {

        messageServ.createMessage(message);

        return "redirect:/message-board";

    }

    @GetMapping("/message-board")
    public String show(Model model, Model model2) {

        model2.addAttribute("messages", messageServ.showMessages());
        model.addAttribute("message", new Message());

        return "/messages/index";

    }

    @PostMapping("/deletemsg")
    public String deleteMessage(@RequestParam(name = "button") long id) {

        messageServ.deleteMessageById(id);

        return "redirect:/message-board";

    }

    @PostMapping("/editmsg")
    public String editPostsById(@RequestParam (name="editbtn") long id) {

        messageServ.editMessageById(id);

        return "redirect:/profile";

    }

}
