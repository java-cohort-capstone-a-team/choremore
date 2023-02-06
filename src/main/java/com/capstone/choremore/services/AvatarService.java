package com.capstone.choremore.services;

import com.capstone.choremore.models.Avatar;

import java.util.List;

public interface AvatarService {

//    List<Avatar> profileShowUserAvatars();

//    void createAvatar(Avatar avatar);

    List<Avatar> showAvatars();

    Avatar showById(long id);

    Avatar editAvatarById(long id);

    void editAvatar(Avatar avatar);

    void deleteAvatarById(long id);

}
