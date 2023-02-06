package com.capstone.choremore.serviceImplementation;

import com.capstone.choremore.models.Avatar;
import com.capstone.choremore.models.User;
import com.capstone.choremore.repositories.AvatarRepo;
import com.capstone.choremore.repositories.UserRepo;
import com.capstone.choremore.services.AvatarService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvatarServiceImp implements AvatarService {

    private final AvatarRepo avatarDao;
    private final UserRepo userDao;
    private final PasswordEncoder passwordEncoder;

    public AvatarServiceImp(AvatarRepo avatarRepo, UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.avatarDao = avatarRepo;
        this.userDao = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

//    @Override
//    public List<Avatar> profileShowUserAvatars() {
//
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        List<Avatar> avatars = userDao.findByUsername(user.getUsername()).getAvatars();
//
//        return avatars;
//
//    }

//    @Override
//    public void createAvatar(Avatar avatar) {
//
//        String hash = passwordEncoder.encode(avatar.getPassword());
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        avatar.setUser(user);
//        avatar.setPassword(hash);
//        avatarDao.save(avatar);
//
//    }

    @Override
    public List<Avatar> showAvatars() {

        List<Avatar> avatars = avatarDao.findAll();

        return avatars;

    }

    @Override
    public Avatar showById(long id) {

        Avatar avatar = avatarDao.getReferenceById(id);

        return avatar;

    }

    @Override
    public Avatar editAvatarById(long id) {

        Avatar avatar = avatarDao.getReferenceById(id);

        return avatar;

    }

    @Override
    public void editAvatar(Avatar avatar) {

        avatarDao.save(avatar);

    }

    @Override
    public void deleteAvatarById(long id) {

        avatarDao.deleteById(id);

    }
}