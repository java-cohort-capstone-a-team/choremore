package com.capstone.choremore.repositories;

import com.capstone.choremore.models.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarRepo extends JpaRepository<Avatar, Long> {

//    Avatar findById(long id);

//    Avatar findByUsername(String username);

}