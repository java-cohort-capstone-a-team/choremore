package com.capstone.choremore.services;

import com.capstone.choremore.models.Avatar;
import com.capstone.choremore.models.Chore;
import com.capstone.choremore.models.Message;
import org.springframework.ui.Model;

import java.util.List;

public interface AvatarService {

    Avatar showAvatarByChildId();

    List<Avatar> showAvatarsByParentsId();

    String getAvatarImg(Avatar myAv);

    Avatar getAvatarByMessage(Message message);

    Avatar getAvatarByChore (Chore chore);

    void editHp();

    void editStrength();

    void editDefense();

    Avatar getCurrentAvatar();

    void skillBuilderView(Model model);

}
