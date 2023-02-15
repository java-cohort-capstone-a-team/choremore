package com.capstone.choremore.controller;

import com.capstone.choremore.models.Message;
import com.capstone.choremore.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;

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
    public String show(Model model) throws UnsupportedEncodingException {

        messageServ.messageBoardView(model);

        return "messages/index";

    }

    @PostMapping("/deletemsg")
    public String deleteMessage(@RequestParam(name = "button") long id) {

        messageServ.deleteMessageById(id);

        return "redirect:/message-board";

    }

    @PostMapping("/deletemsgPro")
    public String deleteMessagePro(@RequestParam(name = "button") long id) {

        messageServ.deleteMessageById(id);

        return "redirect:/child-profile";

    }

    @PostMapping("/editmsg")
    public String editMessage(@ModelAttribute Message message, @RequestParam(name = "childid") long id, @RequestParam(name = "editThis") long id2) {

        messageServ.editMessage(message, id, id2);

        return "redirect:/message-board";

    }

    @PostMapping("/editmsgPro")
    public String editMessagePro(@ModelAttribute Message message, @RequestParam(name = "childid") long id, @RequestParam(name = "editThis") long id2) {

        messageServ.editMessage(message, id, id2);

        return "redirect:/child-profile";

    }

}
