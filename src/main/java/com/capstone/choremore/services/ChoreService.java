package com.capstone.choremore.services;

import com.capstone.choremore.models.Chore;
import org.springframework.ui.Model;

import java.util.List;

public interface ChoreService {

    void createChore(Chore chore, long id);

    void changeChoreStatus(long id);

    List<Chore> showChoresByParentsId();

    List<Chore> showChoresByChildId();

    void approveChore(long id);

    List<Chore> childShowChores();

    void deleteChoreById(long id);

    void editChore(Chore chore, long id, long id2);

    void choreManagerView(Model model);

    void choresView(Model model);

}
