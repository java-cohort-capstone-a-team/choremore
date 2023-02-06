package com.capstone.choremore.repositories;

import com.capstone.choremore.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Long> {

    Message findByTitle(String title);

}
