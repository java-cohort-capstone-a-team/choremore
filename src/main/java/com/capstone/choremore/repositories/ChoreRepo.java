package com.capstone.choremore.repositories;

import com.capstone.choremore.models.Chore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChoreRepo extends JpaRepository<Chore, Long> {

    Chore findByTitle(String title);

    List<Chore> findChoreByParentId(long id);

    List<Chore> findAllByChildId(long id);

    List<Chore> getChoresByChildId(long id);

}