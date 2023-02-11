package com.capstone.choremore.services;

import com.capstone.choremore.models.Avatar;
import com.capstone.choremore.models.Chore;
import com.capstone.choremore.models.Message;

import java.util.List;

public interface AvatarService {

    Avatar showAvatarByChildId();

    List<Avatar> showAvatarsByParentsId();

    String getAvatarImg(Avatar myAv);

//    Avatar showAvatarByParentId();

    Avatar getAvatarByMessage(Message message);

    Avatar getAvatarByChore (Chore chore);

    void editHp();

    void editStrength();

    void editDefense();

    Avatar getCurrentAvatar();

    void editAvatarImgClass(Avatar avatar);

}
