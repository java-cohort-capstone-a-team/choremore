package com.capstone.choremore.controller;

import com.capstone.choremore.models.Message;
import com.capstone.choremore.repositories.MessageRepo;
import com.capstone.choremore.services.MessageService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@NoArgsConstructor
public class MessageController {

//    @Autowired
//    private MessageRepo messageDao;

    @Autowired
    private MessageService messageServ;

    public MessageController(MessageService messageServ) {

        this.messageServ = messageServ;

    }

//    @GetMapping("/profile")
//    public String profileShowUserPosts(Model model) {
//
//        model.addAttribute("posts", messageServ.profileShowUserMessages());
//
//        return "/messages/profile";
//
//    }

//    @PostMapping("/profile")
//    public String editThis(@ModelAttribute Message message, @RequestParam(name="button") long id) {
//
//        return "redirect:/edit/" + id;
//
//    }

//    @GetMapping("/create")
//    public String createPostForm(Model model) {
//
//        model.addAttribute("post", new Message());
//
//        return "/messages/create";
//
//    }

    @PostMapping("/createmsg")
    public String createMessage(@ModelAttribute Message message) {

        messageServ.createMessage(message);

        return "redirect:/message-board";

    }

    @GetMapping("/message-board")
    public String show(Model model, Model model2) {

        model2.addAttribute("messages", messageServ.showMessages());
        model.addAttribute("message", new Message());

        return "/messages/index";

    }

//    @PostMapping("/messages")
//    public String create(@ModelAttribute Message message, @RequestParam(name="button") long id) {
//
//        return "redirect:/show/" + id;
//
//    }

//    @GetMapping("/show/{id}")
//    public String showById(@PathVariable long id, Model model) {
//
//        model.addAttribute("post", messageServ.showById(id));
//
//        return "messages/show";
//
//    }

//    @GetMapping("/edit/{id}")
//    public String editPostById(@PathVariable long id, Model model) {
//
//        model.addAttribute("message", messageServ.editMessageById(id));
//
//        return "messages/edit";
//
//    }

//    @PostMapping ("/edit")
//    public String editPost(@ModelAttribute Message message) {
//
//        messageServ.editMessage(message);
//
//        return "redirect:/profile";
//
//    }

//    @PostMapping("/delete")
//    public String deletePostById(@RequestParam (name="button") long id) {
//
//        messageServ.deleteMessageById(id);
//
//        return "redirect:/profile";
//
//    }

}