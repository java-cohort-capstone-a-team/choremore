package com.capstone.choremore.controller;

import com.capstone.choremore.apis.ApiHandleImp;
import com.capstone.choremore.config.Config;
import com.capstone.choremore.models.*;
import com.capstone.choremore.repositories.AvatarRepo;
import com.capstone.choremore.repositories.UserRepo;
import com.capstone.choremore.services.AvatarService;
import com.capstone.choremore.services.ChoreService;
import com.capstone.choremore.services.MessageService;
import com.capstone.choremore.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;

@Controller
public class UserController {

    @Autowired
    private UserService userServ;

    @Autowired
    private UserRepo userDao;

    @Autowired
    private AvatarRepo avatarDao;

    @Autowired
    private ChoreService choreServ;

    @Autowired
    private MessageService messageServ;

    @Autowired
    private AvatarService avatarServ;

    @Autowired
    private ApiHandleImp apiServ;

    @GetMapping("/create-avatar")
    public String childAvatarForm(Model model) {

        model.addAttribute("user", new User());

        return "users/avatar-register";

    }

    @PostMapping("/avatar-creation")
    public String createChildAvatar(@ModelAttribute User user) {

        userServ.createChildUser(user);

        return "redirect:/avatar-manager";

    }

    @GetMapping("/avatar-manager")
    public String avatarManager(Model model, Model model2) {

        List<User> users = userServ.getChildOfParent();

        users.forEach(user -> {

            Avatar avatar = avatarDao.findAvatarByChildId(user.getId());
            String base64Encoded = avatarServ.getAvatarImg(avatar);
            avatar.setImageString(base64Encoded);

        });

        model2.addAttribute("users", userServ.getChildOfParent());
        model.addAttribute("user", new User());

        return "avatars/index";

    }

    @GetMapping("/profile")
    public String showProfile(Model model) {

        List<Avatar> myAvatars = avatarServ.showAvatarsByParentsId();

        myAvatars.forEach(avatar -> {

            String base64Encoded = avatarServ.getAvatarImg(avatar);
            avatar.setImageString(base64Encoded);

        });

        List<Chore> chores = choreServ.showChoresByParentsId();

        chores.forEach(chore -> {

            Avatar avatar = avatarServ.getAvatarByChore(chore);
            String base64Encoded2 = avatarServ.getAvatarImg(avatar);
            avatar.setImageString(base64Encoded2);

        });

        List<Message> messages = messageServ.getChildMessages();
        messages.forEach(message -> {

            Avatar avatar = avatarServ.getAvatarByMessage(message);
            String base64Encoded2 = avatarServ.getAvatarImg(avatar);
            avatar.setImageString(base64Encoded2);

        });

        model.addAttribute("user", userServ.getCurrentUser());
        model.addAttribute("avatars", myAvatars);
        model.addAttribute("chores", chores);
        model.addAttribute("messages", messages);
        model.addAttribute("chore", new Chore());

        return "users/profile";

    }



    @GetMapping("/child-profile")
    public String showChildProfile(Model model) throws UnsupportedEncodingException {

        Avatar myAv = avatarServ.getCurrentAvatar();

        model.addAttribute("avatar", myAv);

        String base64Encoded = avatarServ.getAvatarImg(myAv);

        model.addAttribute("contentImage", base64Encoded);

        List<Message> messages = messageServ.showMessages();
        messages.forEach(message -> {

            Avatar avatar = avatarServ.getAvatarByMessage(message);
            String base64Encoded2 = avatarServ.getAvatarImg(avatar);
            avatar.setImageString(base64Encoded2);

        });

        model.addAttribute("messages", messages);
        model.addAttribute("message", new Message());
        model.addAttribute("chores", choreServ.showChoresByChildId());
        model.addAttribute("user", userServ.getCurrentUser());

        return "avatars/child-profile";

    }

    @PostMapping("/avatarbuilder")
    public String addImageAndClassToAvatar(@RequestParam("image") MultipartFile image, @RequestParam("class_type") String class_type) throws Exception {

        User user = userServ.getCurrentUser();
        Avatar myAvatar = avatarDao.findAvatarByChildId(user.getId());

        if (Objects.equals(class_type, "fairy")) {

            byte[] newImg = apiServ.apiCall(image, Config.toonFairy);
            myAvatar.setImage(newImg);

        } else if (Objects.equals(class_type, "mage")) {

            byte[] newImg = apiServ.apiCall(image, Config.toonMage);
            myAvatar.setImage(newImg);

        } else if (Objects.equals(class_type, "warrior")) {

            byte[] newImg = apiServ.apiCall(image, Config.toonWarrior);
            myAvatar.setImage(newImg);

        } else if (Objects.equals(class_type, "undead")) {

            byte[] newImg = apiServ.apiCall(image, Config.toonUndead);
            myAvatar.setImage(newImg);

        } else if (Objects.equals(class_type, "dwarf")) {

            byte[] newImg = apiServ.apiCall(image, Config.toonDwarf);
            myAvatar.setImage(newImg);

        }

        myAvatar.setClassType(class_type);
        avatarDao.save(myAvatar);

        return "redirect:/child-profile";

    }

    @GetMapping("/avatar-form")
    public String showAvatarForm(Model model) {

        model.addAttribute("avatar", new Avatar());

        return "avatars/avatar-form";

    }

    @GetMapping("/battle-arena")
    public String showBattleArena(Model model) {

        Avatar myAv = avatarServ.getCurrentAvatar();

        model.addAttribute("avatar", myAv);

        String base64Encoded = avatarServ.getAvatarImg(myAv);

        List<Avatar> avatars = avatarDao.findAll();
        avatars.forEach(avatar -> {


            String base64Encoded2 = avatarServ.getAvatarImg(avatar);
            avatar.setImageString(base64Encoded2);

        });


        model.addAttribute("contentImage", base64Encoded);
        model.addAttribute("avatars", avatars);


        return "avatars/battle-arena";

    }

    @GetMapping("/sign-up")
    public String showSignupForm(Model model) {

        model.addAttribute("user", new User());

        return "users/sign-up";

    }

    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User user, Model model, HttpServletRequest request) throws ServletException {

        if (userServ.createNewUser(user, model, request)) {

            return "redirect:/create-avatar";

        } else {

            return "users/sign-up";

        }

    }

}
