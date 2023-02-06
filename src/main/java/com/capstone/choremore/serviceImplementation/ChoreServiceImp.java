package com.capstone.choremore.serviceImplementation;

import com.capstone.choremore.models.Chore;
import com.capstone.choremore.models.User;
import com.capstone.choremore.repositories.ChoreRepo;
import com.capstone.choremore.repositories.UserRepo;
import com.capstone.choremore.services.ChoreService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChoreServiceImp implements ChoreService {

    private final ChoreRepo choreDao;

    private final UserRepo userDao;

    public ChoreServiceImp(ChoreRepo choreDao, UserRepo userDao) {
        this.choreDao = choreDao;
        this.userDao = userDao;
    }


    @Override
    public List<Chore> profileShowUserChores() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Chore> chores = userDao.findByUsername(user.getUsername()).getChores();

        return chores;

    }

    @Override
    public void createChore(Chore chore) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        chore.setParent(user);
        choreDao.save(chore);

    }

    @Override
    public List<Chore> showAllChores() {

        List<Chore> chores = choreDao.findAll();

        return chores;

    }

    @Override
    public Chore showChoreById(Long id) {

        Chore chore = choreDao.getReferenceById(id);

        return chore;

    }

    @Override
    public Chore editChoreById(Long id) {

        Chore chore = choreDao.getReferenceById(id);

        return chore;

    }

    @Override
    public void editChore(Chore chore) {

        choreDao.save(chore);

    }

    @Override
    public void deleteChoreById(Long id) {

        choreDao.deleteById(id);

    }

    @Override
    public Chore findByTitle(String title) {

        return choreDao.findByTitle(title);

    }
}
