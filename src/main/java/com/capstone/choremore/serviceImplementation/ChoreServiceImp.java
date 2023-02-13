package com.capstone.choremore.serviceImplementation;

import com.capstone.choremore.models.Avatar;
import com.capstone.choremore.models.Chore;
import com.capstone.choremore.models.User;
import com.capstone.choremore.repositories.AvatarRepo;
import com.capstone.choremore.repositories.ChoreRepo;
import com.capstone.choremore.repositories.UserRepo;
import com.capstone.choremore.services.AvatarService;
import com.capstone.choremore.services.ChoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class ChoreServiceImp implements ChoreService {

    @Autowired
    private ChoreRepo choreDao;

    @Autowired
    private AvatarRepo avatarDao;

    @Autowired
    private UserRepo userDao;

    @Autowired
    private AvatarService avatarServ;

    @Override
    public List<Chore> showChoresByParentsId() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long id = user.getId();
        List<Chore> chores = choreDao.findChoreByParentId(id);

        return chores;

    }

    public List<Chore> showChoresByChildId() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long id = user.getId();
        List<Chore> chores = choreDao.findAllByChildId(id);

        return chores;

    }

    @Override
    public void createChore(Chore chore, long id) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User child = userDao.findById(id).get();

        chore.setParent(user);
        chore.setChild(child);

        choreDao.save(chore);

    }

    public void changeChoreStatus(long id) {

        Chore chore = choreDao.findById(id).get();

        if (chore.getStatus().equals("Incomplete")) {

            chore.setStatus("Complete");

            choreDao.save(chore);

        } else {

            chore.setStatus("Incomplete");

            choreDao.save(chore);

        }

    }

    public void approveChore(long id) {

        Chore chore = choreDao.findById(id).get();
        User child = chore.getChild();
        Avatar avatar = child.getAvatar();

        if (chore.getStatus().equals("Complete")) {

            long newExp = avatar.getExp() + chore.getValue();
            long newBuildPoints = avatar.getBuild_points() + chore.getValue();

            if (newExp >= 10) {

                long newLevel = avatar.getLevel() + 1;

                avatar.setLevel(newLevel);
                avatar.setExp(newExp - 10);

            } else {

                avatar.setExp(newExp);

            }

            avatar.setBuild_points(newBuildPoints);

            avatarDao.save(avatar);
            choreDao.delete(chore);

        }

    }

    public List<Chore> childShowChores() {

            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<Chore> chores = choreDao.findAllByChildId(user.getId());

            return chores;

    }

    public void deleteChoreById(long id) {

        choreDao.deleteById(id);

    }

    public void editChore(Chore chore, long id, long id2) {

        Chore editedChore = choreDao.findById(id2).get();

        editedChore.setTitle(chore.getTitle());
        editedChore.setBody(chore.getBody());
        editedChore.setValue(chore.getValue());

        User child = userDao.findById(id).get();

        editedChore.setChild(child);

        choreDao.save(editedChore);

    }

    public void choreManagerView(Model model) {

        List<Chore> chores = showChoresByParentsId();

        chores.forEach(chore -> {

            Avatar avatar = avatarServ.getAvatarByChore(chore);
            String base64Encoded2 = avatarServ.getAvatarImg(avatar);

            avatar.setImageString(base64Encoded2);

        });

        model.addAttribute("avatars", avatarServ.showAvatarsByParentsId());
        model.addAttribute("chore", new Chore());
        model.addAttribute("chores", showChoresByParentsId());

    }

    public void choresView(Model model) {

        model.addAttribute("chores", childShowChores());

    }

}
