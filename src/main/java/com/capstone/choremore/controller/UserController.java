package com.capstone.choremore.controller;

import com.capstone.choremore.imagehandle.FileUploadUtil;
import com.capstone.choremore.models.*;
import com.capstone.choremore.repositories.AvatarRepo;
import com.capstone.choremore.services.AvatarService;
import com.capstone.choremore.services.ChoreService;
import com.capstone.choremore.services.MessageService;
import com.capstone.choremore.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private static final String UPLOAD_DIRECTORY = "src/main/resources/static/images/avatars";
    @Autowired
    private UserService userServ;

    @Autowired
    private AvatarRepo avatarDao;

    @Autowired
    private ChoreService choreServ;

    @Autowired
    private MessageService messageServ;

    @Autowired
    private AvatarService avatarServ;
//    @Autowired
//    private AvatarRepo avatarRepo;

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

        model2.addAttribute("users", userServ.getChildOfParent());
        model.addAttribute("user", new User());

        return "avatars/index";

    }

    @GetMapping("/profile")
    public String showProfile(Model model, Model model2, Model model3, Model model4, Model model5) {

        model.addAttribute("user", userServ.getCurrentUser());
        model2.addAttribute("avatars", avatarServ.showAvatarsByParentsId());
        model3.addAttribute("chores", choreServ.showChoresByParentsId());
        model4.addAttribute("messages", messageServ.getChildMessages());
        model5.addAttribute("chore", new Chore());

        return "users/profile";

    }



    @GetMapping("/child-profile")
    public String showChildProfile(Model model, Model model2, Model model3, Model model4, Model model5) throws UnsupportedEncodingException {

        Avatar myAv = avatarServ.getCurrentAvatar();

        model5.addAttribute("avatar", myAv);

        byte[] encodeBase64 = Base64.getEncoder().encode(myAv.getImage());
        String base64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);

        model5.addAttribute("contentImage", base64Encoded);

//        ModelAndView mav = new ModelAndView("view");
//        mav.addObject("contentImage", base64Encoded);

        model3.addAttribute("messages", messageServ.showMessages());
        model4.addAttribute("message", new Message());
        model2.addAttribute("chores", choreServ.showChoresByChildId());
        model.addAttribute("user", userServ.getCurrentUser());

        return "avatars/child-profile";

    }

    @PostMapping("/avatarbuilder")
    public String addImageAndClassToAvatar(@RequestParam("image") MultipartFile image, @RequestParam("class_type") String class_type) throws IOException {

        User user = userServ.getCurrentUser();
        Avatar myAvatar = avatarDao.findAvatarByChildId(user.getId());

        try {

            if(image.isEmpty()) {

                byte[] imageByte = image.getInputStream().readAllBytes();

                myAvatar.setImage(imageByte);

            } else {

                Optional<Avatar> avatar = avatarDao.findById(myAvatar.getId());

                if(avatar.isPresent()) {

                    byte[] image2 = image.getInputStream().readAllBytes();

                    myAvatar.setImage(image2);

                }

            }

            myAvatar.setClassType(class_type);
            avatarDao.save(myAvatar);

        } catch (IOException e) {

            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());

        }

//        User user = userServ.getCurrentUser();
//        Avatar myAvatar = avatarDao.findAvatarByChildId(user.getId());
//        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
//        myAvatar.setImage(fileName);
//        myAvatar.setClassType(class_type);
//        Avatar savedAvatar = avatarDao.save(myAvatar);
//        String uploadDir = "src/main/resources/static/img/avatars/" + savedAvatar.getId();
//        FileUploadUtil.saveFile(uploadDir, fileName, image);

//        StringBuilder filesNames = new StringBuilder();
//        Path filesNameAndPath = Paths.get(UPLOAD_DIRECTORY, image.getOriginalFilename());
//        filesNames.append(image.getOriginalFilename());
//        Files.write(filesNameAndPath, image.getBytes());




//        avatarDao.save(myAvatar);

        return "redirect:/child-profile";

//        try {
//
//            User user = userServ.getCurrentUser();
//            Avatar myAvatar = avatarDao.findAvatarByChildId(user.getId());
//
//            myAvatar.setImage(avimg.getBytes());
//            myAvatar.setClassType(classType);
//
//            avatarDao.save(myAvatar);
//
//
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//        }
//
//        return "redirect:/child-profile";

    }

    @GetMapping("/avatar-form")
    public String showAvatarForm(Model model) {

        model.addAttribute("avatar", new Avatar());

        return "avatars/avatar-form";

    }

    @GetMapping("/battle-arena")
    public String showBattleArena(Model model, Model model2, Model model3) {

        model.addAttribute("user", userServ.getCurrentUser());
        model2.addAttribute("avatars", avatarServ.showAvatarsByParentsId());
        model3.addAttribute("chores", choreServ.showChoresByParentsId());

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

//    @PostMapping("/deletechild")
//    public String deleteChildren(@RequestParam(name = "button") long id) {
//
//        userServ.deleteChildExistenceById(id);
//
//        return "redirect:/avatar-manager";
//
//    }

}
