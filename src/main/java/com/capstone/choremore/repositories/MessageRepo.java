package com.capstone.choremore.repositories;

import com.capstone.choremore.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {

    Message findByTitle(String title);

    List<Message> findChildMessageByParentId(long id);

}
