package com.capstone.choremore.serviceImplementation;

import com.capstone.choremore.models.Avatar;
import com.capstone.choremore.models.User;
import com.capstone.choremore.models.UserWithRoles;
import com.capstone.choremore.repositories.AvatarRepo;
import com.capstone.choremore.services.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AvatarServiceImp implements AvatarService {

    @Autowired
    private AvatarRepo avatarDao;

    public Avatar getCurrentAvatar() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = user.getId();
        Avatar avatar = avatarDao.findAvatarByChildId(userId);

        return avatar;

    }

    public Avatar showAvatarByChildId() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = user.getId();
        Avatar avatar = avatarDao.findAvatarByChildId(userId);

        return avatar;

    }

    public void editAvatarImgClass(Avatar avatar) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Avatar myAvatar = user.getAvatar();

        myAvatar.setImage(avatar.getImage());
        myAvatar.setClassType(avatar.getClassType());

        avatarDao.save(avatar);

    }

    public List<Avatar> showAvatarsByParentsId() {

            UserWithRoles user = (UserWithRoles) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            long id = user.getId();
            List<Avatar> avatars = avatarDao.findAvatarsByParentId(id);

            return avatars;

    }

    public void editHp() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Avatar avatar = user.getAvatar();

        if (avatar.getBuild_points() > 0) {

            avatar.setHp(avatar.getHp() + 1);
            avatar.setBuild_points(avatar.getBuild_points() - 1);
            avatarDao.save(avatar);

        }

    }

    public void editStrength() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Avatar avatar = user.getAvatar();

        if (avatar.getBuild_points() > 0) {
            avatar.setStrength(avatar.getStrength() + 1);
            avatar.setBuild_points(avatar.getBuild_points() - 1);
            avatarDao.save(avatar);
        }

    }

    public void editDefense() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Avatar avatar = user.getAvatar();

        if (avatar.getBuild_points() > 0) {
            avatar.setDefense(avatar.getDefense() + 1);
            avatar.setBuild_points(avatar.getBuild_points() - 1);
            avatarDao.save(avatar);
        }

    }

}