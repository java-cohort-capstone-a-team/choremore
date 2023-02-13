package com.capstone.choremore.serviceImplementation;

import com.capstone.choremore.models.Avatar;
import com.capstone.choremore.models.User;
import com.capstone.choremore.repositories.AvatarRepo;
import com.capstone.choremore.repositories.ChoreRepo;
import com.capstone.choremore.repositories.MessageRepo;
import com.capstone.choremore.repositories.UserRepo;
import com.capstone.choremore.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private AvatarRepo avatarDao;

    @Autowired
    private UserRepo userDao;

    @Autowired
    private ChoreRepo choreDao;

    @Autowired
    private MessageRepo messageDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

}
