package com.capstone.choremore.serviceImplementation;

import com.capstone.choremore.apis.ApiHandleImp;
import com.capstone.choremore.config.Config;
import com.capstone.choremore.models.Avatar;
import com.capstone.choremore.models.Chore;
import com.capstone.choremore.models.Message;
import com.capstone.choremore.models.User;
import com.capstone.choremore.repositories.AvatarRepo;
import com.capstone.choremore.repositories.UserRepo;
import com.capstone.choremore.services.AvatarService;
import com.capstone.choremore.services.ChoreService;
import com.capstone.choremore.services.MessageService;
import com.capstone.choremore.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private AvatarRepo avatarDao;

    @Autowired
    private UserRepo userDao;

    @Autowired
    private MessageService messageServ;

    @Autowired
    private ChoreService choreServ;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AvatarService avatarServ;

    @Autowired
    private ApiHandleImp apiServ;

    public User getCurrentUser() {

        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }

    public Boolean createNewUser(User user, Model model, HttpServletRequest request) throws ServletException {

        try {

            String hash = user.getPassword();

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            userDao.save(user);

            request.login(user.getUsername(), hash);

            if (request.isUserInRole("ROLE_PARENT")) {

                model.addAttribute("avatar", new Avatar());

                return true;
            }

        } catch (ServletException e) {

            e.printStackTrace();

        }

        return false;

    }

    public void createChildUser(User user) {

        Avatar avatar = new Avatar();
        User me = new User((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        user.setRoles("ROLE_CHILD");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userDao.save(user);

        avatar.setParent(me);
        avatar.setChild(user);

        avatarDao.save(avatar);

        user.setAvatar(avatar);
        me.setAvatars(List.of(avatar));

        userDao.save(me);
        userDao.save(user);

    }

    public List<User> getChildOfParent() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long id = user.getId();

        List<User> allUsers = userDao.findAll();

        List<User> myChildren = new ArrayList<>();

        for (User usr : allUsers) {

            if (usr.getRoles().equals("ROLE_CHILD") && usr.getAvatar().getParent().getId() == id) {

                myChildren.add(usr);

            }

        }

        return myChildren;

    }

    public void getAvatarManager (Model model) {

        List<User> users = getChildOfParent();

        users.forEach(user -> {

            Avatar avatar = avatarDao.findAvatarByChildId(user.getId());
            String base64Encoded = avatarServ.getAvatarImg(avatar);
            avatar.setImageString(base64Encoded);

        });

        model.addAttribute("users", getChildOfParent());
        model.addAttribute("user", new User());

    }

    public void getProfileView(Model model) {

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

        model.addAttribute("user", getCurrentUser());
        model.addAttribute("avatars", myAvatars);
        model.addAttribute("chores", chores);
        model.addAttribute("messages", messages);
        model.addAttribute("chore", new Chore());

    }

    public void createAvatarView(Model model) {

        model.addAttribute("user", new User());

    }

    public void childProfileView(Model model) throws UnsupportedEncodingException {

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
        model.addAttribute("user", getCurrentUser());

    }

    public void postAvatarBuilder(MultipartFile image, String class_type) throws Exception {

        User user = getCurrentUser();
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

    }

    public void avatarFormView(Model model) {

        model.addAttribute("avatar", new Avatar());

    }

    public void battleArenaView (Model model) {

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

    }

}
