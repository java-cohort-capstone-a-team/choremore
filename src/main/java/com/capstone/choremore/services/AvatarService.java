package com.capstone.choremore.services;

import com.capstone.choremore.models.Avatar;
import java.util.List;

public interface AvatarService {

    Avatar showAvatarByChildId();

    List<Avatar> showAvatarsByParentsId();

    void editHp();

    void editStrength();

    void editDefense();

    Avatar getCurrentAvatar();

    void editAvatarImgClass(Avatar avatar);

}
