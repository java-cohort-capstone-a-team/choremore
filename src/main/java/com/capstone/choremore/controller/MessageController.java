package com.capstone.choremore.controller;

import com.capstone.choremore.models.Avatar;
import com.capstone.choremore.models.Message;
import com.capstone.choremore.repositories.AvatarRepo;
import com.capstone.choremore.repositories.UserRepo;
import com.capstone.choremore.services.AvatarService;
import com.capstone.choremore.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageServ;

    @Autowired
    private AvatarService avatarServ;

    @Autowired
    private AvatarRepo avatarDao;

    @PostMapping("/createmsg")
    public String createNewMessage(@ModelAttribute Message message) {

        messageServ.createMessage(message);

        return "redirect:/message-board";

    }

    @GetMapping("/message-board")
    public String show(Model model) throws UnsupportedEncodingException {

        Avatar myAvatar = avatarServ.getCurrentAvatar();

        model.addAttribute("avatars", myAvatar);

        String base64Encoded = avatarServ.getAvatarImg(myAvatar);

        List<Message> messages = messageServ.showMessages();
        messages.forEach(message -> {

            Avatar msgAvatar = avatarServ.getAvatarByMessage(message);
            String base64Encoded2 = avatarServ.getAvatarImg(msgAvatar);
            msgAvatar.setImageString(base64Encoded2);

        });

        model.addAttribute("contentImage", base64Encoded);
        model.addAttribute("messages", messages);
        model.addAttribute("message", new Message());

        return "/messages/index";

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
