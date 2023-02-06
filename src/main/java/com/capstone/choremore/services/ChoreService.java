package com.capstone.choremore.services;

import com.capstone.choremore.models.Chore;

import java.util.List;

public interface ChoreService {

    List<Chore> profileShowUserChores();

    void createChore(Chore chore);

    List<Chore> showAllChores();

    Chore showChoreById(Long id);

    Chore editChoreById(Long id);

    void editChore(Chore chore);

    void deleteChoreById(Long id);

    Chore findByTitle(String title);

}
