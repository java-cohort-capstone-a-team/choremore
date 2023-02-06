package com.capstone.choremore.repositories;

import com.capstone.choremore.models.Chore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChoreRepo extends JpaRepository<Chore, Long> {

    Chore findByTitle(String title);

}