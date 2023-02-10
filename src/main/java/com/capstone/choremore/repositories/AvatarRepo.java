package com.capstone.choremore.repositories;

import com.capstone.choremore.models.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvatarRepo extends JpaRepository<Avatar, Long> {

    List<Avatar> findAvatarsByParentId(long id);

    Avatar findAvatarByChildId(long userId);

//    void deleteAvatarByChildId(long id);
}